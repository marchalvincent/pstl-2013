package com.upmc.pstl2013.properties.dynamic;

import com.upmc.pstl2013.properties.dynamic.impl.Absence;
import com.upmc.pstl2013.properties.dynamic.impl.Existence;
import com.upmc.pstl2013.properties.dynamic.impl.ExistenceBetween;
import com.upmc.pstl2013.properties.dynamic.impl.Presence;
import com.upmc.pstl2013.properties.dynamic.impl.Relative;

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

	public AbstractStrategyDynamicBusiness newAbsence() {
		return new Absence();
	}
	
	public AbstractStrategyDynamicBusiness newPresence() {
		return new Presence();
	}
	
	public AbstractStrategyDynamicBusiness newExistence() {
		return new Existence();
	}
	
	public AbstractStrategyDynamicBusiness newExistenceBetween() {
		return new ExistenceBetween();
	}
	
	public AbstractStrategyDynamicBusiness newRelative() {
		return new Relative();
	}
	
	
}
