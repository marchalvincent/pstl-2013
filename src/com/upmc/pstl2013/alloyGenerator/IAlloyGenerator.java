package com.upmc.pstl2013.alloyGenerator;

import java.io.FileNotFoundException;
import java.util.List;
import com.upmc.pstl2013.IProcess;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;

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
	 * Renvoie la liste des fichiers Alloy générés (à l'aide d'une classe conteneur
	 * pour passer des informations à l'éxecuteur).
	 * 
	 * @return {@link List} de {@link IAlloyGenerated}.
	 */
	List<IAlloyGenerated> getGeneratedFiles();

	/**
	 * Vérifie que les fichiers syntax et semantic sont présents dans le dossier de destination.
	 * 
	 * @throws FileNotFoundException
	 *             exception lancée s'il manque 1 ou 2 des fichiers.
	 */
	void fichiersPresents() throws FileNotFoundException;
}
