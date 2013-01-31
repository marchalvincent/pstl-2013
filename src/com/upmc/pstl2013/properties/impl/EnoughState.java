package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.impl.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.EnoughStateTemplate;


public class EnoughState extends AbstractProperties {

	public EnoughState() {
		super();
		attributes.put("inc", "20");
		attributes.put("attribut2enough", "tata");
		attributes.put("attribut3enough", "tutu");
		attributes.put("attribut4enough", "toto");
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new EnoughStateTemplate().generate(this);
	}
}
