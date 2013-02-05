package com.upmc.pstl2013.alloyExecutor.impl;

import java.io.IOException;

import org.apache.log4j.Logger;

import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerated;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;

import edu.mit.csail.sdg.alloy4.A4Reporter;
import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4.ErrorWarning;
import edu.mit.csail.sdg.alloy4compiler.ast.Command;
import edu.mit.csail.sdg.alloy4compiler.ast.Module;
import edu.mit.csail.sdg.alloy4compiler.parser.CompUtil;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Options;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;
import edu.mit.csail.sdg.alloy4compiler.translator.TranslateAlloyToKodkod;
import edu.mit.csail.sdg.alloy4viz.VizGUI;

/**
 * Cette classe se charge d'éxécuter les fichiers Alloy généré précédement
 * 
 */
public class AlloyExecutor implements IAlloyExecutor {

	private IAlloyGenerator generator;
	private String userDir;
	private static Logger log = Logger.getLogger(AlloyExecutor.class);

	/**
	 * Constructeur
	 */
	public AlloyExecutor(IAlloyGenerator generator, String userDir) {

		super();
		this.generator = generator;
		this.userDir = userDir;
	}

	@Override
	public String executeFiles() throws Err, JetException {

		// Résultat
		StringBuilder resultat = new StringBuilder();
		// on lance la génération des fichiers Alloy
		generator.generateFile();
		// The visualizer (We will initialize it to nonnull when we visualize an Alloy solution)
		VizGUI viz = null;
		String filename;
		// Alloy4 sends diagnostic messages and progress reports to the A4Reporter.
		// By default, the A4Reporter ignores all these events (but you can extend the A4Reporter to
		// display the event for the user)
		A4Reporter rep = new A4Reporter() {

			// For example, here we choose to display each "warning" by printing it to System.out
			@Override
			public void warning(ErrorWarning msg) {
				System.out.print("Relevance Warning:\n" + (msg.toString().trim()) + "\n\n");
				System.out.flush();
			}
		};
		
		for (IAlloyGenerated generated : generator.getGeneratedFiles()) {
			
			try {
				filename = generated.getFile().getCanonicalPath();
				// Vérifie que le fichier soit de type ALLOY
				if (filename.substring(filename.length() - 3, filename.length()).equals("als")) {
					// Parse+typecheck the model
					resultat.append("=========== Parsing+Typechecking " + filename + " =============\n");
					Module world = CompUtil.parseEverything_fromFile(rep, null, filename);
					// Choose some default options for how you want to execute the commands
					A4Options options = new A4Options();
					// The required JNI library cannot be found: java.lang.UnsatisfiedLinkError: no
					// minisatx5 in java.library.path
					options.solver = A4Options.SatSolver.SAT4J; // TODO: minisatx5 JNI
					//options.solverDirectory = "D:\\INFORMATIQUE\\JAVA\\workspaces\\workspacePSTL\\pstl-2013\\jars\\minisatjni1.jar";
					
					
					for (Command command : world.getAllCommands()) {
						// Execute the command
						resultat.append("=========== Executing " + command + " ============\n");
						long startTime = System.nanoTime();
						A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), command, options);
						long endTime = System.nanoTime();
						
						// Affichage des info de l'execution
						resultat.append("Solver : " + options.solver.toString() + "   ");
						resultat.append("MaxSeq : " + ans.getMaxSeq() + "   ");
						resultat.append("SkolemsDepth : " + ans.getAllSkolems() + "   ");
						resultat.append("BitWidth : " + ans.getBitwidth() + "   ");
						resultat.append("is Incremental : " + ans.isIncremental() + "   ");
						resultat.append("Is Satisfiable : " + ans.satisfiable() + "\n");
						
						// on ajoute le résultat du parcours de la stratégie
						// si on est dans un run
						if(!generated.isCheck()) {
							if (ans.satisfiable()) {
								resultat.append("VALID, Solution found = " + generated.getStrategy().parcours(ans));
							}
							else {
								resultat.append("INVALID no solution found.");
							}
						}
						// si on est dans un check
						else if (generated.isCheck()) {
							if (ans.satisfiable()) {
								resultat.append("INVALID, counter-example = " + generated.getStrategy().parcours(ans));
							}
							else {
								resultat.append("VALID, no counter-example found, assertion may be valid.");
							}
						}
						
						resultat.append("temps : " + (endTime - startTime) / 1000000 + " ms \n");
						// If satisfiable...
						if (ans.satisfiable()) {
							// You can query "ans" to find out the values of each set or type.
							// This can be useful for debugging.
							// You can also write the outcome to an XML file
							ans.writeXML("alloy_example_output.xml");
							// You can then visualize the XML file by calling this:
							if (viz == null) {
								viz = new VizGUI(false, "alloy_example_output.xml", null);
								if (!viz.loadThemeFile(userDir + "theme\\theme.thm"))
									resultat.append("Le fichier theme n'a pas été pris en compte\n." +
											"Etes vous sûre d'avoir le fichier theme.thm dans le repertoir : " + userDir + "theme ?");
							} else {
								viz.loadXML("alloy_example_output.xml", true);
							}
						}
						log.info(resultat.toString());
					}
				}
			} catch (IOException e) {
				resultat.append("Impossible de récupérer le chemin du fichier : " + e.toString() + "\n");
				log.error("Impossible de récupérer le chemin du fichier : " + e.toString(), e);
			}
		}
		return resultat.toString();
	}

	@Override
	public void reset() {

		generator.reset();
	}
}
