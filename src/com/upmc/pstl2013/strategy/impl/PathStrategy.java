package com.upmc.pstl2013.strategy.impl;

import java.util.Iterator;

import com.upmc.pstl2013.strategy.IStrategy;

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
public class PathStrategy implements IStrategy {

	public PathStrategy() {
		super();
	}
	
	@Override
	public String parcours(A4Solution ans) {
		
		StringBuilder sb = new StringBuilder("Strategie de parcours - PathStrategy : ");
		// on parcours tous les Sig "semantic/State"
		for (Sig sig : ans.getAllReachableSigs()) {
			if (sig.label.equals("semantic/State")) {
				// on parcours tous les Fields "heldTokens"
				for (Field field : sig.getFields()) {
					if (field.label.equals("heldTokens")) {
						A4TupleSet setTuple = ans.eval(field);
						Iterator<A4Tuple> iterator = setTuple.iterator();
						
						Integer etape = new Integer(0);
						sb.append("( ");
						A4Tuple tuple;
						String[] sNumEtape, sNodeName;
						Integer iNumEtape, jeton;
						boolean changerParenthese = false;
						
						// Puis on parcours chaque "heldTokens"
						while (iterator.hasNext()) {
							tuple = iterator.next();
							
							/*
							 * Ici :
							 * tuple.atom(0) -> "semantic/State$0"
							 * tuple.atom(1) -> le nom du noeud + "$0"
							 * tuple.atom(2) -> le jeton qui est a 0 ou 1
							 */
							
							// 1. On regarde à quelle étape on est (on parse tuple.atom(0) )
							sNumEtape = tuple.atom(0).split("\\$");
							iNumEtape = new Integer(sNumEtape[1]);
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
					}
				}
			}
		}
		
		System.out.println(sb.toString());
		return sb.toString();
	}

}
