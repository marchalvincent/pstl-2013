package com.upmc.pstl2013.properties.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.upmc.pstl2013.properties.IProperties;

/**
 * Se charge de créer les propriétés.
 *
 */
public class PropertiesFactory {

	/**
	 * Méthode static pour la création des propriétés.
	 * @param  prop un String représentant la propriété.
	 * @return {@link IProperties}.
	 */
	public static List<IProperties> createPropertie(Map<String, Map<String, String>> prop) {
		List<IProperties> properties = new ArrayList<IProperties>();
		// TODO pour les test, enlever une fois fini
		if (prop == null) {
			properties.add(new DeadLock(null));
			return properties;
		}
		// pour chaque nom de propriété, on récupère l'association de clé-valeur
		Set<String> allKeys = prop.keySet();
		for (String propertieName : allKeys) {
			Map<String, String> cleVal = prop.get(propertieName);
			// puis on créer l'objet propertie
			if (propertieName.equals("DeadLock")) properties.add(new DeadLock(cleVal));
			// ici on peut ajouter les nouvelles propriétés
		}
		return properties;
	}
}
