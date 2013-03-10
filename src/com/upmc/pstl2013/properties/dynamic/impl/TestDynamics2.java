package com.upmc.pstl2013.properties.dynamic.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.properties.dynamic.AbstractStrategyDynamicBusiness;


public class TestDynamics2 extends AbstractStrategyDynamicBusiness {

	@Override
	public String generate(Object argument) throws JetException {
		return new TestDynamicsTemplate().generate(argument);
	}
	
	@Override
	public String getExample() {
		return "exemple de TestDynamics2";
	}
}