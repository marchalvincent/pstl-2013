package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.OptionToCompleteStrongTemplate;
import com.upmc.pstl2013.properties.Family;
import com.upmc.pstl2013.strategyExecution.ExecutionFactory;
import com.upmc.pstl2013.strategyParcours.ParcoursFactory;

/**
 * Représente une propriété de vérification alloy.
 * 
 */
public class OptionToCompleteStrong extends AbstractProperties {
	
	public OptionToCompleteStrong() {
		// le OptionToCompleteStrong est un check avec la strategie d'exécution simple, et de parcours PathStrategy
		super(Boolean.TRUE, 
				ExecutionFactory.getInstance().newSimpleExecutionStrategy(), 
				ParcoursFactory.getInstance().newPathStrategy());
		
		super.setDependance(EnoughState.class);
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new OptionToCompleteStrongTemplate().generate(this);
	}

	@Override
	public Family getBehavior() {
		return Family.SOUDNESS;
	}
}
