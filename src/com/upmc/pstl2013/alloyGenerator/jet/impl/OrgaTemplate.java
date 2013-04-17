package com.upmc.pstl2013.alloyGenerator.jet.impl;

import org.apache.log4j.Logger;
import com.upmc.pstl2013.alloyGenerator.jet.*;
import com.upmc.pstl2013.properties.*;

public class OrgaTemplate implements IJetTemplate {

  protected static String nl;
  public static synchronized OrgaTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    OrgaTemplate result = new OrgaTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "pred ";
  protected final String TEXT_2 = " {" + NL + "\tall s:State | #{ node : ExecutableNode | s.hasTokens[node] } <= #RolePerformer" + NL + "}" + NL;
  protected final String TEXT_3 = NL + "check {";
  protected final String TEXT_4 = "} for ";
  protected final String TEXT_5 = " State, ";
  protected final String TEXT_6 = " Object, ";
  protected final String TEXT_7 = " ActivityNode, ";
  protected final String TEXT_8 = " ActivityEdge, ";
  protected final String TEXT_9 = " Int";
  protected final String TEXT_10 = NL;

	@Override
	public String generate(Object argument) throws JetException
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
	final Logger log = Logger.getLogger(OrgaTemplate.class);
	if (!(argument instanceof IProperties)) {
		final String error = "L'argument passé au template Jet n'est pas une IProperties.";
		log.error(error);
		throw new JetException(error);
	}

	IProperties property = (IProperties) argument;
	final String predicatName = property.getString("predicatName");

    stringBuffer.append(TEXT_1);
    stringBuffer.append(predicatName);
    stringBuffer.append(TEXT_2);
    
	final String inc = property.getString("nbState");
	final String nbObjects = property.getString("nbObjects");
	final String nbNodes = property.getString("nbNodes");
	final String nbEdges = property.getString("nbEdges");
	final String bitwidth = property.getString("bitwidth");

    stringBuffer.append(TEXT_3);
    stringBuffer.append(predicatName);
    stringBuffer.append(TEXT_4);
    stringBuffer.append(inc);
    stringBuffer.append(TEXT_5);
    stringBuffer.append(nbObjects);
    stringBuffer.append(TEXT_6);
    stringBuffer.append(nbNodes);
    stringBuffer.append(TEXT_7);
    stringBuffer.append(nbEdges);
    stringBuffer.append(TEXT_8);
    stringBuffer.append(bitwidth);
    stringBuffer.append(TEXT_9);
    stringBuffer.append(TEXT_10);
    return stringBuffer.toString();
  }
}