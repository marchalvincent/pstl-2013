package com.upmc.pstl2013.viewsDialog;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import com.upmc.pstl2013.views.SwtView;

public class EventClickSubmit extends MouseAdapter {

	private SwtView swtView;
	private Logger log = Logger.getLogger(EventClickSubmit.class);

	/**
	 * Constructor
	 * @param {{@link SwtView}
	 */
	public EventClickSubmit(SwtView swtView) {
		super();
		this.swtView = swtView;
	}

	@Override
	public void mouseDown(MouseEvent e) {

		//TODO : Michel
	}


}
