package com.upmc.pstl2013.alloyGenerator.jet.impl;

import java.util.Map;
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
  protected final String TEXT_1 = NL + "pred final {\t" + NL + "\tsome s:State | s.getTokens[$FINALNODE$] > 0" + NL + "}" + NL + "" + NL + "check {final}  for $INC$ State, $NB$ Object, $NAN$ ActivityNode, $NAE$ ActivityEdge";
  protected final String TEXT_2 = NL;

	@Override
	public String generate(Object argument) throws JetException
  {
    final StringBuffer stringBuffer = new StringBuffer();
     
	final Logger log = Logger.getLogger(DeadLockTemplate.class);
	if (!(argument instanceof Map<?, ?>)) {
		final String error = "L'argument passé au template Jet n'est pas une Map.";
		log.error(error);
		throw new JetException(error);
	}

	Map<String, String> properties = (Map) argument;
	

    stringBuffer.append(TEXT_1);
    stringBuffer.append(TEXT_2);
    return stringBuffer.toString();
  }
}