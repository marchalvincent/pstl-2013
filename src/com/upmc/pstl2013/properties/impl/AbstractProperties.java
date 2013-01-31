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

	protected Map<String, String> attributes;
	

	// TODO enlever si besoin, avoir plus tard
	public static List<String> getProperties() {

		List<String> liste = new ArrayList<String>();
		liste.add("DeadLock");
		liste.add("Properties2");
		return liste;
	}

	public AbstractProperties(Map<String, String> attr) {
		super();
		if (attr == null) attributes = new HashMap<String, String>();
		else attributes = attr;
	}
	
	@Override
	public void put(String key, String value) {
		attributes.put(key, value);
	}
	
	@Override
	public String get(String key) {
		return attributes.get(key);
	}
}
