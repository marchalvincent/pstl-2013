package com.upmc.pstl2013.strategyParcours.impl;

import com.upmc.pstl2013.strategyParcours.IStrategyParcours;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;

/**
 * Parfois, on n'a besoin de spécifier que si une execution est valide ou non. 
 * C'est pourquoi on n'a pas besoin de faire de parcours de solution.
 *
 */
public class VoidStrategy implements IStrategyParcours {

	public VoidStrategy() {
		super();
	}
	
	@Override
	public String parcours(A4Solution ans) {
		return "";
	}
	
	@Override
	public IStrategyParcours clone() throws CloneNotSupportedException {
		return (VoidStrategy) super.clone();
	}
}
