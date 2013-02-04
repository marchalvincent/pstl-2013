package com.upmc.pstl2013.properties;

import java.util.Map;
import com.upmc.pstl2013.alloyGenerator.jet.JetException;
import com.upmc.pstl2013.strategy.IStrategy;

/**
 * Représente une méthode de vérification d'un fichier Alloy.
 * 
 */
public interface IProperties {

	/**
	 * Renvoie le code Alloy associé à cette propriété.
	 * 
	 * @return String du code alloy.
	 * @throws JetException En cas d'erreur lors de la génération Jet.
	 */
	String getAlloyCode() throws JetException;
	
	/**
	 * Ajoute ou modifie un attribut caché à l'interface utilisateur.
	 * @param key le nom de l'attribut.
	 * @param value sa valeur.
	 */
	void putPrivate(String key, String value);
	
	/**
	 * Ajoute ou modifie un attribut de la propriété.
	 * 
	 * @param key le nom de l'attribut.
	 * @param value sa valeur.
	 */
	void put(String key, String value);
	
	/**
	 * Ajoute ou modifie un attribut de la propriété.
	 * 
	 * @param key le nom de l'attribut.
	 * @param value sa valeur.
	 */
	void put(String key, Boolean value);
	
	/**
	 * Renvoie la valeur string associée à la clé passée en paramètre.
	 * 
	 * @param key la clé.
	 * @return String la valeur associée.
	 */
	String getString(String key);
	
	/**
	 * Renvoie la valeur booléenne associée à la clé passée en paramètre.
	 * 
	 * @param key la clé.
	 * @return Boolean la valeur associée.
	 */
	Boolean getBoolean(String key);
	
	/**
	 * Renvoie les attributs string qui doivent être affiché dans l'IU.
	 * 
	 * @return une {@link Map} de clé (string) valeur (string).
	 */
	Map<String, String> getStringAttributes();
	
	/**
	 * Renvoie les attributs booléens qui doivent être affiché dans l'IU.
	 * 
	 * @return une {@link Map} de clé (string) valeur (bool).
	 */
	Map<String, Boolean> getBooleanAttributes();
	
	/**
	 * Renvoie vrai si cette propriété est un "check" alloy. False si c'est un "run".
	 * @return Boolean.
	 */
	Boolean isCheck();
	
	/**
	 * Renvoie la {@link IStrategy} de parcours Alloy associée à cette propriété.
	 * @return
	 */
	IStrategy getStrategy();
}
