package com.upmc.pstl2013.strategyExecution.impl;

import com.upmc.pstl2013.strategyExecution.IStrategyExecution;

/**
 * Représente la stratégie d'exécution qui permet d'augmenter le nombre de State tant
 * qu'on trouve un contre exemple à l'exécution Alloy.
 *
 */
public class IncrementalExecutionStrategy implements IStrategyExecution {

	private boolean isFirst;
	private boolean satisfiable;

	public IncrementalExecutionStrategy() {
		super();
		this.isFirst = true;
		this.satisfiable = false;
	}

	@Override
	public boolean continueExecution() {
		// pour la première exécution on dit qu'on peut générer le fichier.
		if (isFirst) {
			isFirst = false;
			return true;
		}
		// tant qu'on a un contre exemple, on continue la génération.
		if (satisfiable) {
			return true;
		}

		return false;
	}

	@Override
	public void setSatisfiable(boolean satisfiable) {
		this.satisfiable = satisfiable;
	}
	
	@Override
	public IStrategyExecution clone() throws CloneNotSupportedException {
		return (IStrategyExecution) super.clone();
	}
}
