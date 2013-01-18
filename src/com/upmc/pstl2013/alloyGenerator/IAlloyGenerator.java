package com.upmc.pstl2013.alloyGenerator;

import java.io.File;
import java.util.List;

/**
 * L'interface d'un générateur de fichier Alloy.
 *
 */
public interface IAlloyGenerator {

	/**
	 * Génère le(s) fichier(s) Alloy.
	 */
	void generateFile();
	
	/**
	 * Renvoie la liste des fichiers Alloy générés.
	 * @return {@link List} de {@link File}.
	 */
	List<File> getGeneratedFiles();
}
