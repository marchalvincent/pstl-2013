package com.upmc.pstl2013.views.events;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.uml2.uml.Activity;
import com.upmc.pstl2013.alloyExecutor.ExecutorFactory;
import com.upmc.pstl2013.alloyExecutor.IAlloyExecutor;
import com.upmc.pstl2013.alloyExecutor.IFileResult;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.views.SwtView;
import edu.mit.csail.sdg.alloy4.Err;

public class JobExecutor extends Job {

	private Logger log = Logger.getLogger(JobExecutor.class);
	private SwtView swtView;
	private Activity activity;
	private IProperties property;
	private String dirDestination;
	private String nbState;
	private JobExecutor jobToWait;
	private int counterExecution;
	private boolean executed;

	/**
	 * 
	 * @param name, nom du job
	 * @param swtView
	 * @param activity
	 * @param property
	 * @param jobToWait
	 * @param counterExecution
	 */
	public JobExecutor(String name, SwtView swtView, Activity activity, IProperties property, JobExecutor jobToWait, int counterExecution, boolean executed) {
		super(name);
		this.swtView = swtView;
		this.activity = activity;
		this.property = property;
		this.jobToWait = jobToWait;
		this.counterExecution = counterExecution;
		this.dirDestination = swtView.getUserDir();
		this.nbState = null;
		this.executed = executed;
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		StringBuilder sbInfo = new StringBuilder();
		sbInfo.append("Generation");
		if (executed)
			sbInfo.append(" and execution");
		sbInfo.append(" of ");
		sbInfo.append(activity.getName());
		sbInfo.append(" : property ");
		sbInfo.append(property.getName());
		sbInfo.append(".\n");
		log.info(sbInfo.toString());
		swtView.getDataView().showToViewUse(sbInfo.toString());

		// Si on a un job à attendre, on récupère son nombre de state
		if (jobToWait != null) {
			// la méthode est bloquante tant que le jobToWait n'a pas fini
			property.put("nbState", jobToWait.getNbState());
		}

		StringBuilder result = new StringBuilder();

		// 1. On créé l'objet exécutor
		IAlloyExecutor alloyExecutor = ExecutorFactory.getInstance().newAlloyExecutor(activity, dirDestination, property, counterExecution);

		try {
			// On lance l'exécution
			IFileResult iFileResult = alloyExecutor.executeFiles(executed);

			synchronized (this) {
				// On récupère le nombre de state (utile quand on exécute EnoughState)
				nbState = iFileResult.getActivityResult().getNbState();
				
				//Une fois qu'on a le nbState, on notifie tout ceux qui se sont endormi sur nous.
				this.notifyAll();
			}

			// Puis on affiche les résultats sur l'interface graphique
			if (executed)
				swtView.getDataView().showToViewDetails(iFileResult);

			result.append("End of ");
			result.append(activity.getName());
			result.append(" : property ");
			result.append(property.getName());
			result.append(".\n");

			log.info(result.toString());
			swtView.getDataView().showToViewUse(result.toString());
		} catch (Exception e) {
			final StringBuilder sb = new StringBuilder(activity.getName());
			sb.append(" : property ");
			sb.append(property.getName());
			sb.append(" -> ");
			if (e instanceof Err && e.getMessage().contains("ThreadDeath"))
				sb.append("The execution of the job is interrupted.");
			else
				sb.append(e.getMessage());
			sb.append("\n");
			log.error(sb.toString());
			swtView.getDataView().showToViewUse(sb.toString());
			return Status.CANCEL_STATUS;
		}

		return Status.OK_STATUS;
	}

	/**
	 * Méthode qui renvoie le nombre de state qu'a pris le job pour satisfaire 
	 * la vérification du process. 
	 * La méthode est bloquante tant que le job n'a pas fini son exécution.
	 */
	public String getNbState() {
		// si le nbState n'est pas encore initialisé, on wait
		synchronized (this) {
			if (nbState == null) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					log.warn("Job interrompu pendant l'attente du nbState.");
				}
			}
		}
		return nbState;
	}
}
