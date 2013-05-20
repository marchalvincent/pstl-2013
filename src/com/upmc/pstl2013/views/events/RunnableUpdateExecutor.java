package com.upmc.pstl2013.views.events;

import org.eclipse.swt.widgets.Text;

import com.upmc.pstl2013.views.SwtView;

/**
 * 
 *
 */
public class RunnableUpdateExecutor implements Runnable {

	private Text txtLogs;
	private String message;
	
	/**
	 * Runnable permettant de mettre à jours le plugin.
	 * Met a jours la partie exécution. 
	 * @param swtView
	 * @param message
	 */
	public RunnableUpdateExecutor(SwtView swtView, String message) {
		this.txtLogs = swtView.getTxtLogs();
		this.message = message;
	}

	@Override
	public void run() {
		txtLogs.append(message);
	}

}
