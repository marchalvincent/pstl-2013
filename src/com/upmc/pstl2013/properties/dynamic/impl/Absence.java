package com.upmc.pstl2013.properties.dynamic.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.properties.dynamic.AbstractStrategyDynamicBusiness;
import com.upmc.pstl2013.properties.dynamic.EParamType;


public class Absence extends AbstractStrategyDynamicBusiness {

	public Absence() {
		super();
		super.addInput(EParamType.NODE);
	}
	
	@Override
	public String generate(Object argument) throws JetException {
		return new AbsenceTemplate().generate(argument);
	}
	
	@Override
	public String getExample() {
		return super.getExample() + "A is never executed.";
	}
}
