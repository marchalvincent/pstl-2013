package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.uml2.uml.Activity;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.util.ConfPropertiesManager;
import com.upmc.pstl2013.util.JobTimeout;
import com.upmc.pstl2013.views.RunFactory;
import com.upmc.pstl2013.views.SwtView;

public abstract class AbstractEventExecutor extends MouseAdapter {

	private Logger log = Logger.getLogger(AbstractEventExecutor.class);
	private SwtView swtView;
	private static int counterExecution;
	private boolean executed;

	/**
	 * Constructor
	 * @param {@link SwtView} 
	 */
	public AbstractEventExecutor(SwtView swtView, boolean executed) {
		super();
		this.swtView = swtView;
		counterExecution = 0;
		this.executed = executed;
	}

	@Override
	public void mouseDown(MouseEvent evt) {
		counterExecution++;
		
		// 1. On enregistre dans les préférences les options
		swtView.getDataView().saveOption(swtView);
		
		// 2. On créé notre pool de job
		int nbThreads = ConfPropertiesManager.getInstance().getNbThreads();
		MyJobPoolExecutor jobPoolExecutor = RunFactory.getInstance().newJobPoolExecutor(nbThreads);

		// 3. On récupère tous les process
		List<Activity> activitiesSelected = swtView.getActivitiesSelected();
		
		// 4. On récupère toutes les propriétés seléctionnées
		List<IProperties> properties = null;
		JobExecutor wf = null, enoughState = null;
		StringBuilder nomJob = new StringBuilder();
		try {
			properties = this.getProperties();
			
			List<String> propertiesNames = new ArrayList<String>();
			for (IProperties iProperties : properties) {
				propertiesNames.add(iProperties.getName());
			}
			
			// On enregistre dans les préférences les propriétés
			swtView.getDataView().saveProperties(properties);
			
			// 5a. Pour chaque activité
			for (Activity activity : activitiesSelected) {
				// 5b. Pour chaque propriété
				if (properties != null) {
					
					nomJob.delete(0, nomJob.length());
					nomJob.append("Execution Alloy de ");
					nomJob.append(activity.getName());
					nomJob.append(" : ");
					
					// Le 2e paramètre de makeJob représente la priorité du job dans la file d'éxecution
					// on lance d'abord Wf
					wf = this.makeJob("Wf", 0, properties, nomJob, activity, jobPoolExecutor, null);
					propertiesNames.remove("Wf");
					
					// puis enoughState
					enoughState = this.makeJob("EnoughState", 1, properties, nomJob, activity, jobPoolExecutor, wf);
					propertiesNames.remove("EnoughState");
					
					// et enfin les autres 
					this.makeJob(propertiesNames, 2, properties, nomJob, activity, jobPoolExecutor, enoughState);
					
				}
			}
			jobPoolExecutor.startWorkers();
			
			JobTimeout threadTimeout = new JobTimeout(jobPoolExecutor, swtView.getTimeout(), swtView);
			threadTimeout.schedule();
		} catch (PropertiesException e) {
			log.error(e.getMessage());
			swtView.getDataView().showToViewUse(e.getMessage());
		}
	}
	
	/**
	 * Créé un job pour chaque nom de propriété qui est en premier paramètre dans la liste
	 * @param propertiesNames la {@link List} des nom des propriétés qui doivent être exécutée
	 * @param priority un entier qui représente les priorités
	 * @param properties la liste des {@link IProperties} qui sont toutes générées
	 * @param nomJob le nom du job
	 * @param activity l'{@link Activity} qui est exécutée
	 * @param jobPoolExecutor le {@link MyJobPoolExecutor} qui gère les workers
	 * @param dependance le {@link JobExecutor} dont dépend l'exécution de ce job
	 */
	private void makeJob(List<String> propertiesNames, int priority, List<IProperties> properties,
			StringBuilder nomJob, Activity activity, MyJobPoolExecutor jobPoolExecutor,
			JobExecutor dependance) {
		for (String propertyName : propertiesNames) {
			this.makeJob(propertyName, priority, properties, nomJob, activity, jobPoolExecutor, dependance);
		}
	}

	/**
	 * Créé un job pour la propriété donnée en paramètre.
	 * @param propertyName le nom de la propriété qui doit être passée en paramètre
	 * @param priority un entier qui représente les priorités
	 * @param properties la liste des {@link IProperties} qui sont toutes générées
	 * @param nomJob le nom du job
	 * @param activity l'{@link Activity} qui est exécutée
	 * @param jobPoolExecutor le {@link MyJobPoolExecutor} qui gère les workers
	 * @param dependance le {@link JobExecutor} dont dépend l'exécution de ce job
	 * @return
	 */
	private JobExecutor makeJob(String propertyName, int priority, List<IProperties> properties, StringBuilder nomJob,
			Activity activity, MyJobPoolExecutor jobPoolExecutor, JobExecutor dependance) {

		IProperties TMPProperty = null;
		JobExecutor job = null;
		String name = null;
		for (IProperties property : properties) {
			if (property.getName().equals(propertyName)) {
				TMPProperty = property.clone();
				TMPProperty.setEtatInitial(swtView.getInitState());
				name = nomJob.toString() + propertyName + "...";
				job = RunFactory.getInstance().newJobExecutor(name, swtView, activity, TMPProperty, dependance, counterExecution, executed);
				jobPoolExecutor.addJob(job, priority);
				break;
			}
		}
		return job;
	}

	/**
	 * Méthode qui récupère la liste de IProperties à exécuter sur le(s) process.
	 */
	protected abstract List<IProperties> getProperties() throws PropertiesException;

}
