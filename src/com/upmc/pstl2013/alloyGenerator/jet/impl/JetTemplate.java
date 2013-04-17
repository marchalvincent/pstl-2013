package com.upmc.pstl2013.alloyGenerator.jet.impl;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.*;
import com.upmc.pstl2013.alloyGenerator.jet.*;
import com.upmc.pstl2013.properties.*;
import com.upmc.pstl2013.properties.impl.InitialState;

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
  protected final String TEXT_2 = NL + "\t]" + NL + "}" + NL;
  protected final String TEXT_3 = NL + "\t" + NL + "fact fairness {" + NL + "\tall decision:DecisionNode | fairDecision[decision]" + NL + "}" + NL + "" + NL + "pred fairDecision[node:DecisionNode] {" + NL + "\tall edge:node.outgoing | " + NL + "\t\t#{getNumberOfEdgeActivation[edge]} <= 2" + NL + "}" + NL + "" + NL + "fun getNumberOfEdgeActivation[edge:ActivityEdge] : State->State {" + NL + "\t{ s1,s2 : State | s1.next = s2 and s1.getOffers[edge] < s2.getOffers[edge]  }" + NL + "}" + NL;
  protected final String TEXT_4 = NL + NL + "fact enoughState {" + NL + "\tsome s:State | s.running = Finished" + NL + "}" + NL;
  protected final String TEXT_5 = NL + NL + "/** *Visualization Variables */" + NL + "// http://alloy.mit.edu/community/node/548" + NL + "fun vNodeExecuting : State->ActivityNode {" + NL + "   {s:State, a:ActivityNode | s.getTokens[a] > 0}" + NL + "}" + NL + "fun vEdgeHaveOffers : State->ActivityEdge {" + NL + "   {s:State, e:ActivityEdge | s.getOffers[e] > 0}" + NL + "}" + NL + "" + NL + "fun pinInNode : State->Action->Pin->Int {" + NL + "\t {s:State, a:Action, p:a.output+a.input, i:s.getTokens[p]}" + NL + "}";
  protected final String TEXT_6 = NL;

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
	
	// on récupère la propriété
	IProperties property = jetHelper.getProperty();
	if (property == null) {
		final String error = "La propriété est incorrecte.";
		log.error(error);
		throw new JetException(error);
	}
	
	// GENERATION DYNAMIQUE DE L'ETAT INITIAL
	StringBuffer stringInit = new StringBuffer();
	
	// on regarde si l'objet property possède un EtatInitial
	InitialState etatInit = property.getEtatInitial();
	
	// si on n'a pas d'état initial, on fait par défaut
	if (etatInit == null) {
		stringInit.append("ActivityNode -> 0 +");
		// on gère les cas sans noeud initial...
		String allInitialName = property.getString("initialNode");
		if (allInitialName.equals("")) {
			final String error = "Le template Jet n'a pas trouvé de noeud initial.";
			log.error(error);
			throw new JetException(error);
		} else {
			String[] initialNodes = allInitialName.split("---");
			for (String initialName : initialNodes) {
				stringInit.append("+ ");
				stringInit.append(initialName);
				stringInit.append(" -> 1 ");
			}
		}
		stringInit.append(", // tokens \n		ActivityEdge -> 0  // offers");
	}
	
	// sinon on parse l'objet EtatInitial pour définir le process
	else {
		// on s'occupe d'abord des nodes
		if (etatInit.hasNodeInit()) {
			stringInit.append("ActivityNode -> 0 +");
			for (String name : etatInit.keySetNode()) {
				Integer number = etatInit.getNode(name);
				if (number > 0) {
					stringInit.append("+ ");
					stringInit.append(name);
					stringInit.append(" -> ");
					stringInit.append(number);
					stringInit.append(" ");
				}
			}
			stringInit.append(", // tokens\n");
		}
		// si on n'a aucun noeud avec le init
		else {
			stringInit.append("ActivityNode -> 0, \n");
		}
		
		// on s'occupe maintenant des edges
		if (etatInit.hasEdgeInit()) {
			stringInit.append("		ActivityEdge -> 0 +");
			for (String name : etatInit.keySetEdge()) {
				Integer number = etatInit.getEdge(name);
				if (number > 0) {
					stringInit.append("+ ");
					stringInit.append(name);
					stringInit.append(" -> ");
					stringInit.append(number);
					stringInit.append(" ");
				}
			}
			stringInit.append("// offers");
		}
		// si on n'a aucun noeud avec le init
		else {
			stringInit.append("		ActivityEdge -> 0");
		}
	}

    stringBuffer.append(TEXT_1);
    stringBuffer.append(stringInit.toString());
    stringBuffer.append(TEXT_2);
     
	// GENERATION DES NOEUDS ET EDGES EN DYNAMIQUE
	EList<ActivityNode> nodes = jetHelper.getNodes();
	EList<ActivityEdge> edges = jetHelper.getEdges();
	
	stringBuffer.append("------Generated Nodes------" + NL);
	for (ActivityNode node : nodes) {
		stringBuffer.append("one sig " + node.getName() + " extends " + node.eClass().getName() + " {}{}" + NL);
	}
	
	stringBuffer.append(NL + NL + "------Generated Edges------" + NL);
	for (ActivityEdge edge : edges) {
	
		// la source
		String source = null;
		if (edge.getSource() != null && edge.getSource().getName() != null) {
			source = edge.getSource().getName();
		} else {
			source = "none";
		}
		
		// la destination
		String target = null;
		if (edge.getTarget() != null && edge.getTarget().getName() != null) {
			target = edge.getTarget().getName();
		} else {
			target = "none";
		}
	
		stringBuffer.append("one sig " + edge.getName() + " extends " + edge.eClass().getName() + " {}{" + NL);
		stringBuffer.append("    source = " + source + NL);
		stringBuffer.append("    target = " + target + NL);
		stringBuffer.append("}" + NL);
	}
	
	// GENERATION DE LA PROPRIETE FAIRNESS
	Boolean isFairness = property.getBoolean("fairness");
	if (isFairness) {
    stringBuffer.append(TEXT_3);
    	} else {
    stringBuffer.append(TEXT_4);
    	}

	// GENERATION DE LA VERIFICATION DU PROCESS EN FONCTION DE LA PROPRIETE (Cf. subTemplates ou subTemplates/dynamics)
	stringBuffer.append(property.getAlloyCode());

    stringBuffer.append(TEXT_5);
    stringBuffer.append(TEXT_6);
    return stringBuffer.toString();
  }
}