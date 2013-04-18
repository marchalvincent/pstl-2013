package com.upmc.pstl2013.alloyGenerator.jet;

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
	 * Renvoie la {@link IProperties}.
	 * 
	 * @return une {@link IProperties}.
	 */
	IProperties getProperty();
}
