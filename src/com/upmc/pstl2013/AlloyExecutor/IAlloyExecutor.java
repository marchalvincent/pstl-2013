package com.upmc.pstl2013.alloyExecutor;

import java.util.List;

import com.upmc.pstl2013.IProcess;
import com.upmc.pstl2013.alloyGenerator.impl.JetException;
import com.upmc.pstl2013.alloyGenerator.impl.JetTemplate;
import com.upmc.pstl2013.strategy.IStrategy;

import edu.mit.csail.sdg.alloy4.Err;

/**
 * Interface d'un exécuteur de fichier Alloy.
 *
 */
public interface IAlloyExecutor extends IProcess {

	/**
	 * Méthode permettant d'Exécuter la liste des fichiers "als" présents dans la liste.
	 * @param strategies la liste des strategies de parcours du résultat.
	 * @throws Err Dans le cas d'une exécution impossible.
	 * @throws JetTemplate dans le cas d'une erreur lors de la génération Jet.
	 */
	public String executeFiles(List<IStrategy> strategies) throws Err, JetException;
}
