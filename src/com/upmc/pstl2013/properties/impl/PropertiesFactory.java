package com.upmc.pstl2013.properties.impl;

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
	 * @param name le nom de la propriété.
	 * @return une {@link IProperties}.
	 * @throws PropertiesException si le nom de la propriété est introuvable.
	 */
	public static IProperties createPropertie(String name) throws PropertiesException {

		// puis on créer l'objet propertie
		if (name.equals(PersonalPropertie.class.getSimpleName())) return new PersonalPropertie();
		else if (name.equals(DeadLock.class.getSimpleName())) return new DeadLock();
		// ici on peut ajouter les nouvelles propriétés
		else {
			final String error = "Le nom de la propriété n'existe pas dans la factory : " + name;
			log.warn(error);
			throw new PropertiesException(error);
		}
	}
}
