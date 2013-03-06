package com.upmc.pstl2013.strategyExecution;

import com.upmc.pstl2013.strategyExecution.impl.IncrementalExecutionStrategy;
import com.upmc.pstl2013.strategyExecution.impl.SimpleExecutionStrategy;


/**
 * La factory des stratégies d'exécutions.
 *
 */
public class ExecutionFactory {
	
	private static ExecutionFactory instance = new ExecutionFactory();

	public static ExecutionFactory getInstance() {
		return instance;
	}

	private ExecutionFactory() {}
	
	/**
	 * Créé un {@link SimpleExecutionStrategy}.
	 */
	public IStrategyExecution newSimpleExecutionStrategy() {
		return new SimpleExecutionStrategy();
	}
	
	/**
	 * Créé un {@link IncrementalExecutionStrategy}.
	 */
	public IStrategyExecution newIncrementalExecutionStrategy() {
		return new IncrementalExecutionStrategy();
	}
}
