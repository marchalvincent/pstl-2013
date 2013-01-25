package com.upmc.pstl2013.properties.impl;

import java.util.Map;

import com.upmc.pstl2013.properties.IProperties;

/**
 * Se charge de créer les propriétés.
 *
 */
public class PropertiesFactory {

	/**
	 * Méthode static pour la création des propriétés.
	 * @param  propertie un String représentant la propriété.
	 * @return {@link IProperties}.
	 */
	public static IProperties createPropertie(Map<String, Map<String, String>> propertie) {
		if (propertie.equals("DeadLock")) return new DeadLock(propertie);
		else return new DeadLock(propertie);
	}
}
