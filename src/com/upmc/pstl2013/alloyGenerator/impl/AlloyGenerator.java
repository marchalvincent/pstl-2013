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

	/**
	 * Constructeur
	 */
	public AlloyGenerator (IUMLParser parser) {
		super();
		this.parser = parser;
		filesGenerated = new ArrayList<File>();
	}

	@Override
	public void generateFile() {
		
		String separator = File.separator;
		String userDir = System.getProperty("user.home") + separator;
		
		Console.debug("Début des générations.", this.getClass());
		// on récupère les activités
		List<Activity> activities = parser.getActivities();
		int i = 1;

		for (Activity activity : activities) {
			Console.debug("Génération du fichier n°" + i + ".", this.getClass());
			/*
			 * pour chaque activité on créé un objet helper qui va nous 
			 * permettre de passer les nodes/edges au template Jet.
			 */
			IJetHelper jetHelper = Factory.getInstance().newJetHelper(activity.getNodes(), activity.getEdges());
			JetTemplate jetTemplate = Factory.getInstance().newJetTemplate();
			String alloyTxt = jetTemplate.generate(jetHelper);

			try {
				// on créé notre répertoire qui contiendra les fichiers générés
				new File(userDir + ".pstl2013").mkdir();
				File fichier = new File(userDir + ".pstl2013" + separator + "generated" + i + ".als");
				filesGenerated.add(fichier);
				
				FileOutputStream out = new FileOutputStream(fichier);
				out.write(alloyTxt.getBytes());
				out.close();
				
				Console.debug("Génération terminée.", this.getClass());
			}
			catch (FileNotFoundException e) {
				Console.warning("Impossible de trouver le fichier.", this.getClass());
				e.printStackTrace();
			}
			catch (IOException e) {
				Console.warning("Impossible de créer le fichier.", this.getClass());
				e.printStackTrace();
			}
			i++;
		}
		Console.debug("Générations finies.", this.getClass());
	}

	@Override
	public List<File> getGeneratedFiles() {
		return filesGenerated;
	}
}
