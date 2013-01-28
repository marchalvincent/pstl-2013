package com.upmc.pstl2013.alloyGenerator.impl;

import java.util.List;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.properties.IProperties;

/**
 * Cette interface sert de conteneur pour passer plusieurs arguments au template Jet qui génère le
 * fichier Alloy.
 * 
 */
public interface IJetHelper {

	/**
	 * Renvoie la liste des {@link ActivityNode}.
	 * 
	 * @return une {@link EList}.
	 */
	EList<ActivityNode> getNodes();

	/**
	 * Renvoie la liste des {@link ActivityEdge}.
	 * 
	 * @return une {@link EList}.
	 */
	EList<ActivityEdge> getEdges();

	/**
	 * Renvoie le noeud initial.
	 * 
	 * @return {@link ActivityNode}.
	 */
	ActivityNode getInitialNode();

	/**
	 * Renvoie le noeud final.
	 * 
	 * @return {@link ActivityNode}.
	 */
	ActivityNode getFinalNode();

	/**
	 * Renvoie les propriétés.
	 * 
	 * @return {@link IProperties}.
	 */
	List<IProperties> getProperties();

	/**
	 * Renvoie le nom du predicat final. Il faut que ce nom soit différent des autres noms de "node"
	 * ou "edge".
	 * 
	 * @return {@code String}.
	 */
	String getNameFinalPredicat();
}
