package com.upmc.pstl2013.alloyGenerator.jet.impl;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;
import org.eclipse.uml2.uml.*;
import com.upmc.pstl2013.alloyGenerator.jet.*;
import com.upmc.pstl2013.properties.*;

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
  protected final String TEXT_2 = " -> 1 ,  // tokens //TODO pour chaque noeud Initial, NOM1 -> 1 + NOM2 -> 1 + " + NL + "\t\tActivityEdge -> 0  // offers" + NL + "\t]" + NL + "}" + NL + "" + NL + "// Timing" + NL + "one sig T extends Timing {} {" + NL + "\ttiming = (ActivityNode -> 0) " + NL + "}" + NL + "" + NL + "// Role Performer" + NL + "one sig Yoann extends RolePerformer {}" + NL + "one sig P extends Performer {} {" + NL + "\tperformer = ActivityNode -> Yoann" + NL + "}" + NL;
  protected final String TEXT_3 = NL + NL + "/** *Visualization Variables */" + NL + "// http://alloy.mit.edu/community/node/548" + NL + "fun vNodeExecuting : State->ActivityNode {" + NL + "   {s:State, a:ActivityNode | s.getTokens[a] > 0}" + NL + "}" + NL + "fun vEdgeHaveOffers : State->ActivityEdge {" + NL + "   {s:State, e:ActivityEdge | s.getOffers[e] > 0}" + NL + "}" + NL + "" + NL + "fun pinInNode : State->Action->Pin->Int {" + NL + "\t {s:State, a:Action, p:a.output+a.input, i:s.getTokens[p]}" + NL + "}";
  protected final String TEXT_4 = NL;

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
	IProperties propertie = jetHelper.getPropertie();
	if (propertie == null) {
		final String error = "La propriété est incorrecte.";
		log.error(error);
		throw new JetException(error);
	}

    stringBuffer.append(TEXT_1);
     //GENERATION DU NOEUD INITIAL EN DYNAMIQUE
		// on gère les cas sans noeud initial...
		String initialNode = propertie.getString("initialNode");
		if (initialNode == null) {
			final String error = "Le template Jet n'a pas trouvé de noeud initial.";
			log.error(error);
			throw new JetException(error);
		}
		stringBuffer.append(initialNode); 
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
		stringBuffer.append("one sig " + edge.getName() + " extends " + edge.eClass().getName() + " {}{" + NL);
		stringBuffer.append("    source = " + edge.getSource().getName() + NL);
		stringBuffer.append("    target = " + edge.getTarget().getName() + NL);
		stringBuffer.append("}" + NL);
	}

	// GENERATION DE LA VERIFICATION DU PROCESS EN FONCTION DE LA PROPRIETE (Cf. SubTemplate)
	stringBuffer.append(propertie.getAlloyCode());

    stringBuffer.append(TEXT_3);
    stringBuffer.append(TEXT_4);
    return stringBuffer.toString();
  }
}