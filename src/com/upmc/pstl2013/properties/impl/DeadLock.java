package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.DeadLockTemplate;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.Behavior;

/**
 * Représente une propriété de vérification alloy.
 * 
 */
public class DeadLock extends AbstractProperties {
	
	public DeadLock() {
		// le DeadLock est un check avec la strategie d'exécution simple, et de parcours PathStrategy
		super(Boolean.TRUE, 
				Factory.getInstance().newSimpleExecutionStrategy(), 
				Factory.getInstance().newPathStrategy());
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new DeadLockTemplate().generate(this);
	}

	@Override
	public boolean continueExecution() {
		return super.getStrategyExecution().continueExecution();
	}

	@Override
	public Behavior getBehavior() {
		return Behavior.BUISINESS;
	}
}
