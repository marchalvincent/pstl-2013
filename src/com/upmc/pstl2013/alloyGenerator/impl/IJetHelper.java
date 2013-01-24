package com.upmc.pstl2013.alloyGenerator.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;

/**
 * Cette interface sert de conteneur pour passer plusieurs arguments
 * au template Jet qui génère le fichier Alloy.
 *
 */
public interface IJetHelper {
	
	/**
	 * Renvoie vrai si le JetHelper est bien formé.
	 * @return
	 */
	boolean isCorrect();

	/**
	 * Renvoie la liste des {@link ActivityNode}.
	 * @return une {@link EList}.
	 */
	EList<ActivityNode> getNodes();
	
	/**
	 * Renvoie la liste des {@link ActivityEdge}.
	 * @return une {@link EList}.
	 */
	EList<ActivityEdge> getEdges();
	
	/**
	 * Renvoie le noeud initial.
	 * @return {@link ActivityNode}.
	 */
	ActivityNode getInitialNode();
}
