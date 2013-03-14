package com.upmc.pstl2013.properties.dynamic;

/**
 * L'énumération qui représente la totalité des propriétés dynamiques que peut créer un utilisateur.
 *
 */
public enum EDynamicBusiness {

	TEST_DYNAMIC1(DynamicFactory.getInstance().newTestDynamics1()),
	ABSENCE(DynamicFactory.getInstance().newAbsence());

	private final AbstractStrategyDynamicBusiness strategy;
 
	/**
	 * Constructeur privé.
	 * @param strategy la {@link AbstractStrategyDynamicBusiness} qui va permettre de générer le code Alloy.
	 */
	private EDynamicBusiness(AbstractStrategyDynamicBusiness strategy) {
		this.strategy = strategy;
	}
 
	/**
	 * Renvoie le nombre de {@link EParamType} associé à ce type dynamique.
	 * @return
	 */
	public Integer getNbParams() {
		return this.strategy.getParams().size();
	}
	
	/**
	 * Renvoie la stratégie associée à ce type dynamique.
	 * @return
	 */
	public AbstractStrategyDynamicBusiness getStrategy() {
		return this.strategy;
	}
}
