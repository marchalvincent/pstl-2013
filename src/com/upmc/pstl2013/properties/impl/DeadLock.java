package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.impl.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.DeadLockTemplate;

/**
 * Représente une propriété de vérification alloy. Exécute un "run".
 * 
 */
public class DeadLock extends AbstractProperties {
	
	public DeadLock() {
		super();
		attributes.put("inc", "20");
		attributes.put("attribut2", "tata");
		attributes.put("attribut3", "tutu");
		attributes.put("attribut4", "toto");
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new DeadLockTemplate().generate(this);
	}
}
