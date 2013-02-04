package com.upmc.pstl2013.alloyGenerator.jet;

import com.upmc.pstl2013.GenericException;

/**
 * Cette exception est susceptible d'être lancée lors de la génération Jet du texte Alloy.
 * 
 */
public class JetException extends GenericException {

	private static final long serialVersionUID = 1L;

	public JetException(final String mess) {
		super(mess);
	}
}
