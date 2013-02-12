package com.upmc.pstl2013.strategyParcours;

import com.upmc.pstl2013.properties.IProperties;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;

/**
 * Interface des différents parcours de solutions.
 * 
 * @extends Cloneable, un {@code IStrategyParcours} doit pouvoir être cloné. Cf. {@link IProperties}.
 * 
 */
public interface IStrategyParcours extends Cloneable {

	/**
	 * Parcours une solution Alloy.
	 * 
	 * @param ans {@link A4Solution}.
	 * @return Résultat du parcours.
	 */
	String parcours(A4Solution ans);
	
	/**
	 * Méthode clone de {@link Cloneable}.
	 */
	public IStrategyParcours clone() throws CloneNotSupportedException;
}
