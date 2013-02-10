package com.upmc.pstl2013;

/**
 * Une classe générique mère à toutes nos exceptions.
 *
 */
public class GenericException extends Exception {

	private static final long serialVersionUID = 1L;

	public GenericException(final String mess) {
		super(mess);
	}
}
