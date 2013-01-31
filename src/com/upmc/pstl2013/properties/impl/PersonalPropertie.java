package com.upmc.pstl2013.properties.impl;

import java.util.Map;
import com.upmc.pstl2013.properties.IProperties;

/**
 * Représente le code alloy écrit à la main par l'utilisateur dans le champ associé.
 * 
 */
public class PersonalPropertie extends AbstractProperties implements IProperties {
	
	public PersonalPropertie(Map<String, String> prop) {
		super(prop);
	}

	@Override
	public String getAlloyCode() {
		return attributes.get("alloyCode");
	}
}
