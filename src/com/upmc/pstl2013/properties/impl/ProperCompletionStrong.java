package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.ProperCompletionStrongTemplate;
import com.upmc.pstl2013.properties.Family;
import com.upmc.pstl2013.strategyExecution.ExecutionFactory;
import com.upmc.pstl2013.strategyParcours.ParcoursFactory;


public class ProperCompletionStrong extends AbstractProperties {

	public ProperCompletionStrong() {
		super(Boolean.TRUE,
				ExecutionFactory.getInstance().newSimpleExecutionStrategy(), 
				ParcoursFactory.getInstance().newVoidStrategy());
		
		super.setDependance(EnoughState.class);
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new ProperCompletionStrongTemplate().generate(this);
	}

	@Override
	public Family getBehavior() {
		return Family.SOUDNESS;
	}
}
