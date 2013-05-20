package com.upmc.pstl2013.strategyReduction;

import org.eclipse.uml2.uml.Activity;


public interface IReduction {
	
	/**
	 * Permet de réduire une {@link Activity} correspondant au diagramme d'activité parsé.
	 * @param activity
	 */
	void reduce(Activity activity);
}
