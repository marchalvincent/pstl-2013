package com.upmc.pstl2013.alloyExecutor;

public interface IActivityResult {
	
	String getNom();
	String getLogResult();
	/**
	 * 
	 * @return retourne le chemin absolut ou null si aucun resultats n'a été trouvé par Alloy.
	 */
	String getPathXMLResult();

}
