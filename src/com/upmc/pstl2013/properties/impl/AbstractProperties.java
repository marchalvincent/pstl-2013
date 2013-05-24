package com.upmc.pstl2013.properties.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.eclipse.uml2.uml.Activity;
import com.upmc.pstl2013.properties.IAttribute;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.PropertiesFactory;
import com.upmc.pstl2013.strategyExecution.IStrategyExecution;
import com.upmc.pstl2013.strategyParcours.IStrategyParcours;
import com.upmc.pstl2013.strategyReduction.IReduction;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;

/**
 * Représente la classe mère de toutes les propriétés de vérification Alloy.
 * 
 * Cette classe implémente {@link IStrategyExecution} mais ne fait que déléguer le travail
 * à sa stratégie d'exécution en fait.
 */
public abstract class AbstractProperties implements IProperties {

	private List<IAttribute> attributes;
	private Boolean isCheck;
	private IStrategyExecution strategyExecution;
	private IStrategyParcours strategyParcours;
	private boolean isModifiable;
	private InitialState etatInitial;
	private List<IReduction> reductions;
	private String dependance;
	private static Logger log = Logger.getLogger(AbstractProperties.class);

	/**
	 * Renvoie la liste des propriétés de vérification alloy possible.
	 * @return une liste de {@link IProperties}.
	 */
	public static List<IProperties> getProperties() {
		return PropertiesFactory.getInstance().getProperties();
	}

	public AbstractProperties(Boolean isCheck, IStrategyExecution strategyExecution, IStrategyParcours strategy) {
		super();
		this.attributes = new ArrayList<IAttribute>();
		this.isCheck = isCheck;
		this.strategyExecution = strategyExecution;
		this.strategyParcours = strategy;
		this.setModifiable(true);
		this.etatInitial = null;
		this.reductions = new ArrayList<IReduction>();
		// certains attributs par défaut
		this.putPrivate("fairness", false);
		this.putPrivate("bitwidth", "3");
		this.dependance = null;
	}
	
	public void setFairness(boolean bool) {
		this.putPrivate("fairness", bool);
	}

	@Override
	public void putPrivate(String key, String value) {
		this.remove(key);
		attributes.add(PropertiesFactory.getInstance().newAttribute(key, value, Boolean.TRUE));
	}

	@Override
	public void put(String key, String value) {
		this.remove(key);
		attributes.add(PropertiesFactory.getInstance().newAttribute(key, value, Boolean.FALSE));
	}

	@Override
	public void putPrivate(String key, Boolean value) {
		this.remove(key);
		attributes.add(PropertiesFactory.getInstance().newAttribute(key, value, Boolean.TRUE));
	}

	@Override
	public void put(String key, Boolean value) {
		this.remove(key);
		attributes.add(PropertiesFactory.getInstance().newAttribute(key, value, Boolean.FALSE));
	}

	@Override
	public String getString(String key) {
		for (IAttribute iAttribute : attributes) {
			if (iAttribute.getKey().equals(key) && iAttribute.getValue() instanceof String) {
				return (String) iAttribute.getValue();
			}
		}
		return null;
	}

	@Override
	public Boolean getBoolean(String key) {
		for (IAttribute iAttribute : attributes) {
			if (iAttribute.getKey().equals(key) && iAttribute.getValue() instanceof Boolean) {
				return (Boolean) iAttribute.getValue();
			}
		}
		return null;
	}

	@Override
	public Map<String, String> getStringAttributes() {
		Map<String, String> retour = new HashMap<String, String>();
		for (IAttribute iAttribute : attributes) {
			if (!iAttribute.isPrivate() && iAttribute.getValue() instanceof String) {
				retour.put(iAttribute.getKey(), (String) iAttribute.getValue());
			}
		}
		return retour;
	}

	@Override
	public Map<String, Boolean> getBooleanAttributes() {
		Map<String, Boolean> retour = new HashMap<String, Boolean>();
		for (IAttribute iAttribute : attributes) {
			if (!iAttribute.isPrivate() && (iAttribute.getValue() instanceof Boolean)) {
				retour.put(iAttribute.getKey(), ((Boolean) iAttribute.getValue()));
			}
		}
		return retour;
	}

	@Override
	public Boolean isCheck() {
		return isCheck;
	}

	@Override
	public IStrategyExecution getStrategyExecution() {
		return strategyExecution;
	}

	@Override
	public IStrategyParcours getStrategyParcours() {
		return strategyParcours;
	}

	@Override
	public boolean continueExecution() {
		return this.getStrategyExecution().continueExecution();
	}
	
	@Override
	public void setSolution(A4Solution solution) {
		this.getStrategyExecution().setSolution(solution);
	}
	
	@Override
	public String parcours(A4Solution ans) {
		return this.getStrategyParcours().parcours(ans);
	}

	@Override
	public IProperties clone() {
		AbstractProperties object = null;
		try {
			// On récupère la copie de l'objet
			object = (AbstractProperties) super.clone();
			// On copie la liste d'attributs
			// on veut une "deep copy" (en profondeur) donc on parcours tous les éléments de la liste
			object.attributes = new ArrayList<IAttribute>();
			for (IAttribute attr : this.attributes) {
				object.attributes.add(attr.clone());
			}
			
			// On copie les stratégies
			object.strategyExecution = strategyExecution.clone();
			object.strategyParcours = strategyParcours.clone();
			
		} catch (CloneNotSupportedException e) {
			log.error("Impossible de cloner la propriété...");
		}
		return object;
	}
	
	protected void setModifiable(boolean isModifiable) {
		this.isModifiable = isModifiable;
	}
	
	@Override 
	public boolean isModifiable() {
		return isModifiable;
	}
	
	@Override
	public String getName() {
		return this.getClass().getSimpleName();
	}
	
	/**
	 * Supprime la clé si elle existe. Ne fait rien sinon.
	 * @param key
	 */
	private void remove(String key) {
		for (IAttribute iAttribute : attributes) {
			if (iAttribute.getKey().equals(key)) {
				attributes.remove(iAttribute);
				break;
			}
		}
	}

	@Override
	public void setEtatInitial(InitialState etat) {
		this.etatInitial = etat;
	}

	@Override
	public InitialState getEtatInitial() {
		return etatInitial;
	}
	
	protected void addReduction(IReduction reduc) {
		reductions.add(reduc);
	}
	
	@Override
	public Activity reduceActivityDiagram(Activity activity) {
		
		if (reductions.size() == 0)
			return activity;
		
		// puis on réduit
		for (IReduction reduction : reductions) {
			reduction.reduce(activity);
			}
		return activity;
	}
	
	/**
	 * Setter sur la dépendance 
	 * @param dependance
	 */
	protected void setDependance(Class<? extends IProperties> dependance) {
		if (dependance != null)
			this.dependance = dependance.getSimpleName();
	}
	
	@Override
	public String getDependance() {
		return this.dependance;
	}
}
