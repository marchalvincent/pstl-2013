package com.upmc.pstl2013.alloyGenerator.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.jet.IJetTemplate;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.infoGenerator.IInfoGenerator;
import com.upmc.pstl2013.properties.IProperties;
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
	public AlloyGenerator(IInfoGenerator infoGen, IUMLParser pars) {
		super();
		infoGenerator = infoGen;
		parser = pars;
		filesGenerated = new ArrayList<File>();
	}

	@Override
	public void generateFile() throws JetException {
		int i;
		
		// 1. On créé le répertoire qui contiendra les fichiers Alloy s'il n'existe pas.
		String userDir = infoGenerator.getDestinationDirectory();
		new File(userDir).mkdir();
		// 2. On récupère les activités groupées par fichier et les propriétés
		Map<String, List<Activity>> activities = parser.getActivities();
		List<IProperties> properties = Factory.getInstance().newPropertie(infoGenerator.getAttributes());
		
		log.info("Début des générations pour " + properties.size() + " propriété(s).");
		
		// 3a. Pour chaque fichier
		Set<String> names = activities.keySet();
		for (String nameFile : names) {
			i = 0;
			List<Activity> act = activities.get(nameFile);
			// 3b. Pour chaque activité
			for (Activity activity : act) {
				i++;
				// 3c. Pour chaque propriété
				for (IProperties iPropertie : properties) {
					String pathFile = userDir + "gen_" + nameFile + "_" + i + "_" + iPropertie.getClass().getSimpleName() + ".als";
					log.info("Génération du fichier : " + pathFile + ".");
					
					// On génère le contenu Alloy
					String alloyTxt = this.getAlloyTxt(activity, iPropertie);
					FileOutputStream out = null;
					try {
						// on créé le fichier a générer
						File fichier = new File(pathFile);
						filesGenerated.add(fichier);
						// puis on écrit le contenu dedans
						out = new FileOutputStream(fichier);
						out.write(alloyTxt.getBytes());
						out.close();
					} catch (FileNotFoundException e) {
						log.error("Impossible de trouver le fichier : " + e.toString(), e);
					} catch (IOException e) {
						log.error("Impossible de créer le fichier : " + e.toString(), e);
					} finally {
						// on ferme le flux en cas de problèmes...
						try {if (out != null) out.close();} catch (IOException e) {}
					}
				}
			}
		}
		log.info("Générations terminées.");
	}

	@Override
	public void fichiersPresents() throws FileNotFoundException {

		String userDir = infoGenerator.getDestinationDirectory();
		StringBuilder fichiersManquants = new StringBuilder();
		if (!new File(userDir + "semantic.als").exists()) fichiersManquants.append(" semantic.als");
		if (!new File(userDir + "syntax.als").exists()) fichiersManquants.append(" syntax.als");
		if (!fichiersManquants.toString().equals("")) throw new FileNotFoundException(
				"Le(s) fichier(s) suivant(s) manque(nt) :" + fichiersManquants.toString()
						+ ". Veuillez les ajouter dans votre répertoire : " + userDir);
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

	/**
	 * Fait appel a Jet et génère le contenu du fichier Alloy.
	 * 
	 * @param activity
	 *             l'{@link Activity} du fichier UML.
	 * @param iPropertie
	 *             la {@link IPropertie} à générer.
	 * @return String le contenu du fichier alloy.
	 * @throws JetException
	 *             en cas d'erreur lors de la génération.
	 */
	private String getAlloyTxt(Activity activity, IProperties iPropertie) throws JetException {

		EList<ActivityNode> nodes = this.cleanNodes(activity.getNodes());
		EList<ActivityEdge> edges = this.cleanEdges(activity.getEdges());
		ActivityNode initialNode = this.getNodeByType(nodes, "InitialNode");
		ActivityNode finalNode = this.getNodeByType(nodes, "ActivityFinalNode");
		
		// on ajoute à la propriété les infos de base...
		iPropertie.put("nbNodes", Integer.toString(nodes.size()));
		iPropertie.put("nbEdges", Integer.toString(edges.size()));
		iPropertie.put("nbObjects", Integer.toString(edges.size() + nodes.size()));
		
		iPropertie.put("initialNode", initialNode.getName());
		iPropertie.put("finalNode", finalNode.getName());
		iPropertie.put("predicatName", this.generateNamePredicat("predicatPropertie", nodes, edges));
		
		// on utilise un objet helper qui va nous permettre de passer les nodes/edges et la propriété au template Jet.
		IJetHelper jetHelper = Factory.getInstance().newJetHelper(nodes, edges, iPropertie);
		IJetTemplate jetTemplate = Factory.getInstance().newJetTemplate();
		return jetTemplate.generate(jetHelper);
	}

	/**
	 * Renvoie le premier noeud rencontré dont le type est en paramètre. Null, si aucun noeud n'est
	 * trouvé.
	 * 
	 * @param nodes la liste des noeuds à parcourir.
	 * @param type le type de noeud qu'on cherche.
	 * @return {@link ActivityNode} le premier noeud rencontré du type spécifié ou null si aucun noeud n'est trouvé.
	 */
	private ActivityNode getNodeByType(EList<ActivityNode> nodes, String type) {

		// on cherche le noeud selon son type
		for (ActivityNode activityNode : nodes) {
			if (activityNode.eClass().getName().equals(type)) {
				return activityNode;
			}
		}
		return null;
	}

	/**
	 * Nettoie les noms des noeuds par rapport à la syntax d'Alloy.
	 * 
	 * @param nodes la liste des noeuds à nettoyer.
	 * @return une liste d'{@link ActivityNode}.
	 */
	private EList<ActivityNode> cleanNodes(EList<ActivityNode> nodes) {
		for (ActivityNode activityNode : nodes) {
			activityNode.setName(activityNode.getName().replace("-", ""));
		}
		return nodes;
	}
	
	/**
	 * Nettoie les noms des arcs par rapport à la syntax d'Alloy.
	 * 
	 * @param egdes la liste des arcs à nettoyer.
	 * @return une liste d'{@link ActivityEdge}.
	 */
	private EList<ActivityEdge> cleanEdges(EList<ActivityEdge> egdes) {
		for (ActivityEdge activityEdges : egdes) {
			activityEdges.setName(activityEdges.getName().replace("-", ""));
		}
		return egdes;
	}

	/**
	 * Méthode récursive qui ajoutera un chiffre au nom si celui ci est déjà utilisé par les noeuds ou les arcs.
	 * 
	 * @param name
	 *            le nom temporaire du predicat. Il est sujet à modification si ce nom est déjà utilisé quelque part.
	 * @param nodes la liste des {@link ActivityNode}.
	 * @param edges la liste des {@link ActivityEdge}.
	 * @return String le nouveau final.
	 */
	private String generateNamePredicat(String name, EList<ActivityNode> nodes, EList<ActivityEdge> edges) {

		// on parcours la liste des noeuds
		for (ActivityNode activityNode : nodes) {
			if (activityNode.getName().equals(name)) {
				// si on trouve ce nom, on réessaye avec un nouveau nom
				return this.generateNamePredicat(name + "1", nodes, edges);
			}
		}
		// on parcours la liste des arcs
		for (ActivityEdge activityEdge : edges) {
			if (activityEdge.getName().equals(name)) {
				// si on trouve ce nom, on réessaye avec un nouveau nom
				return this.generateNamePredicat(name + "1", nodes, edges);
			}
		}
		// si on n'a trouvé aucun nom, on peut l'utiliser !
		return name;
	}
}
