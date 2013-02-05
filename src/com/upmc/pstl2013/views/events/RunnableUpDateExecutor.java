package com.upmc.pstl2013.views.events;

import java.io.IOException;

import org.eclipse.swt.widgets.Text;

import com.upmc.pstl2013.views.LogCreator;
import com.upmc.pstl2013.views.SwtView;

public class RunnableUpdateExecutor implements Runnable {

	private Text txtLogs,txtDirectory;
	private String message;
	
	public RunnableUpdateExecutor(SwtView swtView, String message) {
		this.txtLogs = swtView.getTxtLogs();
		this.txtDirectory = swtView.getTxtDirectory();
		this.message = message;
	}

	@Override
	public void run() {
		txtLogs.append(message);
		try {
			LogCreator.createLog(txtDirectory.getText());
		} catch (IOException e) {
			txtLogs.append(e.getMessage());
		}
	}

}
