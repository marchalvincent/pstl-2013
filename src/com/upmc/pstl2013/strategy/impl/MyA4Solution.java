package com.upmc.pstl2013.strategy.impl;

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
	
	private int nbRunning;
	private Map<Integer, List<String>> offers;
	private Map<Integer, List<String>> heldTokens;
	
	public MyA4Solution() {
		super();
		nbRunning = 0;
		offers = new HashMap<Integer, List<String>>();
		heldTokens = new HashMap<Integer, List<String>>();
	}
	
	public void setNbRunning(int nb) {
		nbRunning = nb;
	}
	
	public int getNbRunning() {
		return nbRunning;
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
	
	public List<String> getRound(Integer i) {
		List<String> liste = new ArrayList<String>();
		if (heldTokens.get(i) != null)
			liste.addAll(heldTokens.get(i));
		if (offers.get(i) != null)
			liste.addAll(offers.get(i));
		return liste;
	}
}
