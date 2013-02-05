package com.upmc.pstl2013.alloyExecutor;

import com.upmc.pstl2013.IProcess;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.JetTemplate;
import edu.mit.csail.sdg.alloy4.Err;

/**
 * Interface d'un exécuteur de fichier Alloy.
 * 
 */
public interface IAlloyExecutor extends IProcess {

	/**
	 * Méthode permettant d'Exécuter la liste des fichiers "als" présents dans la liste.
	 * 
	 * @throws Err
	 *             Dans le cas d'une exécution impossible.
	 * @throws JetTemplate
	 *             dans le cas d'une erreur lors de la génération Jet.
	 */
	public String executeFiles() throws Err, JetException;
}
