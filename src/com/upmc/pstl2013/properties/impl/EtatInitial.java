package com.upmc.pstl2013.properties.impl;

import java.util.Map;
import java.util.Set;

/**
 * Classe Holder qui va permettre de spécifier le nombre de jeton qu'aura un node ou un edge
 * à l'état initial du process.
 *
 */
public class EtatInitial {
	
	private Map<String, Integer> jetons;
	
	public EtatInitial() {
		
	}
	
	public void put(String nodeOrEdges, Integer numberJeton) {
		jetons.put(nodeOrEdges, numberJeton);
	}
	
	public Set<String> keySet() {
		return jetons.keySet();
	}
	
	public Integer get(String name) {
		return jetons.get(name);
	}
}
