package com.upmc.pstl2013.alloyExecutor;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.JetTemplate;
import edu.mit.csail.sdg.alloy4.Err;

/**
 * Interface d'un exécuteur de fichier Alloy.
 * 
 */
public interface IAlloyExecutor {

	/**
	 * Méthode permettant d'exécuter un fichier UML.
	 * 
	 * @throws Err Dans le cas d'une exécution impossible.
	 * @throws JetTemplate dans le cas d'une erreur lors de la génération Jet.
	 * @return {@link IFileResult} le résultat de l'exécution pour un fichier.
	 */
	public IFileResult executeFiles() throws Err, JetException;
}
