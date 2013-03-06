package com.upmc.pstl2013.views.events;

import java.io.File;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.viewsDialog.DialogBuisiness;

public class EventClickAddBuisiness extends MouseAdapter {

	//TODO : Michel Finir !!!!
	private String separator = File.separator;
	private Text txtChooseDirectory;
	private Text txtLogs;
	private SwtView swtView;
	private Logger log = Logger.getLogger(EventClickAddBuisiness.class);

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

		DialogBuisiness window = new DialogBuisiness(new Shell());
		window.setBlockOnOpen(true);
		window.open();
	}
}