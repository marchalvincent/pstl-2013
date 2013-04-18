package com.upmc.pstl2013.properties.dynamic.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.properties.dynamic.AbstractStrategyDynamicBusiness;
import com.upmc.pstl2013.properties.dynamic.EParamType;


public class Existence extends AbstractStrategyDynamicBusiness {

	public Existence() {
		super();
		super.addInput(EParamType.NODE);
		super.addInput(EParamType.TEXT);
		super.addInput(EParamType.NUMBER);
		super.addTextList("executed exactly");
		super.addTextList("executed more than");
		super.addTextList("executed less than");
	}

	@Override
	public String generate(Object argument) throws JetException {
		return new ExistenceTemplate().generate(argument);
	}
	
	@Override
	public String getExample() {
		return super.getExample() + "A is executed more/less than (or exactly) 2 time.";
	}
}
