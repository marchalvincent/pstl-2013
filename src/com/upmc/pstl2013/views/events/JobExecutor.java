package com.upmc.pstl2013.views.events;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyExecutor.IFileResult;
import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.views.SwtView;

public class JobExecutor extends Job {

	private static Logger log = Logger.getLogger(JobExecutor.class);
	private SwtView swtView;
	private IFile UMLFile;
	private IProperties property;
	private String dirDestination;
	

	public JobExecutor(String name, SwtView swtView, IFile UMLFile, IProperties property) {
		super(name);
		this.swtView = swtView;
		this.UMLFile = UMLFile;
		this.property = property;
		this.dirDestination = swtView.getUserDir();
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		log.info("Génération et exécution des fichiers Alloy.");
		StringBuilder result = new StringBuilder();

		// 1. On créé l'objet exécutor
		IAlloyExecutor alloyExecutor = Factory.getInstance().newAlloyExecutor(UMLFile, dirDestination, property);
		
		try {
			@SuppressWarnings("unused")
			IFileResult iFileResult = alloyExecutor.executeFiles();

			// TODO michou traitement avec le IFileResult
			result.append("Fin d'exécution des fichiers Alloy.");
			log.info(result.toString());
			showToView(result.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
			showToView(e.getMessage());
		}
		
		return Status.OK_STATUS;
	}

	private void showToView(String msg){
		Display.getDefault().asyncExec(new RunnableUpdateExecutor(swtView, msg));
	}

}
