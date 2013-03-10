package com.upmc.pstl2013.properties.dynamic.impl;

import org.apache.log4j.Logger;
import com.upmc.pstl2013.alloyGenerator.jet.*;
import com.upmc.pstl2013.properties.*;
import com.upmc.pstl2013.properties.dynamic.AbstractStrategyDynamicBusiness;

public class TestDynamicsTemplate extends AbstractStrategyDynamicBusiness implements IJetTemplate {

  protected static String nl;
  public static synchronized TestDynamicsTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    TestDynamicsTemplate result = new TestDynamicsTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "pred ";
  protected final String TEXT_2 = " {" + NL + "\t// TODO predicat en fonction de la propriété dynamique" + NL + "\tpredicat de vincent en dynamique ici" + NL + "}" + NL;
  protected final String TEXT_3 = NL + "check {";
  protected final String TEXT_4 = "} for ";
  protected final String TEXT_5 = " State, ";
  protected final String TEXT_6 = " Object, ";
  protected final String TEXT_7 = " ActivityNode, ";
  protected final String TEXT_8 = " ActivityEdge expect 0";
  protected final String TEXT_9 = NL;

	@Override
	public String generate(Object argument) throws JetException
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
	final Logger log = Logger.getLogger(TestDynamicsTemplate.class);
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
    
	final String inc = propertie.getString("nbState");
	final String nbObjects = propertie.getString("nbObjects");
	final String nbNodes = propertie.getString("nbNodes");
	final String nbEdges = propertie.getString("nbEdges");

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
    stringBuffer.append(TEXT_9);
    return stringBuffer.toString();
  }
}