package com.upmc.pstl2013.views.events;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;
import com.upmc.pstl2013.alloyExecutor.ExecutorFactory;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyExecutor.IFileResult;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.views.SwtView;

public class JobExecutor extends Job implements Runnable{

	private Logger log = Logger.getLogger(JobExecutor.class);
	private SwtView swtView;
	private IFile UMLFile;
	private IProperties property;
	private String dirDestination;
	private String nbState;
	private JobExecutor jobToWait;
	private int counterExecution;
	
	public JobExecutor(String name, SwtView swtView, IFile UMLFile, IProperties property, JobExecutor jobToWait, int counterExecution) {
		super(name);
		this.swtView = swtView;
		this.UMLFile = UMLFile;
		this.property = property;
		this.dirDestination = swtView.getUserDir();
		this.nbState = "";
		this.jobToWait = jobToWait;
		this.counterExecution = counterExecution;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		StringBuilder sbInfo = new StringBuilder();
		sbInfo.append("Génération et exécution du fichier ");
		sbInfo.append(UMLFile.getName());
		sbInfo.append(" : propriété ");
		sbInfo.append(property.getClass().getSimpleName());
		sbInfo.append(".\n");
		log.info(sbInfo.toString());
		showToView(sbInfo.toString());
		
		// Si on a un job à attendre, on se "join" sur lui
		if (jobToWait != null) {
			try {
				jobToWait.join();
			} catch (InterruptedException e) {
				log.error("Impossible d'attendre le thread, il a été interrompu...");
			}

			// Puis on spécifie la nouvelle variable nbState du job qui vient de finir (EnoughState).
			property.put("nbState", jobToWait.getNbState());
		}
		
		StringBuilder result = new StringBuilder();

		// 1. On créé l'objet exécutor
		IAlloyExecutor alloyExecutor = ExecutorFactory.getInstance().newAlloyExecutor(UMLFile, dirDestination, property, counterExecution);
		
		try {
			// On lance l'exécution
			IFileResult iFileResult = alloyExecutor.executeFiles();
			
			// On récupère le nombre de state (utile quand on exécute EnoughState)
			nbState = iFileResult.getActivityResult().getNbState();
			
			// Puis on affiche les résultats sur l'interface graphique
			showToDetails(iFileResult);
			
			result.append("Fin d'exécution du fichier ");
			result.append(UMLFile.getName());
			result.append(" : propriété ");
			result.append(property.getClass().getSimpleName());
			result.append(".\n");
			
			log.info(result.toString());
			showToView(result.toString());
		} catch (Exception e) {
			log.error(e.getMessage());
			showToView(e.getMessage());
			return Status.CANCEL_STATUS;
		}

		return Status.OK_STATUS;
	}

	private void showToView(String msg){
		Display.getDefault().asyncExec(new RunnableUpdateExecutor(swtView, msg));
	}
	
	private void showToDetails(IFileResult iFileResult){
		Display.getDefault().asyncExec(new RunnableUpdateDetails(swtView, iFileResult));
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void canceling() {
		this.getThread().interrupt();
		this.getThread().stop();
		log.info("Interruption de : " + this.getName());
	}
	
	public String getNbState() {
		return nbState;
	}

	@Override
	public void run() {
		this.schedule();
	}
}
