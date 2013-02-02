package com.upmc.pstl2013.alloyGenerator.jet.impl;

import org.apache.log4j.Logger;
import com.upmc.pstl2013.alloyGenerator.jet.*;
import com.upmc.pstl2013.alloyGenerator.impl.*;
import com.upmc.pstl2013.properties.*;

public class DeadLockTemplate implements IJetTemplate {

  protected static String nl;
  public static synchronized DeadLockTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    DeadLockTemplate result = new DeadLockTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "pred ";
  protected final String TEXT_2 = " {" + NL + "\tsome s:State | s.getTokens[";
  protected final String TEXT_3 = "] > 0" + NL + "}" + NL;
  protected final String TEXT_4 = NL + "check {";
  protected final String TEXT_5 = "} for ";
  protected final String TEXT_6 = " State, ";
  protected final String TEXT_7 = " Object, ";
  protected final String TEXT_8 = " ActivityNode, ";
  protected final String TEXT_9 = " ActivityEdge expect 0" + NL + "//check tall for 20 State ,  15 Object, 5 ActivityNode, 4 ActivityEdge expect 0";
  protected final String TEXT_10 = NL;

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
	final String predicatName = propertie.getString("predicatName");

    stringBuffer.append(TEXT_1);
    stringBuffer.append(predicatName);
    stringBuffer.append(TEXT_2);
    stringBuffer.append(propertie.getString("finalNode"));
    stringBuffer.append(TEXT_3);
    
	final String inc = propertie.getString("inc");
	final String nbObjects = propertie.getString("nbObjects");
	final String nbNodes = propertie.getString("nbNodes");
	final String nbEdges = propertie.getString("nbEdges");

    stringBuffer.append(TEXT_4);
    stringBuffer.append(predicatName);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(inc);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(nbObjects);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(nbNodes);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(nbEdges);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
}