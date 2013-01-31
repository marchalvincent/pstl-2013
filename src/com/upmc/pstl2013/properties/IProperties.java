package com.upmc.pstl2013.properties;

/**
 * Représente une méthode de vérification d'un fichier Alloy.
 * 
 */
public interface IProperties {

	/**
	 * Renvoie le code Alloy associé à cette propriété.
	 * 
	 * @return String du code alloy.
	 */
	String getAlloyCode();
	
	/**
	 * Ajoute une association clé-valeur à la propriété.
	 * @param key la clé
	 * @param value la valeur
	 */
	void putProperties(String key, String value);
}
