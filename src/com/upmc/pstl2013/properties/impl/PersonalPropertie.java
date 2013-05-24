package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.properties.Family;
import com.upmc.pstl2013.strategyExecution.ExecutionFactory;
import com.upmc.pstl2013.strategyParcours.ParcoursFactory;

/**
 * Représente le code alloy écrit à la main par l'utilisateur dans le champ associé.
 * 
 */
public class PersonalPropertie extends AbstractProperties {
	
	public PersonalPropertie() {
		// TODO voir comment on peut spécifier ni check, ni run... 
		super(Boolean.TRUE, 
				ExecutionFactory.getInstance().newSimpleExecutionStrategy(), 
				ParcoursFactory.getInstance().newVoidStrategy());
		
		// cette propriété n'a pas de dépendance car elle est exécutée toute seule.
//		super.setDependance(null);
	}

	@Override
	public String getAlloyCode() {
		return super.getString("alloyCode");
	}

	@Override
	public Family getBehavior() {
		// cette méthode ne doit pas être appelée pour la PersonalPropertie
		return null;
	}
}
