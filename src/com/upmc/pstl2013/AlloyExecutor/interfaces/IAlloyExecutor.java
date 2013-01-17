package com.upmc.pstl2013.AlloyExecutor.interfaces;

import edu.mit.csail.sdg.alloy4.Err;
/***
 * Interface d'un executeur de fichier Alloy.
 *
 */
public interface IAlloyExecutor {

	/***
	 * 
	 * @throws Err
	 */
	public void executeFiles() throws Err;
}
