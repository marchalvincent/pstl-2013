package com.upmc.pstl2013.alloyGenerator.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;

/**
 * Classe utilitaire pour le template Jet.
 */
public class JetHelper implements IJetHelper {
	
	private EList<ActivityNode> nodes;
	private EList<ActivityEdge> edges;
	private ActivityNode initialNode;
	
	public JetHelper (EList<ActivityNode> n, EList<ActivityEdge> e, ActivityNode init) {
		super();
		nodes = n;
		edges = e;
		initialNode = init;
	}
	
	@Override
	public boolean isCorrect() {
		if (initialNode == null)
			return false;
		return true;
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
}
