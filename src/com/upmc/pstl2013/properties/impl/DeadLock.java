package com.upmc.pstl2013.properties.impl;

import java.util.Map;


public class DeadLock extends AbstractProperties {

	
	public DeadLock(Map<String, Map<String, String>> properties) {
		super(properties);
	}
	
	@Override
	public String getAlloyCode() {
		return "run testAll for 0 but 20 State, 15 Object, 5 ActivityNode, 4 ActivityEdge expect 1";
	}
}
