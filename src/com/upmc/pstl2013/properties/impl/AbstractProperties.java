package com.upmc.pstl2013.properties.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IAttribute;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.strategy.IStrategy;

/**
 * Représente la classe mère de toutes les propriétés de vérification Alloy.
 * 
 */
public abstract class AbstractProperties implements IProperties {

	private List<IAttribute> attributes;
	private Boolean isCheck;
	private IStrategy strategy;
	
	// TODO enlever si besoin, avoir plus tard
	public static List<String> getProperties() {
		List<String> liste = new ArrayList<String>();
		liste.add(DeadLock.class.getSimpleName());
		liste.add(EnoughState.class.getSimpleName());
		liste.add(Orga.class.getSimpleName());
		liste.add(Wf.class.getSimpleName());
		return liste;
	}

	public AbstractProperties(Boolean isCheck, IStrategy strategy) {
		super();
		attributes = new ArrayList<IAttribute>();
		this.isCheck = isCheck;
		this.strategy = strategy;
	}
	
	@Override
	public void putPrivate(String key, String value) {
		attributes.add(Factory.getInstance().newAttribute(key, value, Boolean.TRUE));
	}
	
	@Override
	public void put(String key, String value) {
		attributes.add(Factory.getInstance().newAttribute(key, value, Boolean.FALSE));
	}
	
	@Override
	public void put(String key, Boolean value) {
		attributes.add(Factory.getInstance().newAttribute(key, value, Boolean.FALSE));
	}
	
	@Override
	public String getString(String key) {
		for (IAttribute iAttribute : attributes) {
			if (iAttribute.getKey().equals(key) && iAttribute.getValue() instanceof String) {
				return (String) iAttribute.getValue();
			}
		}
		return null;
	}
	
	@Override
	public Boolean getBoolean(String key) {
		for (IAttribute iAttribute : attributes) {
			if (iAttribute.getKey().equals(key) && iAttribute.getValue() instanceof Boolean) {
				return (Boolean) iAttribute.getValue();
			}
		}
		return null;
	}
	
	@Override
	public Map<String, String> getStringAttributes() {
		Map<String, String> retour = new HashMap<String, String>();
		for (IAttribute iAttribute : attributes) {
			if (!iAttribute.isPrivate() && iAttribute.getValue() instanceof String) {
				retour.put(iAttribute.getKey(), (String) iAttribute.getValue());
			}
		}
		return retour;
	}
	
	@Override
	public Map<String, Boolean> getBooleanAttributes() {
		Map<String, Boolean> retour = new HashMap<String, Boolean>();
		for (IAttribute iAttribute : attributes) {
			if (!iAttribute.isPrivate() && (iAttribute.getValue() instanceof Boolean)) {
				retour.put(iAttribute.getKey(), ((Boolean) iAttribute.getValue()));
			}
		}
		return retour;
	}
	
	@Override
	public Boolean isCheck() {
		return isCheck;
	}
	
	@Override
	public IStrategy getStrategy() {
		return strategy;
	}
}
