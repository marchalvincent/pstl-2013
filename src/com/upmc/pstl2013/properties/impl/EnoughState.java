package com.upmc.pstl2013.properties.impl;

import org.apache.log4j.Logger;
import com.upmc.pstl2013.alloyGenerator.impl.AlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.EnoughStateTemplate;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.Behavior;


public class EnoughState extends AbstractProperties {

	private Logger log = Logger.getLogger(AlloyGenerator.class);
	private boolean firstIncrement;
	private int maxStep = -1;
	private int incrementation = -1;
	public static Behavior family = Behavior.ORGANIZATIONAL;

	public EnoughState() {
		super(Boolean.TRUE, Factory.getInstance().newIncrementalExecutionStrategy(), Factory.getInstance().newVoidStrategy());
		super.setModifiable(false);
		super.putPrivate("nbState", "1");
		super.put("incrementation", "10");
		super.put("maxStep", "100");
		firstIncrement = true;
	}

	@Override
	public String getAlloyCode() throws JetException {
		return new EnoughStateTemplate().generate(this);
	}


	@Override
	public boolean continueExecution() {
		// si notre stratégie nous demande de continuer, on incrémente notre nbState au passage.
		int nbState;
		try {
			nbState = Integer.parseInt(super.getString("nbState"));
		} catch(Exception e) {
			log.error("Impossible de parser l'attribut 'maxStep'.");
			nbState = 1;
		}
		
		if (maxStep == -1) {
			try {
				maxStep = Integer.parseInt(super.getString("maxStep"));
			} catch(Exception e) {
				log.error("Impossible de parser l'attribut 'maxStep'.");
				maxStep = 100;
			}
		}
		if (super.getStrategyExecution().continueExecution() && nbState <= maxStep) {
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
		
		if (incrementation == -1) {
			try {
				incrementation = Integer.parseInt(super.getString("incrementation"));
			} catch (Exception e) {
				log.error("Impossible de parser l'attribut 'incrementation'.");
				incrementation = 10;
			}
		}
		nbState += incrementation;
		super.put("nbState", String.valueOf(nbState));
	}

	@Override
	public Behavior getBehavior() {
		return Behavior.SOUDNESS;
	}
}
