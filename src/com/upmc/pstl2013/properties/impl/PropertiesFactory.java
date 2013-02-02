package com.upmc.pstl2013.properties.impl;

import java.util.Map;
import org.apache.log4j.Logger;
import com.upmc.pstl2013.properties.IProperties;

/**
 * Se charge de créer les propriétés.
 * 
 */
public class PropertiesFactory {

	private static Logger log = Logger.getLogger(PropertiesFactory.class);
	private static Map<String, IProperties> properties;

	/**
	 * Méthode static pour la création des propriétés.
	 * 
	 * @param name le nom de la propriété.
	 * @return une {@link IProperties}.
	 * @throws PropertiesException si le nom de la propriété est introuvable.
	 */
	public static IProperties createPropertie(String name) throws PropertiesException {

		// si l'objet est déjà créé, on le renvoie
		IProperties prop = properties.get(name);
		if (prop != null) {
			return prop;
		}
		
		// sinon, on créer l'objet, on l'ajoute dans la map et on le renvoie
		if (name.equals(PersonalPropertie.class.getSimpleName())) 	properties.put(name, new PersonalPropertie());
		else if (name.equals(DeadLock.class.getSimpleName())) 		properties.put(name, new DeadLock());
		else if (name.equals(EnoughState.class.getSimpleName())) 	properties.put(name, new EnoughState());
		else if (name.equals(Orga.class.getSimpleName())) 			properties.put(name, new Orga());
		else if (name.equals(Wf.class.getSimpleName())) 			properties.put(name, new Wf());
		// c'est ici qu'on ajoute les nouvelles propriétés créées
		else {
			final String error = "Le nom de la propriété n'existe pas dans la factory : " + name;
			log.warn(error);
			throw new PropertiesException(error);
		}
		return properties.get(name);
	}
}
