package com.upmc.pstl2013.alloyGenerator.jet.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.alloyGenerator.jet.IJetHelper;
import com.upmc.pstl2013.properties.IProperties;

/**
 * Classe utilitaire pour le template Jet. Contiend toutes les infos nécessaires au générateur.
 */
public class JetHelper implements IJetHelper {

	private EList<ActivityNode> nodes;
	private EList<ActivityEdge> edges;
	private IProperties propertie;

	public JetHelper(EList<ActivityNode> n, EList<ActivityEdge> e, IProperties prop) {

		super();
		nodes = n;
		edges = e;
		propertie = prop;
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
	public IProperties getProperty() {
		return propertie;
	}
}
