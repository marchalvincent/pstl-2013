package com.upmc.pstl2013.alloyExecutor.impl;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.impl.JetException;
import com.upmc.pstl2013.strategy.IStrategy;
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
	private static Logger log = Logger.getLogger(AlloyExecutor.class);

	/**
	 * Constructeur
	 */
	public AlloyExecutor(IAlloyGenerator generator) {

		super();
		this.generator = generator;
	}

	@Override
	public String executeFiles(List<IStrategy> strategies) throws Err, JetException {

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
		for (File file : generator.getGeneratedFiles()) {
			try {
				filename = file.getCanonicalPath();
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
					for (Command command : world.getAllCommands()) {
						// Execute the command
						resultat.append("=========== Executing " + command + " ============\n");
						long startTime = System.nanoTime();
						A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), command, options);
						long endTime = System.nanoTime();
						// Print the outcome
						// resultat.append(ans.toString());
						// Affichage des info de l'execution
						resultat.append("Solver : " + options.solver.toString() + "   ");
						resultat.append("MaxSeq : " + ans.getMaxSeq() + "   ");
						resultat.append("SkolemsDepth : " + ans.getAllSkolems() + "   ");
						resultat.append("BitWidth : " + ans.getBitwidth() + "   ");
						resultat.append("is Incremental : " + ans.isIncremental() + "   ");
						resultat.append("Is Satisfiable : " + ans.satisfiable() + "\n");
						for (IStrategy iStrategy : strategies) {
							resultat.append(iStrategy.parcours(ans) + "\n");
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
							} else {
								viz.loadXML("alloy_example_output.xml", true);
							}
						}
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
