package com.upmc.pstl2013.views.events;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.PropertiesException;
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
			jobExec = Factory.getInstance().newJobExecutor("Execution", getProperties(), swtView);
			jobExec.setUser(true);
			jobExec.schedule();

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
