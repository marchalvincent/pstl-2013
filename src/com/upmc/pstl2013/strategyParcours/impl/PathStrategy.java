package com.upmc.pstl2013.strategyParcours.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import com.upmc.pstl2013.strategyParcours.IStrategyParcours;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig.Field;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Tuple;
import edu.mit.csail.sdg.alloy4compiler.translator.A4TupleSet;

/**
 * Représente une strategie de parcours qui permet de récupérer 
 * le chemin trouvé par la génération Alloy.
 * 
 */
public class PathStrategy implements IStrategyParcours {

	private MyA4Solution mySolution;

	/**
	 * Constructor
	 */
	public PathStrategy() {
		super();
		mySolution = new MyA4Solution();
	}

	@Override
	public String parcours(A4Solution ans) {
		// 1. On parse la solution alloy
		this.parse(ans);
		// 2. On renvoie le parcours de la solution
		return getParcours();
	}

	/**
	 * Parse le {@link A4Solution} d'alloy, et ne garde que les infos dont on a besoin dans l'instance
	 * de {@lkink MyA4Solution}.
	 * @param ans le {@link A4Solution}.
	 */
	private void parse(A4Solution ans) {
		// on parcours tous les Sig "semantic/State"
		for (Sig sig : ans.getAllReachableSigs()) {
			if (sig.label.equals("semantic/State")) {

				// les Field sont du type "heldTokens", "offers", "time" ou "running".
				for (Field field : sig.getFields()) {
					A4TupleSet tupleSet = ans.eval(field);
					if (field.label.equals("heldTokens")) {
						// on parse les "nodes"
						this.parseRounds(tupleSet, MyA4Solution.HELD_TOKEN);
					}
					else if (field.label.equals("offers")) {
						// on parse les "edges"
						this.parseRounds(tupleSet, MyA4Solution.OFFERS);
					}
					else if (field.label.equals("running")) {
						// on compte combien on a d'étape en mode "running"
						this.parseNbStateRunning(tupleSet);
					}
				}
			}
		}
	}

	/**
	 * Compte le nombre de "semantic/state" qui ont le flag running à "semantic/Running$0".
	 */
	private void parseNbStateRunning(A4TupleSet setTuple) {

		int nbRunning = 0;
		Iterator<A4Tuple> iterator = setTuple.iterator();
		// On parcours chaque "running"
		while (iterator.hasNext()) {
			A4Tuple tuple = iterator.next();
			if (tuple.toString().contains("semantic/Running$0")) {
				nbRunning++;
			}
		}
		mySolution.setNumStateFinished(nbRunning);
	}

	/**
	 * Parcours un {@link A4TupleSet} et récupère seulement les éléments dont le jeton est a 1.
	 * @param setTuple l'objet Alloy
	 * @param isHeldToken un entier spécifiant si 
	 */
	private void parseRounds(A4TupleSet setTuple, int isHeldToken) {

		A4Tuple tuple;
		String[] sNumEtape, sNodeName;
		Integer iNumEtape, jeton;

		// On parcours chaque "heldTokens"
		Iterator<A4Tuple> iterator = setTuple.iterator();
		while (iterator.hasNext()) {
			tuple = iterator.next();

			/*
			 * Ici : 
			 * tuple.atom(0) -> "semantic/State$x" avec x le numéro de l'étape
			 * tuple.atom(1) -> le nom du noeud + "$0" 
			 * tuple.atom(2) -> le jeton qui est a 0 ou 1
			 */
			
			// 1. On regarde si le jeton est supérieur à 0 ( avec tuple.atom(2) )
			jeton = new Integer(tuple.atom(2));
			if (jeton > 0) {
				
				// 2. On regarde à quelle étape on est (on parse tuple.atom(0) )
				sNumEtape = tuple.atom(0).split("\\$");
				iNumEtape = new Integer(sNumEtape[1]);

				// 3. on récupère le nom du noeud et on l'ajoute dans notre solution personnalisée
				sNodeName = tuple.atom(1).split("\\$");
				if (isHeldToken == MyA4Solution.HELD_TOKEN) {
					mySolution.addHeldTokens(iNumEtape, sNodeName[0]);
				}
				else if (isHeldToken == MyA4Solution.OFFERS) {
					mySolution.addOffers(iNumEtape, sNodeName[0]);
				}
			}
		}
	}

	/**
	 * Print le parcours de l'objet {@link MyA4Solution} qui est en variable d'instance.
	 * @return String, le chemin de la solution.
	 */
	private String getParcours() {
		Map<Integer, List<String>> heldTokens = mySolution.getHeldTokens();
		int numStateFinished = mySolution.getNumStateFinished();
		
		StringBuilder sb = new StringBuilder("");
		// pour chaque étape de la solution
		for (Integer key : heldTokens.keySet()) {
			// si ce numéro de state est encore dans l'état "running"
			if (key < numStateFinished) {
				sb.append("(");
				// on récupère tous les éléments de l'objet mySolution
				for (String name : heldTokens.get(key)) {
					sb.append(" ");
					sb.append(name);
				}
				sb.append(" )");
			}
		}
		return sb.toString();
	}

	@Override
	public IStrategyParcours clone() throws CloneNotSupportedException {
		PathStrategy object = null;
		
		object = (PathStrategy) super.clone();
		// pas besoin de cloner cet objet, quand on fait appel à la méthode clone
		// l'objet n'a pas encore été rempli
		object.mySolution = new MyA4Solution();
		
		return object;
	}
}
