package com.upmc.pstl2013.properties.dynamic.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.properties.dynamic.AbstractStrategyDynamicBusiness;
import com.upmc.pstl2013.properties.dynamic.EParamType;


public class Relative extends AbstractStrategyDynamicBusiness {

	public Relative() {
		super();
		super.addInput(EParamType.NODE);
		super.addInput(EParamType.TEXT);
		super.addTextList("Before");
		super.addTextList("After");
		super.addTextList("parallel");
		super.addTextList("excluded");
		super.addInput(EParamType.NODE);
	}
	
	@Override
	public String generate(Object argument) throws JetException {
		return new RelativeTemplate().generate(argument);
	}
	
	@Override
	public String getExample() {
		return super.getExample() + "A before B, A after B, A parallel to B, A excluded to B.";
	}
}
