package com.upmc.pstl2013.alloyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.uml2.uml.Activity;

import com.upmc.pstl2013.alloyGenerator.interfaces.IAlloyGenerator;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.interfaces.IUMLFileChooser;
import com.upmc.pstl2013.util.Console;

/**
 * Cette classe se charge de générer le fichier Alloy à partir 
 *
 */
public class AlloyGenerator implements IAlloyGenerator {

	/**
	 * Constructeur
	 */
	public AlloyGenerator () {
		super();
	}

	@Override
	public void generateFile(IUMLFileChooser fileChooser) {
		// on créé le parser et on récupère les activités
		IUMLParser parser = Factory.getInstance().newParser();
		List<Activity> activities = parser.getActivities(fileChooser);
		int i = 1;

		for (Activity activity : activities) {
			Console.debug("Génération du fichier n°" + i + ".", this.getClass());
			/*
			 * pour chaque activité on créé un objet helper qui va nous 
			 * permettre de passer les nodes/edges au template Jet.
			 */
			IJetHelper jetHelper = new JetHelper(activity.getNodes(), activity.getEdges());
			JetTemplate jetTemplate = new JetTemplate();
			String alloyTxt = jetTemplate.generate(jetHelper);

			try {
				File fichier = new File("generated" + i + ".als");
				fichier.createNewFile();
				
				FileOutputStream out = new FileOutputStream(fichier);
				out.write(alloyTxt.getBytes());
				out.close();
				
				Console.debug("Génération terminée.", this.getClass());
			}
			catch (FileNotFoundException e) {
				Console.warning("Impossible de trouver le fichier.", this.getClass());
			}
			catch (IOException e) {
				Console.warning("Impossible de créer le fichier.", this.getClass());
			}
			i++;
		}
	}
}
