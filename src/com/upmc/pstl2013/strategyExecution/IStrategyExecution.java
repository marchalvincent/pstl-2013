package com.upmc.pstl2013.strategyExecution;

import com.upmc.pstl2013.properties.IProperties;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;

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
	 * Spécifie à la stratégie la solution générée par Alloy.
	 */
	void setSolution(A4Solution solution);
	
	/**
	 * Méthode clone de {@link Cloneable}.
	 */
	public IStrategyExecution clone() throws CloneNotSupportedException;
}
