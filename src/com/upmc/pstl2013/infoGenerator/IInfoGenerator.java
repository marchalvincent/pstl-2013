package com.upmc.pstl2013.infoGenerator;

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
	
	// TODO ajouter les properties...
}
