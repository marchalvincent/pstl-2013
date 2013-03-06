package com.upmc.pstl2013.properties.dynamic;

import com.upmc.pstl2013.properties.dynamic.impl.TestDynamics;

/**
 * Représente la factory des stratégies de génération alloy pour les propriétés créées dynamiquement
 * par l'utilisateur.
 *
 */
public class DynamicFactory {
	
	private static final DynamicFactory instance = new DynamicFactory();

	private DynamicFactory() {}

	/**
	 * Renvoie l'unique instance de la DynamicFactory.
	 * 
	 * @return {@link DynamicFactory}.
	 */
	public static DynamicFactory getInstance() {
		return instance;
	}
	
	public AbstractStrategyDynamicBusiness newTestDynamics() {
		return new TestDynamics();
	}
}
