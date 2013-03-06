package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.OrgaTemplate;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.Behavior;


public class Orga extends AbstractProperties {

	public Orga() {
		super(Boolean.TRUE, Factory.getInstance().newSimpleExecutionStrategy(), Factory.getInstance().newVoidStrategy());
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
