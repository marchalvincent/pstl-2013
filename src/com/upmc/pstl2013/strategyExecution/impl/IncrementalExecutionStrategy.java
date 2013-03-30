package com.upmc.pstl2013.strategyExecution.impl;

import com.upmc.pstl2013.strategyExecution.IStrategyExecution;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;

/**
 * Représente la stratégie d'exécution qui permet d'augmenter le nombre de State tant
 * qu'on trouve un contre exemple à l'exécution Alloy.
 *
 */
public class IncrementalExecutionStrategy implements IStrategyExecution {

	private A4Solution solution;

	public IncrementalExecutionStrategy() {
		super();
	}
	
	@Override
	public boolean continueExecution() {
		// tant qu'on a un contre exemple, on continue la génération.
		if (solution.satisfiable()) {
			return true;
		}
		return false;
	}

	@Override
	public void setSolution(A4Solution solution) {
		this.solution = solution;
	}
	
	@Override
	public IStrategyExecution clone() throws CloneNotSupportedException {
		return (IStrategyExecution) super.clone();
	}
}
