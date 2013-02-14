package com.upmc.pstl2013.strategyExecution;

import com.upmc.pstl2013.properties.IProperties;

/**
 * Représente la façon dont on exécute un fichier Alloy.
 *
 * @extends Cloneable, un {@code IStrategyExecution} doit pouvoir être cloné. Cf. {@link IProperties}.
 * 
 */
public interface IStrategyExecution extends Cloneable {
	
	/**
	 * Renvoie un booléen pour dire s'il faut continuer l'exécution ou non.
	 * @return boolean
	 */
	boolean continueExecution();
	
	/**
	 * Spécifie à la stratégie que la précédente vérification est satisfaisable ou non.
	 */
	void setSatisfiable(boolean satisfiable);
	
	/**
	 * Méthode clone de {@link Cloneable}.
	 */
	public IStrategyExecution clone() throws CloneNotSupportedException;
}
