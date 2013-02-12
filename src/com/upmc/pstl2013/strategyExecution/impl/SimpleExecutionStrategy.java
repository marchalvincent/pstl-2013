package com.upmc.pstl2013.strategyExecution.impl;

import com.upmc.pstl2013.strategyExecution.IStrategyExecution;

/**
 * Cette exécution permet de ne faire la génération qu'une seule fois.
 *
 */
public class SimpleExecutionStrategy implements IStrategyExecution {

	private boolean isFirst;
	
	public SimpleExecutionStrategy() {
		super();
		isFirst = true;
	}
	
	@Override
	public boolean continueExecution() {
		if (isFirst) {
			isFirst = false;
			return true;
		}
		return false;
	}

	@Override
	public void setSatisfiable(boolean satisfiable) {}
	
	@Override
	public IStrategyExecution clone() throws CloneNotSupportedException {
		return (IStrategyExecution) super.clone();
	}
}
