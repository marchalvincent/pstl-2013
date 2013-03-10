package com.upmc.pstl2013.strategyParcours.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Classe utilitaire pour la stratégie {@link PathStrategy}. Permet de stocker 
 * seulement les informations nécessaires lors du parcours de la solution Alloy.
 *
 */
public class MyA4Solution {

	public static int HELD_TOKEN = 1;
	public static int OFFERS = 2;
	
	private int numStateFinished;
	private Map<Integer, List<String>> offers;
	private Map<Integer, List<String>> heldTokens;
	
	public MyA4Solution() {
		super();
		numStateFinished = 0;
		offers = new HashMap<Integer, List<String>>();
		heldTokens = new HashMap<Integer, List<String>>();
	}
	
	public void setNumStateFinished(int nb) {
		numStateFinished = nb;
	}
	
	public int getNumStateFinished() {
		return numStateFinished;
	}
	
	public void addOffers(Integer nbRound, String name) {
		if (offers.get(nbRound) == null) {
			offers.put(nbRound, new ArrayList<String>());
		}
		offers.get(nbRound).add(name);
	}
	
	public void addHeldTokens(Integer nbRound, String name) {
		if (heldTokens.get(nbRound) == null) {
			heldTokens.put(nbRound, new ArrayList<String>());
		}
		heldTokens.get(nbRound).add(name);
	}
	
	public Map<Integer, List<String>> getHeldTokens() {
		return heldTokens;
	}
}
