package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.impl.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.OrgaTemplate;


public class Orga extends AbstractProperties {

	public Orga() {
		super();
		attributes.put("inc", "20");
		attributes.put("attribut2orga", "tata");
		attributes.put("attribut3orga", "tutu");
		attributes.put("attribut4orga", "toto");
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new OrgaTemplate().generate(this);
	}
}
