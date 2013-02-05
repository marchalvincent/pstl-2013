package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.DeadLockTemplate;
import com.upmc.pstl2013.factory.Factory;

/**
 * Représente une propriété de vérification alloy.
 * 
 */
public class DeadLock extends AbstractProperties {
	
	public DeadLock() {
		// le DeadLock est un check avec la strategie PathStrategy
		super(Boolean.TRUE, Factory.getInstance().newPathStrategy());
		super.put("inc", "20");
		super.put("attribut2deadlock", "tata");
		super.put("attribut3deadlock", "tutu");
		super.put("attribut4deadlock", "toto");
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new DeadLockTemplate().generate(this);
	}
}
