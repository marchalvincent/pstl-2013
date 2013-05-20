package com.upmc.pstl2013.strategyReduction.impl;

import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.Activity;
import org.eclipse.uml2.uml.ActivityEdge;
import org.eclipse.uml2.uml.ActivityNode;
import com.upmc.pstl2013.strategyReduction.IReduction;


public class ReductionExample implements IReduction {

	@Override
	public void reduce(Activity activity) {
		// ici faire l'algorithme permettant de réduire les diagrammes d'activités
		
		
		// pour cet exemple, nous enlèvons tous les edges allant vers "gen_A"
		EList<ActivityEdge> edges = activity.getEdges();
		for (ActivityEdge activityEdge : edges) {
			// pour chaque edge, on récupère la target
			ActivityNode target = activityEdge.getTarget();
			if (target.getName().equals("gen_A")) {
				// si la target est "gen_a", alors on change la target en la 1ère target de "gen_A"
				activityEdge.setTarget(target.getOutgoings().get(0).getTarget());
			}
		}
		
	}
}
