package com.upmc.pstl2013.properties.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.upmc.pstl2013.properties.IProperties;

public abstract class AbstractProperties implements IProperties {

	private Map<String, Map<String, String>> properties;
	
	//TODO enlever si besoin, avoir plus tard
	public static List<String> getProperties() {
		List<String> liste = new ArrayList<String>();
		liste.add("DeadLock");
		liste.add("Properties2");
		return liste;
	}
	
	public AbstractProperties(Map<String, Map<String, String>> prop) {
		super();
		properties = prop;
	}
}
