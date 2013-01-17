package com.upmc.pstl2013.views;

import org.eclipse.swt.widgets.Composite;

public class SWTTestVincent extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public SWTTestVincent(Composite parent, int style) {
		super(parent, style);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
