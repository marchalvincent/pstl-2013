package com.upmc.pstl2013.alloyExecutor.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import com.upmc.pstl2013.alloyExecutor.IActivityResult;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyExecutor.IFileResult;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerated;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IProperties;
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
	private IFile UMLFile;
	private String dirDestination;
	//TODO faire le système d'itération
	@SuppressWarnings("unused")
	private IProperties property;
	private static Logger log = Logger.getLogger(AlloyExecutor.class);

	/**
	 * Constructeur
	 */
	public AlloyExecutor(IFile UMLFile, String dirDestination, IProperties property) {

		super();
		this.generator = Factory.getInstance().newAlloyGenerator(UMLFile, dirDestination, property);
		this.UMLFile = UMLFile;
		this.dirDestination = dirDestination;
		this.property = property;
	}

	@Override
	public IFileResult executeFiles() throws Err, JetException {

		// Déclaration des variables
		VizGUI viz = null;
		String filenameAlloy, filenameXML;
		File generatedFile;
		IActivityResult activityResult;
		List<IActivityResult> activityResults = new ArrayList<IActivityResult>();

		// 1. On lance la génération des fichiers Alloy
		List<IAlloyGenerated> filesGenerated = generator.generateFile();

		// Alloy4 sends diagnostic messages and progress reports to the A4Reporter.
		// By default, the A4Reporter ignores all these events (but you can extend the A4Reporter to
		// display the event for the user)
		A4Reporter rep = new A4Reporter() {

			// TODO redéfinir la méthode solve???
			@Override
			public void solve(int primaryVars, int totalVars, int clauses) {
				System.out.println("appel a solve : primaryVars : " + primaryVars + ", totalVars : " + totalVars + ", clauses : " + clauses);
			}
			
			// For example, here we choose to display each "warning" by printing it to System.out
			@Override
			public void warning(ErrorWarning msg) {
				System.out.print("Relevance Warning:\n" + (msg.toString().trim()) + "\n\n");
				System.out.flush();
			}
		};

		for (IAlloyGenerated generated : filesGenerated) {
			generatedFile = generated.getFile();
			// on créé un résultat pour cette activité
			activityResult = Factory.getInstance().newActivityResult(generatedFile.getAbsolutePath());
			
			try {
				filenameAlloy = generatedFile.getCanonicalPath();
				// On vérifie que le fichier soit de type ALLOY
				if (filenameAlloy.substring(filenameAlloy.length() - 3, filenameAlloy.length()).equals("als")) {
					
					// On parse le fichier pour le transformer en objet Alloy.
					activityResult.appendLog("\n\n=========== Parsing+Typechecking " + filenameAlloy + " =============\n");
					Module world = CompUtil.parseEverything_fromFile(rep, null, filenameAlloy);
					// Choose some default options for how you want to execute the commands
					A4Options options = new A4Options();
					// The required JNI library cannot be found: java.lang.UnsatisfiedLinkError: no minisatx5 in java.library.path
					options.solver = A4Options.SatSolver.SAT4J; // TODO: minisatx5 JNI

					for (Command command : world.getAllCommands()) {
						// On exécute la génération de la solution Alloy.
						activityResult.appendLog("=========== Executing " + command + " ============\n");
						long startTime = System.nanoTime();
						A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), command, options);
						long endTime = System.nanoTime();

						// Affichage des info de l'execution
						activityResult.appendLog("Solver : " + options.solver.toString() + "   ");
						activityResult.appendLog("MaxSeq : " + ans.getMaxSeq() + "   ");
						activityResult.appendLog("SkolemsDepth : " + ans.getAllSkolems() + "   ");
						activityResult.appendLog("BitWidth : " + ans.getBitwidth() + "   ");
						activityResult.appendLog("is Incremental : " + ans.isIncremental() + "   ");
						activityResult.appendLog("Is Satisfiable : " + ans.satisfiable() + "\n");

						activityResult.appendLog("temps : " + (endTime - startTime) / 1000000 + " ms \n");

						// on ajoute le résultat du parcours de la stratégie
						// si on est dans un run
						if(!generated.isCheck()) {
							if (ans.satisfiable()) {
								activityResult.appendLog("VALID, Solution found = ");
								activityResult.appendLog(generated.getStrategy().parcours(ans));
							}
							else {
								activityResult.appendLog("INVALID no solution found.");
							}
						}
						// si on est dans un check
						else if (generated.isCheck()) {
							if (ans.satisfiable()) {
								activityResult.appendLog("INVALID, counter-example = ");
								activityResult.appendLog(generated.getStrategy().parcours(ans));
							}
							else {
								activityResult.appendLog("VALID, no counter-example found, assertion may be valid.");
							}
						}
						activityResult.appendLog("\n");

						// Si la solution est satisfiable
						if (ans.satisfiable()) {
							filenameXML = dirDestination + filenameAlloy + ".xml";
							activityResult.setPathXMLResult(filenameXML);
							
							// On écrit le résultat dans un fichier XML
							ans.writeXML("alloySolution.xml");
							// Et on lance le visualisateur de solution
							if (viz == null) {
								viz = new VizGUI(false, "alloySolution.xml", null);
								if (!viz.loadThemeFile(dirDestination + "theme\\theme.thm"))
									activityResult.appendLog("Le fichier theme n'a pas été pris en compte\n." +
											"Etes vous sûre d'avoir le fichier theme.thm dans le répertoire : " + dirDestination + "theme ?");
							} else {
								viz.loadXML("alloySolution.xml", true);
							}
						}
					}
				}
			} catch (IOException e) {
				activityResult.appendLog("Impossible de récupérer le chemin du fichier : " + e.toString() + "\n");
				log.error("Impossible de récupérer le chemin du fichier : " + e.toString(), e);
			}
			// et enfin on ajoute le résultat de l'activityResult
			activityResults.add(activityResult);
		}
		
		// On peut enfin retourner l'objet IFileResult
		return Factory.getInstance().newFileResult(UMLFile.getName(), activityResults);
	}
}
