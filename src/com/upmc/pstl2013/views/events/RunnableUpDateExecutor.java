package com.upmc.pstl2013.views.events;

import org.eclipse.swt.widgets.Text;

public class RunnableUpdateExecutor implements Runnable {

	private Text txtLogs;
	private String message;
	
	public RunnableUpdateExecutor(Text control, String message) {
		this.txtLogs = control;
		this.message = message;
	}

	@Override
	public void run() {
		txtLogs.append(message);
	}

}
