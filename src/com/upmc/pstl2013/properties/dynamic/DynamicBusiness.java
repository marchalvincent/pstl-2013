package com.upmc.pstl2013.properties.dynamic;

import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.properties.Family;
import com.upmc.pstl2013.properties.impl.AbstractProperties;
import com.upmc.pstl2013.properties.impl.EnoughState;
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
		
		// les propriétés dynamiques dépendent d'EnoughState
		super.setDependance(EnoughState.class);
	}

	@Override
	public String getAlloyCode() throws JetException {
		// on se donne à la stratégie pour récupérer les informations telles que le nombre de nodes, edges, etc.
		return enumType.getStrategy().generate(this);
	}

	@Override
	public Family getBehavior() {
		return Family.BUISINESS;
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
		for (int i = 0; i < dataParams.length; i++) {
			if (dataParams[i] == null)
				dataParams[i] = "";
		}
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
	
	/**
	 * Getter sur le type de business.
	 * @return String.
	 */
	public String getEnumType() {
		return this.enumType.toString();
	}
}
