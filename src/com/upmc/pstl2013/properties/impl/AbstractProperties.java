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
	protected Map<String, Boolean> attributesBoolean;
	
	// TODO enlever si besoin, avoir plus tard
	public static List<String> getProperties() {
		List<String> liste = new ArrayList<String>();
		liste.add("DeadLock");
		liste.add("EnoughState");
		liste.add("Orga");
		liste.add("Wf");
		return liste;
	}

	public AbstractProperties() {
		super();
		attributes = new HashMap<String, String>();
		attributesBoolean = new HashMap<String, Boolean>();
	}
	
	@Override
	public void put(String key, String value) {
		attributes.put(key, value);
	}
	
	@Override
	public void put(String key, Boolean value) {
		attributesBoolean.put(key, value);
	}
	
	@Override
	public String getString(String key) {
		return attributes.get(key);
	}
	
	@Override
	public Boolean getBoolean(String key) {
		return attributesBoolean.get(key);
	}
	
	@Override
	public Map<String, String> getStringAttributes() {
		return attributes;
	}
	
	@Override
	public Map<String, Boolean> getBooleanAttributes() {
		return attributesBoolean;
	}
}
