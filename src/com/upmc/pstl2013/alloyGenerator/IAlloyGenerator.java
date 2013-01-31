package com.upmc.pstl2013.alloyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import com.upmc.pstl2013.IProcess;
import com.upmc.pstl2013.alloyGenerator.impl.JetException;

/**
 * L'interface d'un générateur de fichier Alloy.
 * 
 */
public interface IAlloyGenerator extends IProcess {

	/**
	 * Génère le(s) fichier(s) Alloy.
	 */
	void generateFile() throws JetException;

	/**
	 * Renvoie la liste des fichiers Alloy générés.
	 * 
	 * @return {@link List} de {@link File}.
	 */
	List<File> getGeneratedFiles();

	/**
	 * Vérifie que les fichiers syntax et semantic sont présents dans le dossier de destination.
	 * 
	 * @throws FileNotFoundException
	 *             exception lancée s'il manque 1 ou 2 des fichiers.
	 */
	void fichiersPresents() throws FileNotFoundException;
}
