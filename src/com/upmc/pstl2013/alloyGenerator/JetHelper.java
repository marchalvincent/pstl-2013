package com.upmc.pstl2013.alloyGenerator;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;

/**
 * Classe utilitaire pour le template Jet.
 */
public class JetHelper implements IJetHelper {
	
	private EList<ActivityNode> nodes;
	private EList<ActivityEdge> edges;
	
	public JetHelper (EList<ActivityNode> n, EList<ActivityEdge> e) {
		super();
		nodes = n;
		edges = e;
	}

	@Override
	public EList<ActivityNode> getNodes() {
		return nodes;
	}

	@Override
	public EList<ActivityEdge> getEdges() {
		return edges;
	}
}
