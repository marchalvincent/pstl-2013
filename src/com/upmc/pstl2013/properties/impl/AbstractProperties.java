package com.upmc.pstl2013.properties.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.upmc.pstl2013.properties.IProperties;

/**
 * Représente la classe mère de toutes les propriétés de vérification Alloy.
 * 
 */
public abstract class AbstractProperties implements IProperties {

	private Map<String, String> properties;
	private String alloyCode;
	

	// TODO enlever si besoin, avoir plus tard
	public static List<String> getProperties() {

		List<String> liste = new ArrayList<String>();
		liste.add("DeadLock");
		liste.add("Properties2");
		return liste;
	}

	public AbstractProperties(Map<String, String> prop) {
		super();
		if (prop == null) properties = new HashMap<String, String>();
		else properties = prop;
	}

	@Override
	public String getAlloyCode() {
		return alloyCode;
	}
	
	@Override
	public void putProperties(String key, String value) {
		properties.put(key, value);
	}
	
	protected void setAlloyCode(String code) {
		alloyCode = code;
	}
}
