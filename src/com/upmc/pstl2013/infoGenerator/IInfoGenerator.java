package com.upmc.pstl2013.infoGenerator;

import java.util.Map;

/**
 * Un {@code IInfoGenerator} permet de passer des paramètres
 * au générateur de fichiers alloy.
 * 
 */
public interface IInfoGenerator {
	
	/**
	 * Setter pour le chemin du dossier de destination.
	 * @param path
	 */
	void setDestinationDirectory(String path);

	/**
	 * Renvoie le chemin du dossier de destination où
	 * seront générés les fichiers alloy.
	 * @return
	 */
	String getDestinationDirectory();
	
	/**
	 * Spécifie les propriétés de génération.
	 * @param properties. Contient une association String-Map. Le string 
	 * correspond au nom de la propriété. La sous-map représente l'association
	 * clé-valeur de propriété personnalisée par l'utilisateur.
	 */
	void setProperties(Map<String, Map<String, String>> properties);
	
	/**
	 * Renvoie les propriétés de génération alloy.
	 */
	Map<String, Map<String, String>> getProperties();
}
