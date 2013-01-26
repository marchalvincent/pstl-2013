package com.upmc.pstl2013.properties.impl;

import java.util.Map;

import com.upmc.pstl2013.properties.IProperties;

/**
 * Représente le code alloy écrit à la main par l'utilisateur 
 * dans le champ fait pour.
 * 
 */
public class PersonalPropertie extends AbstractProperties implements IProperties {

	public PersonalPropertie(Map<String, String> prop) {
		super(prop);
		super.alloyCode = prop.get("alloyCode");
	}
}
