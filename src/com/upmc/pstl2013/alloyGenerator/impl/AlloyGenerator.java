package com.upmc.pstl2013.alloyGenerator.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.alloyGenerator.GeneratorFactory;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerated;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.jet.IJetHelper;
import com.upmc.pstl2013.alloyGenerator.jet.IJetTemplate;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.util.Utils;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;

/**
 * Cette classe se charge de générer le fichier Alloy à partir
 * 
 */
public class AlloyGenerator implements IAlloyGenerator {

	private Activity activity;
	private String dirDestination;
	private IProperties property;
	private Logger log = Logger.getLogger(AlloyGenerator.class);

	/**
	 * Constructeur
	 */
	public AlloyGenerator(Activity activity, String dirDestination, IProperties property) {
		super();
		this.activity = activity;
		this.dirDestination = dirDestination;
		this.property = property;
	}

	@Override
	public boolean hasNext() {
		return property.continueExecution();
	}

	@Override
	public IAlloyGenerated next() {
		try {
			return generateFile();
		} catch (JetException e) {
			log.error("Impossible de générer le fichier Alloy. " + e.toString(), e);
		}
		return null;
	}

	@Override
	public void remove() {}

	@Override
	public IAlloyGenerated generateFile() throws JetException {
		IAlloyGenerated fileGenerated = null;

		// 1. On créé le répertoire qui contiendra les fichiers Alloy s'il n'existe pas.
		new File(dirDestination).mkdir();

		// 2. On y ajoute les fichiers requis
		this.addNeddedFile();

		String filename = activity.getName().substring(0, activity.getName().length() - 4);
		String pathFile = dirDestination + "gen_" + filename + "_" + property.getName() + ".als";
		log.info("Génération du fichier : " + pathFile + ".");

		// On fait la réduction des diagrammes d'activité
		Activity activityOfProperty = property.reduceActivityDiagram(activity);
		
		// On génère le contenu Alloy
		String alloyTxt = this.getAlloyTxt(activityOfProperty, property);
		FileOutputStream out = null;
		try {
			// on créé le fichier a générer
			File fichier = new File(pathFile);
			// puis on écrit le contenu dedans
			out = new FileOutputStream(fichier);
			out.write(alloyTxt.getBytes());
			out.close();
			fileGenerated = GeneratorFactory.getInstance().newAlloyGenerated(fichier, property);

		} catch (FileNotFoundException e) {
			log.error("Impossible de trouver le fichier : " + e.toString(), e);
		} catch (IOException e) {
			log.error("Impossible de créer le fichier : " + e.toString(), e);
		} finally {
			// on ferme le flux en cas de problèmes...
			try {if (out != null) out.close();} catch (IOException e) {}
		}

		log.info("Générations terminées.");
		return fileGenerated;
	}

	private void addNeddedFile() {

		try {
			// 1. On check que syntax et semantic sont là
			File syntax = new File(dirDestination + "syntax.als");
			File semantic = new File(dirDestination + "semantic.als");

			// 2. S'il ne sont pas là on les copie
			if (!syntax.exists()) {
				File trueSyntax = new File(Utils.pluginPath + "model" + File.separator + "syntax.als");
				Utils.copyContentFile(trueSyntax, syntax);
			}

			if (!semantic.exists()) {
				File trueSemantic = new File(Utils.pluginPath + "model" + File.separator + "semantic.als");
				Utils.copyContentFile(trueSemantic, semantic);
			}
		} catch (Exception e) {
			log.error("Impossible de copier les fichiers : " + e.getMessage());
		}

	}

	@Override
	public void setSolution(A4Solution solution) {
		this.property.setSolution(solution);
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

		EList<ActivityNode> nodes = activity.getNodes();
		EList<ActivityEdge> edges = activity.getEdges();
		String initialNodes = this.getNodeByType(nodes, "InitialNode");
		String finalNodes = this.getNodeByType(nodes, "ActivityFinalNode");

		// on ajoute à la propriété les infos de base...
		iPropertie.putPrivate("nbNodes", Integer.toString(nodes.size()));
		iPropertie.putPrivate("nbEdges", Integer.toString(edges.size()));
		iPropertie.putPrivate("nbObjects", Integer.toString(edges.size() + nodes.size()));
		
		iPropertie.putPrivate("initialNode", initialNodes);
		iPropertie.putPrivate("finalNode", finalNodes);
		
		iPropertie.putPrivate("predicatName", this.generateNamePredicat("predicatName", nodes, edges));

		// on utilise un objet helper qui va nous permettre de passer les nodes/edges et la propriété au template Jet.
		IJetHelper jetHelper = GeneratorFactory.getInstance().newJetHelper(nodes, edges, iPropertie);
		IJetTemplate jetTemplate = GeneratorFactory.getInstance().newJetTemplate();
		return jetTemplate.generate(jetHelper);
	}

	/**
	 * Renvoie les noeuds rencontré dont le type est en paramètre. Null, si aucun noeud n'est
	 * trouvé.
	 * 
	 * @param nodes la liste des noeuds à parcourir.
	 * @param type le type de noeud qu'on cherche.
	 * @return une String contenant le nom des noeuds rencontrés du type spécifié.
	 */
	private String getNodeByType(EList<ActivityNode> nodes, String type) {

		StringBuilder sb = new StringBuilder();
		// on cherche le noeud selon son type
		for (ActivityNode activityNode : nodes) {
			if (activityNode.eClass().getName().equals(type)) {
				sb.append(activityNode.getName());
				sb.append("---");
			}
		}
		return sb.toString();
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

	@Override
	public String getNbState() {
		return property.getString("nbState");
	}
}
