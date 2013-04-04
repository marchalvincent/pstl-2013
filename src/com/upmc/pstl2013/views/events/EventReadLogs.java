package com.upmc.pstl2013.views.events;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import com.upmc.pstl2013.views.SwtView;

public class EventReadLogs extends MouseAdapter {

	private Logger log = Logger.getLogger(EventReadLogs.class);
	private String nameLog;

	/**
	 * Évènement permettant d'afficher dans le navigateur les logs.
	 * @param {@link SwtView}
	 */
	public EventReadLogs(String nameLog) {

		this.nameLog = nameLog;
	}

	@Override
	public void mouseDown(MouseEvent e) {

		try {
			Desktop.getDesktop().open(new File("AlloyAnalyzer" + File.separator + nameLog));
		} catch (IOException e1) {
			log.error(e1.toString());
		}
	}
}
