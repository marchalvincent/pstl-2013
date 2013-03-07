package com.upmc.pstl2013.properties.dynamic;

import com.upmc.pstl2013.properties.dynamic.impl.TestDynamics1;
import com.upmc.pstl2013.properties.dynamic.impl.TestDynamics2;
import com.upmc.pstl2013.properties.dynamic.impl.TestDynamics3;

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
	
	public AbstractStrategyDynamicBusiness newTestDynamics1() {
		return new TestDynamics1();
	}
	public AbstractStrategyDynamicBusiness newTestDynamics2() {
		return new TestDynamics2();
	}
	public AbstractStrategyDynamicBusiness newTestDynamics3() {
		return new TestDynamics3();
	}
}
