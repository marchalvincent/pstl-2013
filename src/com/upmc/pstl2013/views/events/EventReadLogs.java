package com.upmc.pstl2013.views.events;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;
import com.upmc.pstl2013.views.SwtView;

public class EventReadLogs extends MouseAdapter {

	private Logger log = Logger.getLogger(EventReadLogs.class);
	private Text textDirectory;
	private String nameLog;

	/**
	 * Constructor
	 * @param {{@link SwtView}
	 */
	public EventReadLogs(SwtView swtView,String nameLog) {

		this.textDirectory = swtView.getTxtDirectory();
		this.nameLog = nameLog;
	}

	@Override
	public void mouseDown(MouseEvent e) {

		try {
			Desktop.getDesktop().open(new File(textDirectory.getText() + nameLog));
		} catch (IOException e1) {
			log.error(e1.toString());
		}
	}
}
