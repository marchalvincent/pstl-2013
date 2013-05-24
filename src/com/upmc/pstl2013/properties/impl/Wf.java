package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.WfTemplate;
import com.upmc.pstl2013.properties.Family;
import com.upmc.pstl2013.strategyExecution.ExecutionFactory;
import com.upmc.pstl2013.strategyParcours.ParcoursFactory;


public class Wf extends AbstractProperties {

	public Wf() {
		super(Boolean.FALSE, 
				ExecutionFactory.getInstance().newSimpleExecutionStrategy(), 
				ParcoursFactory.getInstance().newVoidStrategy());
		super.put("initial", Boolean.TRUE);
		super.put("final", Boolean.TRUE);
		super.put("merge", Boolean.TRUE);
		
		// cette propriété n'a aucune dépendance, c'est la première à etre exécutée.
//		super.setDependance(null);
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new WfTemplate().generate(this);
	}

	@Override
	public Family getBehavior() {
		return Family.SYNTACTICAL;
	}
}
