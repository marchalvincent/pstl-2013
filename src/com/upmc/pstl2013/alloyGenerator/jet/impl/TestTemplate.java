package com.upmc.pstl2013.alloyGenerator.jet.impl;

import com.upmc.pstl2013.alloyGenerator.jet.*;

public class TestTemplate implements IJetTemplate {

  protected static String nl;
  public static synchronized TestTemplate create(String lineSeparator)
  {
    nl = lineSeparator;
    TestTemplate result = new TestTemplate();
    nl = null;
    return result;
  }

  public final String NL = nl == null ? (System.getProperties().getProperty("line.separator")) : nl;
  protected final String TEXT_1 = NL + "test vincent ici";

	@Override
	public String generate(Object argument) throws JetException
  {
    final StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(TEXT_1);
    return stringBuffer.toString();
  }
}