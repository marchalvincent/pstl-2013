package com.upmc.pstl2013.alloyGenerator.interfaces;

import com.upmc.pstl2013.interfaces.IUMLFileChooser;

/**
 * L'interface d'un générateur de fichier Alloy.
 *
 */
public interface IAlloyGenerator {

	/**
	 * Génère le fichier Alloy.
	 * @param fileChooser le {@link IUMLFileChooser} qui a servit à sélectionner les fichiers UML.
	 */
	void generateFile(IUMLFileChooser fileChooser);
}
