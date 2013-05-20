package com.upmc.pstl2013.properties.dynamic.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.properties.dynamic.AbstractStrategyDynamicBusiness;
import com.upmc.pstl2013.properties.dynamic.EParamType;


public class ExistenceBetween extends AbstractStrategyDynamicBusiness {

	public ExistenceBetween() {
		super();
		super.addInput(EParamType.NODE);
		super.addInput(EParamType.NUMBER);
		super.addInput(EParamType.NUMBER);
	}

	@Override
	public String generate(Object argument) throws JetException {
		return new ExistenceBetweenTemplate().generate(argument);
	}
	
	@Override
	public String getExample() {
		return super.getExample() + "A is executed between 3 and 4 times.";
	}
}
