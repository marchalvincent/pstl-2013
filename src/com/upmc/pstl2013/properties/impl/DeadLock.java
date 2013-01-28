package com.upmc.pstl2013.properties.impl;

import java.util.Map;

/**
 * Représente une propriété de vérification alloy. Exécute un "run".
 * 
 */
public class DeadLock extends AbstractProperties {
	
	public DeadLock(Map<String, String> properties) {
		super(properties);
		super.alloyCode = "run testAll for 0 but 20 State, 15 Object, 5 ActivityNode, 4 ActivityEdge expect 1";
	}
}
