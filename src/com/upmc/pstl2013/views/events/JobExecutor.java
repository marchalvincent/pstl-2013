package com.upmc.pstl2013.views.events;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.views.SwtView;

public class JobExecutor extends Job {

	private List<IProperties> properties;
	private SwtView swtView;
	private static Logger log = Logger.getLogger(AbstractEventExecutor.class);

	public JobExecutor(String name, List<IProperties> properties, SwtView swtView) {
		super(name);
		this.properties = properties;
		this.swtView = swtView;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		swtView.getInfoGenerator().setProperties(properties);
		log.info("Génération et exécution des fichiers Alloy.");
		StringBuilder result = new StringBuilder();

		try {
			result.append(swtView.getAlloyExecutor().executeFiles());
			result.append("Fin d'exécution des fichiers Alloy.");
			log.info(result.toString());
			showToView(result.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
			showToView(e.getMessage());
		}
		swtView.getAlloyExecutor().reset();
		
		return Status.OK_STATUS;
	}

	private void showToView(String msg){
		
		Display.getDefault().asyncExec(new RunnableUpdateExecutor(swtView, msg));
	}

}
