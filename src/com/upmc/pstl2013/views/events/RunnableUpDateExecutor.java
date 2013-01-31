package com.upmc.pstl2013.views.events;

import org.eclipse.swt.widgets.Text;

public class RunnableUpDateExecutor implements Runnable {

	private Text control;
	private String message;
	
	public RunnableUpDateExecutor(Text control, String message) {
		this.control = control;
		this.message = message;
	}

	@Override
	public void run() {
		control.setText(message);
	}

}
