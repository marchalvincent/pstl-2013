package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.TestTemplate;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.Behavior;


public class TestPropertie extends AbstractProperties {

	public TestPropertie() {

		super(true, Factory.getInstance().newSimpleExecutionStrategy(), Factory.getInstance().newPathStrategy());
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new TestTemplate().generate(this);
	}

	@Override
	public boolean continueExecution() {
		return false;
	}

	@Override
	public Behavior getBehavior() {
		return Behavior.BUISINESS;
	}
}
