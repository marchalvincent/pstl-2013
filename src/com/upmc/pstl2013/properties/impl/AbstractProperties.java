package com.upmc.pstl2013.properties.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.upmc.pstl2013.properties.IProperties;

public abstract class AbstractProperties implements IProperties {

	// TODO a voir comment on gère les properties
	private Map<String, String> properties;
	protected String alloyCode;
	
	//TODO enlever si besoin, avoir plus tard
	public static List<String> getProperties() {
		List<String> liste = new ArrayList<String>();
		liste.add("DeadLock");
		liste.add("Properties2");
		return liste;
	}
	
	public AbstractProperties(Map<String, String> prop) {
		super();
		properties = prop;
	}

	@Override
	public String getAlloyCode() {
		return alloyCode;
	}
}
