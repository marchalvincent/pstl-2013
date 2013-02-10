package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.EnoughStateTemplate;
import com.upmc.pstl2013.factory.Factory;


public class EnoughState extends AbstractProperties {

	public EnoughState() {
		super(Boolean.TRUE, Factory.getInstance().newPathStrategy());
		super.put("nbState", "1");
		super.put("incrementation", "1");
		super.put("attribut2enough", "tata");
		super.put("attribut3enough", "tutu");
		super.put("attribut4enough", "toto");
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new EnoughStateTemplate().generate(this);
	}
}
