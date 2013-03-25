package com.upmc.pstl2013.properties.dynamic;

import java.util.ArrayList;
import java.util.List;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.properties.IProperties;

/**
 * Représente la classe abstraite des stratégies de génération alloy pour les propriétés dynamiques.
 *
 */
public abstract class AbstractStrategyDynamicBusiness {
	

	private List<EParamType> inputs;
	private List<String> textsList;
	
	public AbstractStrategyDynamicBusiness() {
		super();
		inputs = new ArrayList<EParamType>();
	}
	
	protected void addInput(EParamType type) {
		inputs.add(type);
	}
	
	public List<EParamType> getParams() {
		return inputs;
	}
	
	protected void addTextList(String text) {
		if (textsList == null)
			textsList = new ArrayList<String>();
		textsList.add(text);
	}
	
	public List<String> getTextsList() {
		return textsList;
	}
	
	/**
	 * Cette méthode renvoie le code Alloy généré par la stratégie de propriété dynamique.
	 * Elle doit obligatoirement être redéfinie.
	 * @param argument la {@link IProperties} pour que le fichier Jet puisse récupérer les informations nécessaires à la génération.
	 * @return String le code alloy généré.
	 * @throws JetException une exception lancée en cas de problème de génération Jet.
	 */
	public abstract String generate(Object argument) throws JetException;
	
	/**
	 * Cette méthode peut être redéfinie pour montrer un exemple d'utilisation à l'utilisateur
	 * concernant la stratégie en question. Ce string sera affiché dans la popup.
	 * @return String un exemple d'utilisation.
	 */
	public String getExample() {
		return "Example : ";
	}
}
