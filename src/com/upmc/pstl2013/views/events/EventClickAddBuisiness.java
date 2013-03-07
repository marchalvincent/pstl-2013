package com.upmc.pstl2013.views.events;

import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Shell;

import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.viewsDialog.DialogBuisiness;

public class EventClickAddBuisiness extends MouseAdapter {

	private SwtView swtView;
	//private Logger log = Logger.getLogger(EventClickAddBuisiness.class);

	/**
	 * Constructor
	 * @param {{@link SwtView}
	 */
	public EventClickAddBuisiness(SwtView swtView) {
		super();
		this.swtView = swtView;
	}

	@Override
	public void mouseDown(MouseEvent e) {

		DialogBuisiness window = new DialogBuisiness(new Shell(),swtView);
		window.setBlockOnOpen(true);
		window.open();
	}
}