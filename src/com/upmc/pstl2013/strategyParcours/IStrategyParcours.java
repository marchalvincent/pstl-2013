package com.upmc.pstl2013.strategyParcours;

import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;

/**
 * Interface des différents parcours de solutions.
 * 
 */
public interface IStrategyParcours {

	/**
	 * Parcours une solution Alloy.
	 * 
	 * @param ans {@link A4Solution}.
	 * @return Résultat du parcours.
	 */
	String parcours(A4Solution ans);
}
