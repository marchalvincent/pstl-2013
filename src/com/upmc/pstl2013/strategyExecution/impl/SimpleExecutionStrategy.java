package com.upmc.pstl2013.strategyExecution.impl;

import com.upmc.pstl2013.strategyExecution.IStrategyExecution;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;

/**
 * Cette exécution permet de ne faire la génération qu'une seule fois.
 *
 */
public class SimpleExecutionStrategy implements IStrategyExecution {
	
	public SimpleExecutionStrategy() {
		super();
	}
	
	@Override
	public boolean continueExecution() {
		return false;
	}

	@Override
	public void setSolution(A4Solution solution) {}
	
	@Override
	public IStrategyExecution clone() throws CloneNotSupportedException {
		return (IStrategyExecution) super.clone();
	}


}
