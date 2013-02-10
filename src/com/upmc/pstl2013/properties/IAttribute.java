package com.upmc.pstl2013.properties;

/**
 * Représente l'interface d'un attribut d'une {@link IProperties}.
 * @author Vincent
 *
 */
public interface IAttribute {
	
	String getKey();
	
	Object getValue();
	
	Boolean isPrivate();
}
