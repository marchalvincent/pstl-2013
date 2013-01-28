package com.upmc.pstl2013.views.events;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Text;

public class EventReadLogs extends MouseAdapter
{
	private Logger log;
	private Text textDirectory;
	
	public EventReadLogs (Text textDirectory, Logger log)
	{
		this.textDirectory = textDirectory;
		this.log = log;
	}
	
	@Override
	public void mouseDown(MouseEvent e) {
		try {
			Desktop.getDesktop().open(new File(textDirectory.getText()+"log.html"));
		} catch (IOException e1) {
			log.error(e1.toString());
		}
	}
}