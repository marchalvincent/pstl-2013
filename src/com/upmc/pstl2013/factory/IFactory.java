package com.upmc.pstl2013.factory;

import com.upmc.pstl2013.alloyGenerator.IUMLParser;
import com.upmc.pstl2013.alloyGenerator.interfaces.IAlloyGenerator;
import com.upmc.pstl2013.interfaces.IUMLFileChooser;

/**
 * Représente les objets que la factory doit savoir créer.
 */
public interface IFactory {

	/**
	 * Créé un sélecteur de fichier UML.
	 * @return
	 */
	IUMLFileChooser newFileChooser();
	
	/**
	 * Créé un parser de fichier UML.
	 * @return
	 */
	IUMLParser newParser();
	
	/**
	 * Créé un générateur de fichier Alloy.
	 * @return
	 */
	IAlloyGenerator newAlloyGenerator();
}
