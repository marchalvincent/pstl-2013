package com.upmc.pstl2013.AlloyExecutor;

import java.io.File;
import java.util.List;

import edu.mit.csail.sdg.alloy4.Err;
/***
 * Interface d'un exécuteur de fichier Alloy.
 *
 */
public interface IAlloyExecutor {

	/***
	 * Méthode permettant d'Exécuter la liste des fichiers als présent dans la liste.
	 * @throws Err Dans le cas d'une exécution impossible.
	 */
	public void executeFiles(List<File> listFiles) throws Err;
}
