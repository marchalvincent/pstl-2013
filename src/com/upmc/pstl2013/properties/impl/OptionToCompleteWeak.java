package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.OptionToCompleteWeakTemplate;
import com.upmc.pstl2013.properties.Family;
import com.upmc.pstl2013.strategyExecution.ExecutionFactory;
import com.upmc.pstl2013.strategyParcours.ParcoursFactory;

/**
 * Représente une propriété de vérification alloy.
 * 
 */
public class OptionToCompleteWeak extends AbstractProperties {
	
	public OptionToCompleteWeak() {
		// le OptionToCompleteWeak est un run avec la strategie d'exécution simple, et de parcours PathStrategy
		super(Boolean.FALSE, 
				ExecutionFactory.getInstance().newSimpleExecutionStrategy(), 
				ParcoursFactory.getInstance().newPathStrategy());
		
		super.setDependance(EnoughState.class);
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new OptionToCompleteWeakTemplate().generate(this);
	}

	@Override
	public Family getBehavior() {
		return Family.SOUDNESS;
	}
}
