package com.upmc.pstl2013.strategy.impl;

import java.util.Iterator;

import com.upmc.pstl2013.strategy.IStrategy;

import edu.mit.csail.sdg.alloy4compiler.ast.Sig;
import edu.mit.csail.sdg.alloy4compiler.ast.Sig.Field;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Solution;
import edu.mit.csail.sdg.alloy4compiler.translator.A4Tuple;
import edu.mit.csail.sdg.alloy4compiler.translator.A4TupleSet;

/**
 * Représente une strategie de parcours qui permet de récupérer le chemin trouvé par la génération
 * Alloy.
 * 
 */
public class PathStrategy implements IStrategy {

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
		
		String retour = "";
		int nbRunning = 0;
		
		// on parcours tous les Sig "semantic/State"
		for (Sig sig : ans.getAllReachableSigs()) {
			if (sig.label.equals("semantic/State")) {
				
				for (Field field : sig.getFields()) {
					System.out.println("test : " + field.label);
				}
				
				// 1. On récupère le nombre d'étapes en mode "running". Les autres ne doivent pas compter.
				for (Field field : sig.getFields()) {
					if (field.label.equals("running")) {
						nbRunning = getNbStateRunning(ans.eval(field));
						Iterator<A4Tuple> iterator = ans.eval(field).iterator();
						while (iterator.hasNext()) {
							A4Tuple t = iterator.next();
							System.out.println(t.atom(0));
							System.out.println(t.atom(1));
							
						}
					}
				}
				
				// 2. On parcours tous les Fields "heldTokens" pour avoir le chemin.
				for (Field field : sig.getFields()) {
					if (field.label.equals("heldTokens")) {
						retour = getPath(ans.eval(field), nbRunning);
					}
				}
			}
		}
		return retour;
	}
	
	private void parse(A4Solution ans) {
		
	}
	
	/**
	 * Renvoie le nombre de "semantic/state" qui ont le flag running à semantic/Running$0
	 * @return
	 */
	private int getNbStateRunning(A4TupleSet setTuple) {
		
		int nbRunning = 0;
		Iterator<A4Tuple> iterator = setTuple.iterator();
		// On parcours chaque "running"
		while (iterator.hasNext()) {
			A4Tuple tuple = iterator.next();
			if (tuple.toString().contains("semantic/Running$0")) {
				nbRunning++;
			}
		}
		System.out.println("nombre de running : " + nbRunning);
		return nbRunning;
	}
	
	/**
	 * Renvoie le chemin parcouru pour {@code nbRunning} étapes.
	 * @param setTuple 
	 * @param nbRunning
	 * @return
	 */
	private String getPath(A4TupleSet setTuple, int nbRunning) {

		A4Tuple tuple;
		String[] sNumEtape, sNodeName;
		Integer iNumEtape, jeton;
		Integer etape = new Integer(0);
		boolean changerParenthese = false;
		
		StringBuilder sb = new StringBuilder("Strategie de parcours - PathStrategy : ");
		sb.append("( ");
		
		Iterator<A4Tuple> iterator = setTuple.iterator();
		// On parcours chaque "heldTokens"
		while (iterator.hasNext()) {
			tuple = iterator.next();
			/*
			 * Ici : tuple.atom(0) -> "semantic/State$0" tuple.atom(1) -> le nom du noeud + "$0" 
			 * tuple.atom(2) -> le jeton qui est a 0 ou 1
			 */
			// 1. On regarde à quelle étape on est (on parse tuple.atom(0) )
			sNumEtape = tuple.atom(0).split("\\$");
			iNumEtape = new Integer(sNumEtape[1]);
			
			// si on n'est plus dans les étapes en mode "running"
			if (iNumEtape > nbRunning) {
				break;
			}
			
			// si c'est une nouvelle étape, on modifie le stringBuilder
			if (iNumEtape.compareTo(etape) != 0) {
				etape++;
				changerParenthese = true;
			}
			// 2. On regarde si le jeton est supérieur à 0 ( avec tuple.atom(2) )
			jeton = new Integer(tuple.atom(2));
			if (jeton > 0) {
				// si on est a une étape suivante, on change de parenthèse
				if (changerParenthese) {
					changerParenthese = false;
					sb.append("), ( ");
				}
				// 3. on récupère le nom du noeud
				sNodeName = tuple.atom(1).split("\\$");
				sb.append(sNodeName[0] + " ");
			}
		}
		sb.append(")");
		return sb.toString();
	}
}
