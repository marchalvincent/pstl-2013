package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.properties.IAttribute;

/**
 * Représente un attribut d'une propriété.
 *
 */
public class Attribute implements IAttribute {
	
	private String key;
	private Object value;
	private Boolean isPrivate;
	
	public Attribute(String key, Object value, Boolean isPrivate) {
		super();
		this.key = key;
		this.value = value;
		this.isPrivate = isPrivate;
	}
	
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public Object getValue() {
		return value;
	}
	
	@Override
	public Boolean isPrivate() {
		return isPrivate;
	}
	
	@Override
	public IAttribute clone() throws CloneNotSupportedException {
		return (IAttribute) super.clone();
	}
}
