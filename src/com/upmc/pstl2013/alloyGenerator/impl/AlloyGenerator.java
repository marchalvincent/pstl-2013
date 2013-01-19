package com.upmc.pstl2013.alloyGenerator.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.uml2.uml.Activity;

import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.util.Console;

/**
 * Cette classe se charge de générer le fichier Alloy à partir 
 *
 */
public class AlloyGenerator implements IAlloyGenerator {
	
	private IUMLParser parser;
	private List<File> filesGenerated;
	private String separator = File.separator;
	private String userDir = System.getProperty("user.home") + separator + ".pstl2013" + separator;

	/**
	 * Constructeur
	 */
	public AlloyGenerator (IUMLParser parser) {
		super();
		this.parser = parser;
		filesGenerated = new ArrayList<File>();
		// on créé le répertoire qui contiendra les fichiers Alloy
		new File(userDir).mkdir();
	}

	@Override
	public void generateFile() {
		
		Console.debug("Début des générations.", this.getClass());
		// on récupère les activités
		List<Activity> activities = parser.getActivities();
		int i = 1;

		for (Activity activity : activities) {
			Console.debug("Génération du fichier n°" + i + ".", this.getClass());
			
			String alloyTxt = this.getAlloyTxt(activity);

			try {
				// on créé le fichier a générer
				File fichier = new File(userDir + "generated" + i + ".als");
				filesGenerated.add(fichier);
				
				// puis on écrit le contenu dedans
				FileOutputStream out = new FileOutputStream(fichier);
				out.write(alloyTxt.getBytes());
				out.close();
				
				Console.debug("Génération terminée.", this.getClass());
			}
			catch (FileNotFoundException e) {
				Console.warning("Impossible de trouver le fichier : " + e.toString(), this.getClass());
			}
			catch (IOException e) {
				Console.warning("Impossible de créer le fichier : " + e.toString(), this.getClass());
			}
			i++;
		}
		Console.debug("Générations finies.", this.getClass());
	}
	
	/**
	 * Fait appel a Jet et génère le contenu du fichier Alloy.
	 * @param activity 
	 * @return le contenu du fichier alloy.
	 */
	private String getAlloyTxt(Activity activity) {
		
		// on utilise un objet helper qui va nous permettre de passer les nodes/edges au template Jet.
		IJetHelper jetHelper = Factory.getInstance().newJetHelper(activity.getNodes(), activity.getEdges());
		JetTemplate jetTemplate = Factory.getInstance().newJetTemplate();
		return jetTemplate.generate(jetHelper);
	}
	
	@Override
	public boolean fichiersPresents() throws FileNotFoundException {
		StringBuilder fichiersManquants = new StringBuilder();
		if (!new File(userDir + "semantic.als").exists())
			fichiersManquants.append(" semantic.als");
		if (!new File(userDir + "syntax.als").exists())
			fichiersManquants.append(" syntax.als");
		if (fichiersManquants.toString().equals(""))
			return true;
		throw new FileNotFoundException("Le(s) fichier(s) suivant(s) manque(nt) :" + fichiersManquants.toString() +
				". Veuillez les ajouter dans votre répertoire : " + userDir);
	}

	@Override
	public List<File> getGeneratedFiles() {
		return filesGenerated;
	}
}
