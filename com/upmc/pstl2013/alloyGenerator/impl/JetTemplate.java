package com.upmc.pstl2013.alloyGenerator.impl;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.*;

public class JetTemplate implements IJetTemplate {

  protected static String nl;
  public static synchronized JetTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    JetTemplate result = new JetTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "module process" + NL + "" + NL + "open syntax" + NL + "open semantic" + NL + "" + NL + "fact initTokens {" + NL + "\tInit[  " + NL + "\t\t";
  protected final String TEXT_2 = " -> 1 ,  // tokens //TODO pour chaque noeud Initial, NOM1 -> 1 + NOM2 -> 1 + " + NL + "\t\tActivityEdge -> 0  // offers\t" + NL + "\t]" + NL + "}" + NL + "" + NL + "// Timing" + NL + "one sig T extends Timing {} {" + NL + "\ttiming = (ActivityNode -> 0) " + NL + "}" + NL + "" + NL + "// Role Performer" + NL + "one sig Yoann extends RolePerformer {}" + NL + "one sig P extends Performer {} {" + NL + "\tperformer = ActivityNode -> Yoann" + NL + "}" + NL;
  protected final String TEXT_3 = NL;
  protected final String TEXT_4 = NL + "pred ";
  protected final String TEXT_5 = " {" + NL + "\t//some s : State | s.getTokens[Final] = 1 // 4 Solution" + NL + "\tsome s:State | s.getTokens[";
  protected final String TEXT_6 = "] > 0" + NL + "}" + NL + "" + NL + "" + NL + "" + NL + "/////////////" + NL + "" + NL + "" + NL + "pred testAll {" + NL + "\t";
  protected final String TEXT_7 = " " + NL + "}" + NL + "" + NL + "assert tall {" + NL + "\ttestAll" + NL + "}" + NL + "" + NL + "//TODO le nombre peux State peux augmenter ex: 20 State ou 30 State etc..." + NL + "//run testAll for 0 but 20 State ,  15 Object, 5 ActivityNode, 4 ActivityEdge expect 1" + NL + "//check tall for 20 State ,  15 Object, 5 ActivityNode, 4 ActivityEdge expect 0";
  protected final String TEXT_8 = NL + NL + NL + NL + "/** *Visualization Variables */" + NL + "// http://alloy.mit.edu/community/node/548" + NL + "fun vNodeExecuting : State->ActivityNode {" + NL + "   {s:State, a:ActivityNode | s.getTokens[a] > 0}" + NL + "}" + NL + "fun vEdgeHaveOffers : State->ActivityEdge {" + NL + "   {s:State, e:ActivityEdge | s.getOffers[e] > 0}" + NL + "}" + NL + "" + NL + "fun pinInNode : State->Action->Pin->Int {" + NL + "\t {s:State, a:Action, p:a.output+a.input, i:s.getTokens[p]}" + NL + "}";
  protected final String TEXT_9 = NL;

	@Override
	public String generate(Object argument) throws JetException
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
	final Logger log = Logger.getLogger(JetTemplate.class);
	if (!(argument instanceof IJetHelper)) {
		final String error = "L'argument passé au template Jet n'est pas un IJetHelper.";
		log.error(error);
		throw new JetException(error);
	}
	IJetHelper jetHelper = (IJetHelper) argument;

    stringBuffer.append(TEXT_1);
     //GENERATION DU NOEUD INITIAL EN DYNAMIQUE
		// on gère les cas sans noeud initial...
		ActivityNode initialNode = jetHelper.getInitialNode();
		if (initialNode == null) {
			final String error = "Le fichier n'a pas trouvé de noeud initial.";
			log.error(error);
			throw new JetException(error);
		}
		stringBuffer.append(initialNode.getName().replace("-", "")); 
    stringBuffer.append(TEXT_2);
     // GENERATION DES NOEUDS ET EDGES EN DYNAMIQUE
	EList<ActivityNode> nodes = jetHelper.getNodes();
	EList<ActivityEdge> edges = jetHelper.getEdges();
	
	stringBuffer.append("------Generated Nodes------" + NL);
	for (ActivityNode node : nodes) {
		stringBuffer.append("one sig " + node.getName().replace("-", "") + " extends " + node.eClass().getName() + " {}{}" + NL);
	}
	
	stringBuffer.append(NL + NL + "------Generated Edges------" + NL);
	for (ActivityEdge edge : edges) {
		stringBuffer.append("one sig " + edge.getName().replace("-", "") + " extends " + edge.eClass().getName() + " {}{" + NL);
		stringBuffer.append("    source = " + edge.getSource().getName() + NL);
		stringBuffer.append("    target = " + edge.getTarget().getName() + NL);
		stringBuffer.append("}" + NL);
	}

    stringBuffer.append(TEXT_3);
     // GENERATION DU NOEUD FINAL EN DYNAMIQUE
	// on gère les cas sans noeud finaux...
	ActivityNode finalNode = jetHelper.getfinalNode();
	if (finalNode == null) {
		final String error = "Le fichier n'a pas trouvé de noeud final.";
		log.error(error);
		throw new JetException(error);
	}
	
	String namePredicat = jetHelper.getNameFinalPredicat();
	//TODO générer le nom du prédicat

    stringBuffer.append(TEXT_4);
     stringBuffer.append(namePredicat); 
    stringBuffer.append(TEXT_5);
     stringBuffer.append(finalNode); 
    stringBuffer.append(TEXT_6);
     stringBuffer.append(namePredicat); 
    stringBuffer.append(TEXT_7);
    
	IProperties properties = jetHelper.getProperties();
	if (properties == null) {
		final String error = "Les propriétés sont incorrectes.";
		log.error(error);
		throw new JetException(error);
	}
	stringBuffer.append(properties.getAlloyCode() + NL);

    stringBuffer.append(TEXT_8);
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
}