package com.upmc.pstl2013.properties.impl;

import org.apache.log4j.Logger;
import com.upmc.pstl2013.alloyGenerator.impl.AlloyGenerator;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.alloyGenerator.jet.impl.EnoughStateTemplate;
import com.upmc.pstl2013.properties.Family;
import com.upmc.pstl2013.strategyExecution.ExecutionFactory;
import com.upmc.pstl2013.strategyParcours.ParcoursFactory;


public class EnoughState extends AbstractProperties {

	private Logger log = Logger.getLogger(AlloyGenerator.class);
	private int maxStep = -1;
	private int incrementation = -1;

	public EnoughState() {
		super(Boolean.TRUE, 
				ExecutionFactory.getInstance().newIncrementalExecutionStrategy(), 
				ParcoursFactory.getInstance().newVoidStrategy());
		super.setModifiable(false);
		super.putPrivate("nbState", "1");
		super.put("incrementation", "10");
		super.put("maxStep", "100");
		super.setFairness(true);
		
		super.setDependance(Wf.class);
		
		// vous pouvez ajouter des réductions de diagramme d'activités ainsi...
//		super.addReduction(ReductionFactory.getInstance().newReductionExample());
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
			log.error("Impossible to parse the attribute 'nbState'.");
			nbState = 1;
		}
		
		if (maxStep == -1) {
			try {
				maxStep = Integer.parseInt(super.getString("maxStep"));
			} catch(Exception e) {
				log.error("Impossible to parse the attribute 'maxStep'.");
				maxStep = 100;
			}
		}
		if (super.getStrategyExecution().continueExecution() && nbState <= maxStep) {
			this.incrementation(nbState);
			return true;
		}
		return false;
	}

	/**
	 * Incrémente le nombre de State de la propriété.
	 */
	private void incrementation(int nbState) {
		
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
	public Family getBehavior() {
		return Family.SOUDNESS;
	}
}
