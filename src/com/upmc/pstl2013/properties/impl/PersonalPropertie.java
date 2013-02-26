package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.Behavior;

/**
 * Représente le code alloy écrit à la main par l'utilisateur dans le champ associé.
 * 
 */
public class PersonalPropertie extends AbstractProperties {
	
	public PersonalPropertie() {
		//TODO voir comment on peut spécifier ni check, ni run... 
		super(Boolean.TRUE, Factory.getInstance().newSimpleExecutionStrategy(), Factory.getInstance().newVoidStrategy());
	}

	@Override
	public String getAlloyCode() {
		return super.getString("alloyCode");
	}

	@Override
	public boolean continueExecution() {
		return super.getStrategyExecution().continueExecution();
	}

	@Override
	public Behavior getBehavior() {
		// cette méthode ne doit pas être appelée pour la PersonalPropertie
		return null;
	}
}
