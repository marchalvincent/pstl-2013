package com.upmc.pstl2013.properties.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;
import com.upmc.pstl2013.properties.IProperties;

/**
 * Se charge de créer les propriétés.
 * 
 */
public class PropertiesFactory {

	private static Logger log = Logger.getLogger(PropertiesFactory.class);

	/**
	 * Méthode static pour la création des propriétés.
	 * 
	 * @param prop
	 *            une Map<String, Map<String, String>> associant le nom de la propriété à son ensemble
	 *            de clé-valeur personnalisées.
	 * @return une liste de {@link IProperties}.
	 */
	public static List<IProperties> createPropertie(Map<String, Map<String, String>> prop) {

		List<IProperties> properties = new ArrayList<IProperties>();
		
		// pour chaque nom de propriété, on récupère l'association de clé-valeur
		Set<String> allKeys = prop.keySet();
		for (String propertieName : allKeys) {
			Map<String, String> cleVal = prop.get(propertieName);
			// puis on créer l'objet propertie
			if (propertieName.equals(PersonalPropertie.class.getName())) properties.add(new PersonalPropertie(cleVal));
			else if (propertieName.equals(DeadLock.class.getName())) properties.add(new DeadLock(cleVal));
			// ici on peut ajouter les nouvelles propriétés
			else {
				log.warn("Le nom de la propriété n'existe pas : " + propertieName);
			}
		}
		return properties;
	}
}
