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
	 * 
	 * @param key la clé
	 * @param value la valeur
	 */
	void put(String key, String value);
	
	/**
	 * Renvoie la valeur associée à la clé passée en paramètre.
	 * 
	 * @param key la clé.
	 * @return String la valeur associée.
	 */
	String get(String key);
}
