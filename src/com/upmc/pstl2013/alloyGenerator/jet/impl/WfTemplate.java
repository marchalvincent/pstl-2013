package com.upmc.pstl2013.alloyGenerator.jet.impl;

import org.apache.log4j.Logger;
import com.upmc.pstl2013.alloyGenerator.jet.*;
import com.upmc.pstl2013.properties.*;

public class WfTemplate implements IJetTemplate {

  protected static String nl;
  public static synchronized WfTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    WfTemplate result = new WfTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = "fact initialNode {" + NL + "\tall n : InitialNode | {" + NL + "\t\t#n.incoming = 0 // [1] An initial node has no incoming edges." + NL + "\t}" + NL + "}" + NL + "fact initialNode2 {" + NL + "\tno edge : ObjectFlow | edge.source in InitialNode // [2] Only control edges can have initial nodes as source." + NL + "}";
  protected final String TEXT_2 = NL + "fact activityFinalNode {" + NL + "\tall n : ActivityFinalNode | {" + NL + "\t\t#n.outgoing = 0//\t[1] A final node has no outgoing edges." + NL + "\t}" + NL + "}";
  protected final String TEXT_3 = NL + "fact mergeNode {" + NL + "\tall n : MergeNode | {" + NL + "\t\t#n.outgoing = 1 // [1] A merge node has one outgoing edge." + NL + "\t\tand" + NL + "\t\t// [2] The edges coming into and out of a merge node must be either all object flows or all control flows." + NL + "\t\tall edgeIn : n.incoming, edgeOut : n.outgoing | " + NL + "\t\t\t(edgeIn in ControlFlow and edgeOut in ControlFlow)" + NL + "\t\t\tor" + NL + "\t\t\t(edgeIn in ObjectFlow and edgeOut in ObjectFlow)" + NL + "\t}" + NL + "}";
  protected final String TEXT_4 = NL + "run {} for 0 but 0 State, ";
  protected final String TEXT_5 = " Object, ";
  protected final String TEXT_6 = " ActivityNode, ";
  protected final String TEXT_7 = " ActivityEdge";
  protected final String TEXT_8 = NL;

	@Override
	public String generate(Object argument) throws JetException
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
	final Logger log = Logger.getLogger(DeadLockTemplate.class);
	if (!(argument instanceof IProperties)) {
		final String error = "L'argument passé au template Jet n'est pas une IProperties.";
		log.error(error);
		throw new JetException(error);
	}

	IProperties propertie = (IProperties) argument;
	final Boolean isInitial = propertie.getBoolean("initial");
	final Boolean isFinal = propertie.getBoolean("final");
	final Boolean isMerge = propertie.getBoolean("merge");

	// CAS INITIAL
	if (isInitial) {

    stringBuffer.append(TEXT_1);
     
	}
	
	// CAS FINAL
	if (isFinal) {

    stringBuffer.append(TEXT_2);
    
	}
	
	//CAS MERGE
	if (isMerge) {

    stringBuffer.append(TEXT_3);
    
	}

	// et le petit run a la fin...
	final String nbObjects = propertie.getString("nbObjects");
	final String nbNodes = propertie.getString("nbNodes");
	final String nbEdges = propertie.getString("nbEdges");

    stringBuffer.append(TEXT_4);
    stringBuffer.append(nbObjects);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(nbNodes);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(nbEdges);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(TEXT_8);
    return stringBuffer.toString();
  }
}