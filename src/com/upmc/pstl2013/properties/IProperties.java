package com.upmc.pstl2013.properties;

import java.util.Map;
import com.upmc.pstl2013.alloyGenerator.impl.JetException;

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
	 * Ajoute une association clé-valeur (string) à la propriété.
	 * 
	 * @param key la clé
	 * @param value la valeur string
	 */
	void put(String key, String value);
	
	/**
	 * Ajoute une association clé-valeur (booléen) à la propriété.
	 * 
	 * @param key la clé
	 * @param value la valeur booléenne
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
	 * Renvoie les attributs string.
	 * 
	 * @return une {@link Map} de clé (string) valeur (string).
	 */
	Map<String, String> getStringAttributes();
	
	/**
	 * Renvoie les attributs booléens.
	 * 
	 * @return une {@link Map} de clé (string) valeur (bool).
	 */
	Map<String, Boolean> getBooleanAttributes();
}
