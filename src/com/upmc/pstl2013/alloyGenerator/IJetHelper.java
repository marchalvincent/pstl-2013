package com.upmc.pstl2013.alloyGenerator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;

/**
 * Cette interface permet au template Jet de lister les {@link ActivityNode}
 * et les {@link ActivityEdge} à générer.
 *
 */
public interface IJetHelper {

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
}
