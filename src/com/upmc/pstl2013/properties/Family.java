package com.upmc.pstl2013.properties;


/**
 * Représente les familles de propriétés. Une famille peut appartenir elle-même à une famille.
 * 
 */
public enum Family {

	BEHAVIORAL(null),
	SOUDNESS(Family.BEHAVIORAL),
	ORGANIZATIONAL(Family.BEHAVIORAL),
	BUISINESS(Family.BEHAVIORAL),
	SYNTACTICAL(null);
	
	private Family parent;
	
	private Family(Family parent) {
		this.parent = parent;
	}
	
	public boolean hasParent() {
		return parent != null;
	}
	
	public Family getParent() {
		return parent;
	}
}
