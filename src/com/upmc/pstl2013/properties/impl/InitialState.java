package com.upmc.pstl2013.properties.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Classe Holder qui va permettre de spécifier le nombre de jeton qu'aura un node ou un edge
 * à l'état initial du process.
 *
 */
public class InitialState {

	private Map<String, Integer> nodes;
	private Map<String, Integer> edges;
	
	public InitialState() {
		nodes = new HashMap<String, Integer>();
		edges = new HashMap<String, Integer>();
	}
	
	public InitialState(HashMap<String, Integer> nodes, HashMap<String, Integer> edges) {
		this.nodes = nodes;
		this.edges = edges;
	}
	
	public void putNode(String node, Integer numberJeton) {
		nodes.put(node, numberJeton);
	}
	
	public Set<String> keySetNode() {
		return nodes.keySet();
	}
	
	public Integer getNode(String name) {
		return nodes.get(name);
	}
	
	public void putEdge(String edge, Integer numberJeton) {
		edges.put(edge, numberJeton);
	}
	
	public Set<String> keySetEdge() {
		return edges.keySet();
	}
	
	public Integer getEdge(String name) {
		return edges.get(name);
	}
	
	/**
	 * Renvoie true si au moins un noeud possède un jeton. Cette méthode est utilisée 
	 * pour la génération Alloy.
	 * @return
	 */
	public boolean hasNodeInit() {
		for (String name : nodes.keySet()) {
			if (nodes.get(name) > 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Renvoie true si au moins un edge possède un jeton. Cette méthode est utilisée 
	 * pour la génération Alloy.
	 * @return
	 */
	public boolean hasEdgeInit() {
		for (String name : edges.keySet()) {
			if (edges.get(name) > 0) {
				return true;
			}
		}
		return false;
	}
}
