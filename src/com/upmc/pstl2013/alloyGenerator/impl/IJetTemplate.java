package com.upmc.pstl2013.alloyGenerator.impl;

import com.upmc.pstl2013.GenericException;

/**
 * L'interface du template Jet.
 *
 */
public interface IJetTemplate {

	/**
	 * La méthode est dans la possibilité de renvoyer une exception en cas de problème.
	 * @param argument
	 * @return
	 * @throws GenericException
	 */
	String generate(Object argument) throws JetException;
}
