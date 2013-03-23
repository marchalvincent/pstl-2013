package com.upmc.pstl2013.alloyExecutor.impl;

import edu.mit.csail.sdg.alloy4.A4Reporter;

/**
 * Cette classe permet de récupérer des informations sur la génération de la solution Alloy.
 *
 */
public class MyA4Reporter extends A4Reporter {

	private String solver;
	private int bitwidth;
	private int maxseq;
	private int skolemDepth;
	private int symmetry;
	
	private int primaryVars;
	private int totalVars;
	private int clauses;
	private long solvingTime;
	
	private String resultCNF;

	public MyA4Reporter() {
		super();
		solver = "";
		bitwidth = 0;
		maxseq = 0;
		skolemDepth = 0;
		symmetry = 0;
		
		primaryVars = 0;
		totalVars = 0;
		clauses = 0;
		solvingTime = 0;
		
		resultCNF = "";
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
	public void translate (String solver, int bitwidth, int maxseq, int skolemDepth, int symmetry) {
		this.solver = solver;
		this.bitwidth = bitwidth;
		this.maxseq = maxseq;
		this.skolemDepth = skolemDepth;
		this.symmetry = symmetry;
		super.translate(solver, bitwidth, maxseq, skolemDepth, symmetry);
	}
	
	@Override
	public void resultCNF(String filename) {
		resultCNF = filename;
		super.resultCNF(filename);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  Solver=");
		sb.append(solver);
		sb.append(" Bitwidth=");
		sb.append(bitwidth);
		sb.append(" MaxSeq=");
		sb.append(maxseq);
		sb.append(" SkolemDepth=");
		sb.append(skolemDepth);
		sb.append(" Symmetry=");
		sb.append(symmetry);
		sb.append("\n  ");
		
		sb.append(totalVars);
		sb.append(" vars. ");
		sb.append(primaryVars);
		sb.append(" primary vars. ");
		sb.append(clauses);
		sb.append(" clauses. ");
		sb.append(solvingTime);
		sb.append(" ms.");

		if (!resultCNF.isEmpty()) {
			sb.append("\n  Result CNF : ");
			sb.append(resultCNF);
		}
		
		return sb.toString();
	}
}
