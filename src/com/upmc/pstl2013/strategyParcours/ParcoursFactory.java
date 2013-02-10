package com.upmc.pstl2013.strategyParcours;

import com.upmc.pstl2013.strategyParcours.impl.PathStrategy;
import com.upmc.pstl2013.strategyParcours.impl.VoidStrategy;

/**
 * La factory des stratégies de parcours.
 *
 */
public class ParcoursFactory {

	private static ParcoursFactory instance = new ParcoursFactory();

	public static ParcoursFactory getInstance() {
		return instance;
	}

	private ParcoursFactory() {}

	public IStrategyParcours newPathStrategy() {
		return new PathStrategy();
	}

	public IStrategyParcours newVoidStrategy() {
		return new VoidStrategy();
	}
}
