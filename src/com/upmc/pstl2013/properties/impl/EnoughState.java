package com.upmc.pstl2013.properties.impl;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.EnoughStateTemplate;
import com.upmc.pstl2013.factory.Factory;


public class EnoughState extends AbstractProperties {

	private boolean firstIncrement;
	public static int MAX_STATES = 100;
	
	public EnoughState() {
		super(Boolean.TRUE, Factory.getInstance().newIncrementalExecutionStrategy(), Factory.getInstance().newVoidStrategy());
		super.put("nbState", "1");
		super.put("incrementation", "10");
		firstIncrement = true;
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new EnoughStateTemplate().generate(this);
	}


	@Override
	public boolean continueExecution() {
		// si notre stratégie nous demande de continuer, on incrémente notre nbState au passage.
		if (super.getStrategyExecution().continueExecution() && Integer.parseInt(super.getString("nbState")) <= MAX_STATES) {
			this.incrementation();
			return true;
		}
		return false;
	}

	/**
	 * Incrémente le nombre de State de la propriété.
	 */
	private void incrementation() {
		// pour la première exécution, on n'incrémente pas
		if (firstIncrement) {
			firstIncrement = false;
			return;
		}
		int nbState = Integer.parseInt(super.getString("nbState"));
		String sIncr = super.getString("incrementation");
		if (sIncr != null) {
			nbState = nbState + Integer.parseInt(sIncr);
		} else {
			nbState += 10;
		}
		super.put("nbState", String.valueOf(nbState));
	}
}
