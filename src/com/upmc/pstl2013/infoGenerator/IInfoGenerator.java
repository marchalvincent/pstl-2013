package com.upmc.pstl2013.infoGenerator;

import java.util.Map;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;

/**
 * Un {@code IInfoGenerator} permet à l'IU de passer des paramètres au {@link IAlloyGenerator}.
 * 
 */
public interface IInfoGenerator {

	/**
	 * Setter pour le chemin du dossier de destination.
	 * 
	 * @param path le chemin de destination.
	 */
	void setDestinationDirectory(String path);

	/**
	 * Renvoie le chemin du dossier de destination où seront générés les fichiers alloy.
	 * 
	 * @return String le chemin de destination.
	 */
	String getDestinationDirectory();

	/**
	 * Spécifie les propriétés de génération.
	 * 
	 * @param properties
	 *            . Contient une association String-Map. Le string correspond au nom de la
	 *            propriété. La sous-map représente l'association clé-valeur de propriété
	 *            personnalisée par l'utilisateur.
	 */
	void setProperties(Map<String, Map<String, String>> properties);

	/**
	 * Renvoie les propriétés de génération alloy.
	 */
	Map<String, Map<String, String>> getProperties();
}
