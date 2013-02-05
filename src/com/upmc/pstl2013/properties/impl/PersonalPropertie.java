package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.properties.IProperties;

/**
 * Représente le code alloy écrit à la main par l'utilisateur dans le champ associé.
 * 
 */
public class PersonalPropertie extends AbstractProperties implements IProperties {
	
	public PersonalPropertie() {
		super(null, null);
	}

	@Override
	public String getAlloyCode() {
		return super.getString("alloyCode");
	}
}
