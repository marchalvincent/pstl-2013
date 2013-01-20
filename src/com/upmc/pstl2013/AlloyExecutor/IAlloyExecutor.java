package com.upmc.pstl2013.AlloyExecutor;

import com.upmc.pstl2013.IProcess;

import edu.mit.csail.sdg.alloy4.Err;
/***
 * Interface d'un exécuteur de fichier Alloy.
 *
 */
public interface IAlloyExecutor extends IProcess {

	/***
	 * Méthode permettant d'Exécuter la liste des fichiers "als" présents dans la liste.
	 * @throws Err Dans le cas d'une exécution impossible.
	 */
	public void executeFiles() throws Err;
}
