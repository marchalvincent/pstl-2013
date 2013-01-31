package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.impl.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.DeadLockTemplate;

/**
 * Représente une propriété de vérification alloy.
 * 
 */
public class DeadLock extends AbstractProperties {
	
	public DeadLock() {
		super();
		attributes.put("inc", "20");
		attributes.put("attribut2deadlock", "tata");
		attributes.put("attribut3deadlock", "tutu");
		attributes.put("attribut4deadlock", "toto");
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new DeadLockTemplate().generate(this);
	}
}
