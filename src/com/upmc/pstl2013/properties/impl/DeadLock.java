package com.upmc.pstl2013.properties.impl;

import java.util.Map;

/**
 * Représente une propriété de vérification alloy. Exécute un "run".
 * 
 */
public class DeadLock extends AbstractProperties {
	
	public DeadLock(Map<String, String> attributes) {
		super(attributes);
		attributes.put("attribut1", "toto");
		attributes.put("attribut2", "tata");
		attributes.put("attribut3", "tutu");
	}

	@Override
	public String getAlloyCode() {

		return null;
	}
}
