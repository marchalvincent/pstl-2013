package com.upmc.pstl2013.alloyGenerator.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityNode;

import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.umlParser.IUMLParser;

/**
 * Cette classe se charge de générer le fichier Alloy à partir 
 *
 */
public class AlloyGenerator implements IAlloyGenerator {
	
	private IInfoGenerator infoGenerator;
	private IUMLParser parser;
	private List<File> filesGenerated;
	private static Logger log = Logger.getLogger(AlloyGenerator.class);

	/**
	 * Constructeur
	 */
	public AlloyGenerator (IInfoGenerator infoGen, IUMLParser pars) {
		super();
		infoGenerator = infoGen;
		parser = pars;
		filesGenerated = new ArrayList<File>();
	}

	@Override
	public void generateFile() throws JetException {
		log.debug("Début des générations.");
		
		// 1. On créé le répertoire qui contiendra les fichiers Alloy s'il n'existe pas.
		String userDir = infoGenerator.getDestinationDirectory();
		new File(userDir).mkdir();
		
		// 2. On récupère les activités
		List<Activity> activities = parser.getActivities();
		int i = 1;

		for (Activity activity : activities) {
			log.debug("Génération du fichier n°" + i + ".");
			
			String alloyTxt = this.getAlloyTxt(activity);

			try {
				// on créé le fichier a générer
				File fichier = new File(userDir + "generated" + i + ".als");
				filesGenerated.add(fichier);
				
				// puis on écrit le contenu dedans
				FileOutputStream out = new FileOutputStream(fichier);
				out.write(alloyTxt.getBytes());
				out.close();
				
				log.debug("Génération terminée.");
			}
			catch (FileNotFoundException e) {
				log.error("Impossible de trouver le fichier : " + e.toString(),e);
			}
			catch (IOException e) {
				log.error("Impossible de créer le fichier : " + e.toString(),e);
			}
			i++;
		}
		log.debug("Générations finies.");
	}
	
	/**
	 * Fait appel a Jet et génère le contenu du fichier Alloy.
	 * @param activity 
	 * @return le contenu du fichier alloy.
	 * @throws JetException en cas d'erreur lors de la génération.
	 */
	private String getAlloyTxt(Activity activity) throws JetException {
		
		ActivityNode initialNode = null;
		EList<ActivityNode> nodes = activity.getNodes();
		
		// on cherche le noeud initial
		for (ActivityNode activityNode : nodes) {
			log.debug("Nom de la classe : " + activityNode.eClass().getName());
			if (activityNode.eClass().getName().equals("InitialNode")) {
				initialNode = activityNode;
				// au premier noeud initial rencontré, on quitte le parcours
				break;
			}
		}
		
		// on utilise un objet helper qui va nous permettre de passer les nodes/edges au template Jet.
		IJetHelper jetHelper = Factory.getInstance().newJetHelper(nodes, activity.getEdges(),
				initialNode);
		log.debug("nom du noeud initial:" + jetHelper.getInitialNode().getName());
		JetTemplate jetTemplate = Factory.getInstance().newJetTemplate();
		return jetTemplate.generate(jetHelper);
	}
	
	@Override
	public void fichiersPresents() throws FileNotFoundException {
		
		String userDir = infoGenerator.getDestinationDirectory();
		StringBuilder fichiersManquants = new StringBuilder();
		if (!new File(userDir + "semantic.als").exists())
			fichiersManquants.append(" semantic.als");
		if (!new File(userDir + "syntax.als").exists())
			fichiersManquants.append(" syntax.als");
		if (!fichiersManquants.toString().equals(""))
			throw new FileNotFoundException("Le(s) fichier(s) suivant(s) manque(nt) :" + fichiersManquants.toString() +
				". Veuillez les ajouter dans votre répertoire : " + userDir);
	}

	@Override
	public List<File> getGeneratedFiles() {
		return filesGenerated;
	}

	@Override
	public void reset() {
		filesGenerated.clear();
		parser.reset();
	}
}
