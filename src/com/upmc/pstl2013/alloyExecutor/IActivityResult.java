package com.upmc.pstl2013.alloyExecutor;

/**
 * Une {@code IActivityResult} représente le résultat d'une exécution pour une activité.
 *
 */
public interface IActivityResult {
	
	/**
	 * Renvoie le nom de l'activité.
	 */
	String getNom();
	
	/**
	 * Renvoie les logs générés lors de l'exécution.
	 */
	String getLogResult();
	
	/**
	 * Ajoute un string aux logs.
	 * @param s le String
	 */
	void appendLog(String s);
	
	/**
	 * Remet a zéro les logs.
	 */
	void resetLog();
	
	/**
	 * Renvoie le chemin absolut (ou null) vers le fichier XML de la solution Alloy.
	 * @return String le chemin ou null si aucun résultat n'a été trouvé.
	 */
	String getPathXMLResult();
	
	/**
	 * Spécifie le chemin du fichier XML solution de l'activité.
	 * @param path le chemin vers le fichier XML.
	 */
	void setPathXMLResult(String path);
	
	/**
	 * Renvoie un booléen qui dit si le résultat est satisfiable.
	 */
	boolean isSatisfiable();
	
	/**
	 * Met le flag satifiable à {@code bool}.
	 */
	void setSatisfiable(boolean bool);
	
	/**
	 * Setter permettant de spécifier le nombre de state qui a été utilisé pour générer 
	 * cette solution.
	 * @param nbState String, le nombre de state.
	 */
	void setNbState(String nbState);
	
	/**
	 * Renvoie le nombre de state qui a été utilisé pour générer cette solution Alloy.
	 * Cette méthode est surtout utile lorsqu'on exécute la propriété EnoughState.
	 */
	String getNbState();
	
}
