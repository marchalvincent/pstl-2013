package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.impl.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.WfTemplate;


public class Wf extends AbstractProperties {

	public Wf() {
		super();
		attributes.put("predicatName", "");
		attributes.put("attribut3wf", "tutu");
		attributes.put("attribut4wf", "toto");
		attributes.put("attribut4wf", "toto");
		attributesBoolean.put("initial", Boolean.TRUE);
		attributesBoolean.put("final", Boolean.TRUE);
		attributesBoolean.put("merge", Boolean.TRUE);
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new WfTemplate().generate(this);
	}
}
