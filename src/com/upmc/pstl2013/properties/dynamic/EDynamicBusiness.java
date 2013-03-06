package com.upmc.pstl2013.properties.dynamic;

/**
 * L'énumération qui représente la totalité des propriétés dynamiques que peut créer un utilisateur.
 *
 */
public enum EDynamicBusiness {

	TEST_DYNAMIC1(1, DynamicFactory.getInstance().newTestDynamics()),
	TEST_DYNAMIC2(2, DynamicFactory.getInstance().newTestDynamics()),
	TEST_DYNAMIC3(3, DynamicFactory.getInstance().newTestDynamics());

	private final Integer nbNodes;
	private final AbstractStrategyDynamicBusiness strategy;
 
	/**
	 * Constructeur privé.
	 * @param nbNodes le nombre de noeuds nécessaire pour la génération Alloy.
	 * @param strategy la {@link AbstractStrategyDynamicBusiness} qui va permettre de générer le code Alloy.
	 */
	private EDynamicBusiness(Integer nbNodes, AbstractStrategyDynamicBusiness strategy) {
		this.nbNodes = nbNodes;
		this.strategy = strategy;
	}
 
	/**
	 * Renvoie le nombre de noeuds associé à ce type dynamique.
	 * @return
	 */
	public Integer getNbNodes() {
		return this.nbNodes;
	}
	
	/**
	 * Renvoie la stratégie associée à ce type dynamique.
	 * @return
	 */
	public AbstractStrategyDynamicBusiness getStrategy() {
		return this.strategy;
	}
}
