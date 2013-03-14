package com.upmc.pstl2013.properties.dynamic;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.properties.Behavior;
import com.upmc.pstl2013.properties.impl.AbstractProperties;
import com.upmc.pstl2013.strategyExecution.ExecutionFactory;
import com.upmc.pstl2013.strategyParcours.ParcoursFactory;

/**
 * Représente une propriété "Business" créée dynamiquement par l'utilisateur.
 *
 */
public class DynamicBusiness extends AbstractProperties {

	private String nom;
	private EDynamicBusiness enumType;
	private String[] dataParams;
	
	/**
	 * Constructor.
	 * @param nom le nom de la propriété.
	 * @param enumType l'{@link EDynamicBusiness} correspondant au type de la propriété dynamique.
	 */
	public DynamicBusiness(String nom, EDynamicBusiness enumType) {
		// par défaut une dynamique business est une simple exécution avec aucun parcours de solution.
		super(Boolean.TRUE, 
				ExecutionFactory.getInstance().newSimpleExecutionStrategy(), 
				ParcoursFactory.getInstance().newVoidStrategy());
		this.nom = nom;
		this.enumType = enumType;
		dataParams = new String[enumType.getNbParams()];
	}

	@Override
	public String getAlloyCode() throws JetException {
		// on se donne à la stratégie pour récupérer les informations telles que le nombre de nodes, edges, etc.
		return enumType.getStrategy().generate(this);
	}

	@Override
	public Behavior getBehavior() {
		return Behavior.BUISINESS;
	}
	
	/**
	 * Spécifie le noeud qui est à l'emplacement {@code index}.
	 * @param index l'indice du noeud.
	 * @param dataParam le nom du noeud.
	 */
	public void addDataParam(int index, String dataParam) {
		if (0 <= index && index < enumType.getNbParams()) {
			dataParams[index] = dataParam;
		}
	}
	
	public String[] getDataParams() {
		return dataParams;
	}
	
	/**
	 * Getter sur le nom de la propriété.
	 * @return String.
	 */
	@Override
	public String getName() {
		return this.nom;
	}
}
