package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.WfTemplate;
import com.upmc.pstl2013.factory.Factory;


public class Wf extends AbstractProperties {

	public Wf() {
		super(Boolean.FALSE, Factory.getInstance().newPathStrategy());
		super.putPrivate("predicatName", "");
		super.put("initial", Boolean.TRUE);
		super.put("final", Boolean.TRUE);
		super.put("merge", Boolean.TRUE);
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new WfTemplate().generate(this);
	}
}
