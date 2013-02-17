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
import com.upmc.pstl2013.properties.impl.EnoughState;
import com.upmc.pstl2013.util.ConfPropertiesManager;
import com.upmc.pstl2013.views.SwtView;

public class JobExecutor extends Job {

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

		StringBuilder nomFichier = new StringBuilder(UMLFile.getName());
		nomFichier.append(" : propriété ");
		nomFichier.append(property.getClass().getSimpleName());
		
		StringBuilder sbInfo = new StringBuilder();
		sbInfo.append("Génération et exécution du fichier ");
		sbInfo.append(nomFichier);
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

			// Puis on spécifie la nouvelle variable nbState du job qui vient de finir (EnoughState normalement).
			if (jobToWait.getNbState().equals("")) {
				// si le enoughState n'a rien trouvé ou que l'enoughState a dépassé la limite...
				log.warn("Abort du fichier " + nomFichier + ".\n");
				showToView("Abort du fichier " + nomFichier + ".\n");
				return Status.CANCEL_STATUS;
			} else if (Integer.parseInt(jobToWait.getNbState()) >= EnoughState.MAX_STATES) {
				log.warn("Abort du fichier " + nomFichier + ". Dépassement du nombre maximum de state pour la recherche Enough.\n");
				showToView("Abort du fichier " + nomFichier + ". Dépassement du nombre maximum de state pour la recherche Enough.\n");
				return Status.CANCEL_STATUS;
			} else {
				property.put("nbState", jobToWait.getNbState());
			}
		}
		
		StringBuilder result = new StringBuilder();

		// 1. On créé l'objet exécutor
		IAlloyExecutor alloyExecutor = Factory.getInstance().newAlloyExecutor(UMLFile, dirDestination, property, counterExecution);
		
		try {
			// On lance l'exécution
			IFileResult iFileResult = alloyExecutor.executeFiles();
			if (iFileResult.getListActivityResult() == null || iFileResult.getListActivityResult().size() == 0) {
				// dans le cas où aucune activité n'a été trouvée, on ne fait rien, ni affichage graphique, ni récupération du nombre de state
				result.append("Aucune activité n'a été trouvée dans la limite spécifiée dans les options pour le fichier ");
				result.append(nomFichier);
				result.append(" (");
				result.append(ConfPropertiesManager.getInstance().getNbNodes());
				result.append(" noeuds max.)\n");
			}
			else {
				// On récupère le nombre de state (utile quand on exécute EnoughState)
				// pour l'instant, on ne traite qu'une activité à la fois
				// TODO modifier ici si on traite plusieurs activité à la fois...
				nbState = iFileResult.getListActivityResult().get(0).getNbState();
				
				// Puis on affiche les résultats sur l'interface graphique
				showToDetails(iFileResult);
			}
			
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

}
