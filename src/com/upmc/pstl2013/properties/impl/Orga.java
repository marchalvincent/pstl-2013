package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.OrgaTemplate;
import com.upmc.pstl2013.properties.Behavior;
import com.upmc.pstl2013.strategyExecution.ExecutionFactory;
import com.upmc.pstl2013.strategyParcours.ParcoursFactory;


public class Orga extends AbstractProperties {

	public Orga() {
		super(Boolean.TRUE, 
				ExecutionFactory.getInstance().newSimpleExecutionStrategy(), 
				ParcoursFactory.getInstance().newVoidStrategy());
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new OrgaTemplate().generate(this);
	}

	@Override
	public Behavior getBehavior() {
		return Behavior.ORGANIZATIONAL;
	}
}
