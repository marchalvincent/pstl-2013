package com.upmc.pstl2013.alloyGenerator.interfaces;

import com.upmc.pstl2013.fileContainer.interfaces.IUMLFileContainer;

/**
 * L'interface d'un générateur de fichier Alloy.
 *
 */
public interface IAlloyGenerator {

	/**
	 * Génère le fichier Alloy.
	 * @param fileChooser le {@link IUMLFileContainer} qui a servit à sélectionner les fichiers UML.
	 */
	void generateFile(IUMLFileContainer fileChooser);
}
