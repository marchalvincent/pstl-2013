package com.upmc.pstl2013.strategyExecution;

/**
 * Représente la façon dont on exécute un fichier Alloy.
 *
 */
public interface IStrategyExecution {
	
	/**
	 * Renvoie un booléen pour dire s'il faut continuer l'exécution ou non.
	 * @return boolean
	 */
	boolean continueExecution();
	
	/**
	 * Spécifie à la stratégie que la précédente vérification est satisfaisable ou non.
	 */
	void setSatisfiable(boolean satisfiable);
}
