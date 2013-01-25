package com.upmc.pstl2013.properties.impl;

import java.util.Map;

import com.upmc.pstl2013.properties.IProperties;

public abstract class AbstractProperties implements IProperties {

	private Map<String, Map<String, String>> properties;
	
	public AbstractProperties(Map<String, Map<String, String>> prop) {
		super();
		properties = prop;
	}
}
