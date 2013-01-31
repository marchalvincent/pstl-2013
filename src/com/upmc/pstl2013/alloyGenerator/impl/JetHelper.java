package com.upmc.pstl2013.alloyGenerator.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.properties.IProperties;

/**
 * Classe utilitaire pour le template Jet. Contiend toutes les infos nécessaires au générateur.
 */
public class JetHelper implements IJetHelper {

	private EList<ActivityNode> nodes;
	private EList<ActivityEdge> edges;
	private ActivityNode initialNode;
	private ActivityNode finalNode;
	private IProperties propertie;
	/**
	 * Le nom du predicat final
	 */
	private String nameFinalPredicat;

	public JetHelper(EList<ActivityNode> n, EList<ActivityEdge> e, ActivityNode init, ActivityNode fin,
			IProperties prop) {

		super();
		nodes = n;
		edges = e;
		initialNode = init;
		finalNode = fin;
		propertie = prop;
		nameFinalPredicat = this.generateNamePredicat("predicatFinal");
	}

	/**
	 * Méthode récursive qui ajoutera un chiffre au nom si celui ci est déjà utilisé par les noeuds ou les arcs.
	 * 
	 * @param name
	 *            le nom temporaire du predicat. Il est sujet à modification si ce nom est déjà utilisé quelque part.
	 * @return String le nouveau final.
	 */
	private String generateNamePredicat(String name) {

		// on parcours la liste des noeuds
		for (ActivityNode activityNode : nodes) {
			if (activityNode.getName().equals(name)) {
				// si on trouve ce nom, on réessaye avec un nouveau nom
				return this.generateNamePredicat(name + "1");
			}
		}
		// on parcours la liste des arcs
		for (ActivityEdge activityEdge : edges) {
			if (activityEdge.getName().equals(name)) {
				// si on trouve ce nom, on réessaye avec un nouveau nom
				return this.generateNamePredicat(name + "1");
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
	public IProperties getPropertie() {
		return propertie;
	}

	@Override
	public String getNameFinalPredicat() {
		return nameFinalPredicat;
	}
}
