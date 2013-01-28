package com.upmc.pstl2013.strategy;

import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;

/**
 * Interface des différents parcours de solutions.
 * 
 */
public interface IStrategy {

	/**
	 * Parcours une solution Alloy.
	 * 
	 * @param ans
	 *            {@link A4Solution}.
	 * @return
	 */
	String parcours(A4Solution ans);
}
