package com.upmc.pstl2013.alloyGenerator.impl;

import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;

import com.upmc.pstl2013.properties.IProperties;

/**
 * Classe utilitaire pour le template Jet.
 */
public class JetHelper implements IJetHelper {

	private EList<ActivityNode> nodes;
	private EList<ActivityEdge> edges;
	private ActivityNode initialNode;
	private ActivityNode finalNode;
	private List<IProperties> properties;

	/**
	 * Le nom du predicat final
	 */
	private String nameFinalPredicat;


	public JetHelper (EList<ActivityNode> n, EList<ActivityEdge> e, 
			ActivityNode init, ActivityNode fin, List<IProperties> prop) {
		super();
		nodes = n;
		edges = e;
		initialNode = init;
		finalNode = fin;
		properties = prop;
		nameFinalPredicat = this.generateNamePredicat(nodes, edges, "predicatFinal");
	}

	/**
	 * Méthode récursive qui ajoutera un chiffre au nom si celui ci est déjà utilisé.
	 * @param nodes2 la liste des noeuds.
	 * @param edges2 la liste des arcs.
	 * @param name le nom temporaire du predicat. Il est sujet à modification si on trouve ce nom quelque part.
	 * @return String le nouveau nom.
	 */
	private String generateNamePredicat(EList<ActivityNode> nodes2,	EList<ActivityEdge> edges2, String name) {
		
		// on parcours la liste des noeuds
		for (ActivityNode activityNode : nodes2) {
			if (activityNode.getName().equals(name)) {
				// si on trouve ce nom, on réessaye avec un nouveau nom
				return this.generateNamePredicat(nodes2, edges2, name + "1");
			}
		}

		// on parcours la liste des arcs
		for (ActivityEdge activityEdge : edges2) {
			if (activityEdge.getName().equals(name)) {
				// si on trouve ce nom, on réessaye avec un nouveau nom
				return this.generateNamePredicat(nodes2, edges2, name + "1");
			}
		}
		
		// si on n'a trouvé aucun nom, on peut l'utiliser !
		return name;
	}

	@Override
	public EList<ActivityNode> getNodes() {
		return nodes;
	}

	@Override
	public EList<ActivityEdge> getEdges() {
		return edges;
	}

	@Override
	public ActivityNode getInitialNode() {
		return initialNode;
	}

	@Override
	public ActivityNode getFinalNode() {
		return finalNode;
	}

	@Override
	public List<IProperties> getProperties() {
		return properties;
	}

	@Override
	public String getNameFinalPredicat() {
		return nameFinalPredicat;
	}
}
