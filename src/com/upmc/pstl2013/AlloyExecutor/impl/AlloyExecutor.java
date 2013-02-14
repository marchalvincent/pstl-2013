package com.upmc.pstl2013.alloyExecutor.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
import edu.mit.csail.sdg.alloy4compiler.ast.Command;
import edu.mit.csail.sdg.alloy4compiler.ast.Module;
import edu.mit.csail.sdg.alloy4compiler.parser.CompUtil;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Options;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;
import edu.mit.csail.sdg.alloy4compiler.translator.TranslateAlloyToKodkod;

/**
 * Cette classe se charge d'éxécuter les fichiers Alloy généré précédement par le {@link IAlloyGenerator}.
 * 
 */
public class AlloyExecutor implements IAlloyExecutor {

	private IAlloyGenerator generator;
	private IFile UMLFile;
	private static final Boolean XMLLock = Boolean.TRUE;
	private Logger log = Logger.getLogger(AlloyExecutor.class);

	/**
	 * Constructeur
	 */
	public AlloyExecutor(IFile UMLFile, String dirDestination, IProperties property) {

		super();
		this.generator = Factory.getInstance().newAlloyGenerator(UMLFile, dirDestination, property);
		this.UMLFile = UMLFile;
	}

	@Override
	public IFileResult executeFiles() throws Err, JetException {

		// Déclaration des variables
		List<IActivityResult> activityResults = new ArrayList<IActivityResult>();
		IActivityResult activityResult = null;
		
		// tant qu'on peut généré un fichier (Cf. conditions dans la méthode redéfinie)
		while (generator.hasNext() && (Integer.parseInt(generator.getNbState()) <= 100)) {
			// 1. On lance la génération des fichiers Alloy
			IAlloyGenerated generated = generator.next();
			if (generated == null) {
				break;
			}
			
			// 2. On créé notre reporter sur la génération de la solution
			A4Reporter rep = Factory.getInstance().newReporter();

			File generatedFile = generated.getFile();
			// on créé un résultat pour cette activité si elle n'existe pas déjà
			if (activityResult == null) {
				activityResult = Factory.getInstance().newActivityResult(generatedFile.getAbsolutePath());
			}

			try {
				String filenameAlloy = generatedFile.getCanonicalPath();
				// On vérifie que le fichier soit de type ALLOY
				if (filenameAlloy.substring(filenameAlloy.length() - 3, filenameAlloy.length()).equals("als")) {

					// On parse le fichier pour le transformer en objet Alloy.
					activityResult.appendLog("\n\n=========== Parsing+Typechecking " + filenameAlloy + " =============\n");
					Module world = CompUtil.parseEverything_fromFile(rep, null, filenameAlloy);

					// L'option d'exécution Alloy.
					A4Options options = new A4Options();
					options.solver = A4Options.SatSolver.SAT4J; // TODO: minisatx5 JNI

					for (Command command : world.getAllCommands()) {
						// On exécute la génération de la solution Alloy.
						A4Solution ans = TranslateAlloyToKodkod.execute_command(rep, world.getAllReachableSigs(), command, options);

						// Affichage des info de l'execution
						activityResult.appendLog(this.getInfosLogs(command, rep));

						// On parcours la solution Alloy
						activityResult.appendLog(this.executionTravel(generated, ans));

						// Si la solution est satisfiable
						if (ans.satisfiable()) {
							//On passe au generateur la solution.
							generator.setSolution(ans);
							activityResult.setSatisfiable(true);
							String filenameXML = filenameAlloy.substring(0, (filenameAlloy.length() -3)) + "xml";
							activityResult.setPathXMLResult(filenameXML);

							// On écrit le résultat dans un fichier XML
							this.writeXML(ans, filenameXML);
						}
						else {
							//On passe au generateur la solution.
							generator.setSolution(ans);
							activityResult.setSatisfiable(false);
							activityResult.setPathXMLResult(null);
						}
						// on spécifie au résultat le nombre de state utilisé
						activityResult.setNbState(generator.getNbState());
					}
				}
			} catch (IOException e) {
				activityResult.appendLog("Impossible de récupérer le chemin du fichier : " + e.toString() + "\n");
				log.error("Impossible de récupérer le chemin du fichier : " + e.toString(), e);
			}
		}
		System.out.println(activityResult.getLogResult());
		// et enfin on ajoute le résultat de l'activityResult
		if (activityResult != null) {
			activityResults.add(activityResult);
		}

		// On peut enfin retourner l'objet IFileResult
		return Factory.getInstance().newFileResult(UMLFile.getName(), activityResults);
	}

	/**
	 * Ecrit le résultat de la solution trouvée par Alloy dans le chemin spécifier en paramètre.
	 * @param ans la {@link A4Solution} alloy.
	 * @param fileName un string représentant le chemin du fichier XML.
	 * @throws Err 
	 * @throws IOException 
	 */
	private void writeXML(A4Solution ans, String fileName) throws Err, IOException {

		// on se synchronise sur une variable static, comme ca pas de problème de concurence entre les exécutors
		synchronized (XMLLock) {
			// Le "writeXML" de Alloy ne marche pas avec un path absolut.
			// 1. On l'écrit par défaut
			ans.writeXML("alloySolutionTmp.xml");

			// 2. On le copie dans le bon répertoire
			File oldFile = new File("alloySolutionTmp.xml");
			File newFile = new File(fileName);
			if (!newFile.exists()) {
				newFile.createNewFile();
			}

			FileOutputStream out = new FileOutputStream(newFile);
			FileInputStream in = new FileInputStream(oldFile);

			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}

			// 3. On supprime l'ancien fichier
			oldFile.delete();

			out.close();
			in.close();
		}
	}

	/**
	 * Renvoie les logs à afficher dans l'interface graphique.
	 * @param command
	 * @param rep
	 * @return
	 */
	private String getInfosLogs(Command command, A4Reporter rep) {
		StringBuilder sb = new StringBuilder();
		sb.append("Executing \"");
		sb.append(command.toString());
		sb.append("\"\n");
		sb.append(rep.toString() + "\n");
		return sb.toString();
	}

	/**
	 * Parcours une solution Alloy selon la stratégie contenue dans le {@link IAlloyGenerated}.
	 * @param generated
	 * @param ans
	 * @return
	 */
	private String executionTravel(IAlloyGenerated generated, A4Solution ans) {
		StringBuilder sb = new StringBuilder();
		// si on est dans un run
		if(!generated.isCheck()) {
			if (ans.satisfiable()) {
				sb.append("VALID, Solution found. ");
				sb.append(generated.getProperty().parcours(ans));
			}
			else {
				sb.append("INVALID no solution found.");
			}
		}
		// si on est dans un check
		else if (generated.isCheck()) {
			if (ans.satisfiable()) {
				sb.append("INVALID, counterexample found. ");
				sb.append(generated.getProperty().parcours(ans));
			}
			else {
				sb.append("VALID, no counter-example found, assertion may be valid.");
			}
		}
		sb.append("\n");
		return sb.toString();
	}
}
