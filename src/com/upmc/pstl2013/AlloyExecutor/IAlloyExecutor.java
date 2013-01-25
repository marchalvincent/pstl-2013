package com.upmc.pstl2013.alloyExecutor;

import java.util.List;

import kodkod.instance.Instance;
import kodkod.instance.TupleSet;
import kodkod.util.ints.IndexedEntry;

import com.upmc.pstl2013.IProcess;

import edu.mit.csail.sdg.alloy4.Err;
import edu.mit.csail.sdg.alloy4compiler.ast.ExprVar;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig.Field;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;
/***
 * Interface d'un exécuteur de fichier Alloy.
 *
 */
public interface IAlloyExecutor extends IProcess {

	/***
	 * Méthode permettant d'Exécuter la liste des fichiers "als" présents dans la liste.
	 * @throws Err Dans le cas d'une exécution impossible.
	 */
	public String executeFiles() throws Err;
	
	public void getResults(A4Solution ans);
}
