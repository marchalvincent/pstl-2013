package com.upmc.pstl2013.alloyGenerator.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerated;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.jet.IJetHelper;
import com.upmc.pstl2013.alloyGenerator.jet.IJetTemplate;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.umlParser.IUMLParser;
import com.upmc.pstl2013.util.PluginPath;
import com.upmc.pstl2013.util.Utils;

import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;

/**
 * Cette classe se charge de générer le fichier Alloy à partir
 * 
 */
public class AlloyGenerator implements IAlloyGenerator {

	private IUMLParser parser;
	private IFile UMLFile;
	private String dirDestination;
	private IProperties property;
	private Logger log = Logger.getLogger(AlloyGenerator.class);

	/**
	 * Constructeur
	 */
	public AlloyGenerator(IFile file, String dirDestination, IProperties property) {
		super();
		parser = Factory.getInstance().newParser(file);
		this.UMLFile = file;
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

		// 2. On récupère l'activité parsée
		Activity activity = parser.getActivities();

		String filename = UMLFile.getName().substring(0, UMLFile.getName().length() - 4);
		String pathFile = dirDestination + "gen_" + filename + "_" + property.getClass().getSimpleName() + ".als";
		log.info("Génération du fichier : " + pathFile + ".");

		// On génère le contenu Alloy
		String alloyTxt = this.getAlloyTxt(activity, property);
		FileOutputStream out = null;
		try {
			// on créé le fichier a générer
			File fichier = new File(pathFile);
			// puis on écrit le contenu dedans
			out = new FileOutputStream(fichier);
			out.write(alloyTxt.getBytes());
			out.close();
			fileGenerated = Factory.getInstance().newAlloyGenerated(fichier, property);

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
				File trueSyntax = new File(PluginPath.pluginPath + "model" + File.separator + "syntax.als");
				Utils.copyContentFile(trueSyntax, syntax);
			}

			if (!semantic.exists()) {
				File trueSemantic = new File(PluginPath.pluginPath + "model" + File.separator + "semantic.als");
				Utils.copyContentFile(trueSemantic, semantic);
			}
		} catch (Exception e) {
			log.error("Impossible de copier les fichiers : " + e.getMessage());
		}

	}

	@Override
	public void setSolution(A4Solution solution) {
		this.property.getStrategyExecution().setSolution(solution);
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
		iPropertie.putPrivate("nbNodes", Integer.toString(nodes.size()));
		iPropertie.putPrivate("nbEdges", Integer.toString(edges.size()));
		iPropertie.putPrivate("nbObjects", Integer.toString(edges.size() + nodes.size()));

		iPropertie.putPrivate("initialNode", initialNode.getName());
		iPropertie.putPrivate("finalNode", finalNode.getName());
		iPropertie.putPrivate("predicatName", this.generateNamePredicat("predicatPropertie", nodes, edges));

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

	@Override
	public String getNbState() {
		return property.getString("nbState");
	}
}
