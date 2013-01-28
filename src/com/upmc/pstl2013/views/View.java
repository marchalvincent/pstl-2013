package com.upmc.pstl2013.views;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

public class View extends ViewPart {

	private Composite composite;
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "com.upmc.pstl2013.views.View";

	/**
	 * The constructor.
	 */
	public View() {

	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize it.
	 */
	public void createPartControl(Composite parent) {

		composite = new SwtView(parent, 0);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {

		composite.setFocus();
	}
}