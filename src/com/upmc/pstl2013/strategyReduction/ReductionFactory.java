package com.upmc.pstl2013.strategyReduction;

import com.upmc.pstl2013.strategyReduction.impl.ReductionExample;


/**
 * La factory des classes de réduction de diagrammes d'activités.
 * @author Vincent
 *
 */
public class ReductionFactory {

	private static ReductionFactory instance = new ReductionFactory();

	public static ReductionFactory getInstance() {
		return instance;
	}

	private ReductionFactory() {}
	
	public IReduction newReductionExample() {
		return new ReductionExample();
	}
}
