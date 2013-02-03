package com.upmc.pstl2013.views.events;

import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.core.runtime.Status;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.views.LogCreator;
import com.upmc.pstl2013.views.SwtView;

public abstract class AbstractEventExecutor extends MouseAdapter {

	private Logger log = Logger.getLogger(AbstractEventExecutor.class);
	private SwtView swtView;

	/**
	 * Constructor
	 * @param {@link SwtView} 
	 */
	public AbstractEventExecutor(SwtView swtView) {
		this.swtView = swtView;
	}

	@Override
	public void mouseDown(MouseEvent evt) {

		JobExecutor jobExec;
		try {
			jobExec = new JobExecutor("Execution", getProperties(), swtView);
			jobExec.setUser(true);
			jobExec.schedule();
			try {
				jobExec.join();
				if (jobExec.getResult() != null && jobExec.getResult() == Status.OK_STATUS)
				{
					// Création du fichier de log
					try {
						LogCreator.createLog(swtView.getTxtDirectory().getText());
					} catch (IOException e) {
						swtView.getTxtLogs().setText(e.getMessage());
					}
				}
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				swtView.getTxtLogs().append(e.getMessage());
			}
		} catch (PropertiesException e1) {
			log.error(e1.getMessage());
			swtView.getTxtLogs().append(e1.getMessage());
		}
	}

	/**
	 * Récupère toutes les attributs des propriétés cochés.
	 */
	protected abstract List<IProperties> getProperties() throws PropertiesException;
}
