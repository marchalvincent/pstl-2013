package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.OrgaTemplate;
import com.upmc.pstl2013.factory.Factory;


public class Orga extends AbstractProperties {

	public Orga() {
		super(Boolean.TRUE, Factory.getInstance().newSimpleExecutionStrategy(), Factory.getInstance().newVoidStrategy());
		super.put("nbState", "20");
		super.put("attribut2orga", "tata");
		super.put("attribut3orga", "tutu");
		super.put("attribut4orga", "toto");
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new OrgaTemplate().generate(this);
	}

	@Override
	public boolean continueExecution() {
		return super.getStrategyExecution().continueExecution();
	}
}
