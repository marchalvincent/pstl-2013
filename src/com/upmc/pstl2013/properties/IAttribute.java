package com.upmc.pstl2013.properties;

/**
 * Représente l'interface d'un attribut d'une {@link IProperties}.
 * 
 * @extends Cloneable, un {@code IAttribute} doit pouvoir être cloné. Cf. {@link IProperties}.
 *
 */
public interface IAttribute extends Cloneable {
	
	String getKey();
	
	Object getValue();
	
	Boolean isPrivate();
	
	IAttribute clone() throws CloneNotSupportedException;
}
