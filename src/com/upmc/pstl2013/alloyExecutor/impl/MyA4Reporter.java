package com.upmc.pstl2013.alloyExecutor.impl;

import edu.mit.csail.sdg.alloy4.A4Reporter;

/**
 * Cette classe permet de récupérer des informations sur la génération de la solution Alloy.
 *
 */
public class MyA4Reporter extends A4Reporter {

	private int primaryVars;
	private int totalVars;
	private int clauses;
	private long solvingTime;

	public MyA4Reporter() {
		super();
		primaryVars = 0;
		totalVars = 0;
		clauses = 0;
		solvingTime = 0;
	}

	@Override
	public void solve(int primaryVars, int totalVars, int clauses) {
		this.primaryVars = primaryVars;
		this.totalVars = totalVars;
		this.clauses = clauses;
		super.solve(primaryVars, totalVars, clauses);
	}

	@Override
	public void resultSAT (Object command, long solvingTime, Object solution) {
		if (solvingTime != 0)
			this.solvingTime = solvingTime;
		super.resultSAT(command, solvingTime, solution);
	}

	@Override
	public void resultUNSAT (Object command, long solvingTime, Object solution) {
		if (solvingTime != 0)
			this.solvingTime = solvingTime;
		super.resultUNSAT(command, solvingTime, solution);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(totalVars);
		sb.append(" vars. ");
		sb.append(primaryVars);
		sb.append(" primary vars. ");
		sb.append(clauses);
		sb.append(" clauses. ");
		sb.append(solvingTime);
		sb.append(" ms.");
		return sb.toString();
	}
}
