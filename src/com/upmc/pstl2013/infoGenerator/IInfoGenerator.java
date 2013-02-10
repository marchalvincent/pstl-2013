package com.upmc.pstl2013.infoGenerator;

import java.util.List;
import com.upmc.pstl2013.alloyGenerator.IAlloyGenerator;
import com.upmc.pstl2013.properties.IProperties;

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
	 * @param properties une liste de {@link IProperties}.
	 */
	void setProperties(List<IProperties> properties);

	/**
	 * Renvoie les propriétés de génération alloy.
	 */
	List<IProperties> getProperties();
}
