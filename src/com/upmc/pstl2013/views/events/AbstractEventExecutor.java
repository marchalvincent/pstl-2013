package com.upmc.pstl2013.views.events;

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
		IProperties TMPProperty = null;
		JobExecutor job = null;
		try {
			properties = this.getProperties();
			// On enregistre dans les préférences les propriétés
			swtView.getDataView().saveProperties(properties);
			
			// 5a. Pour chaque activité
			for (Activity activity : activitiesSelected) {
				// 5b. Pour chaque propriété
				if (properties != null) {
					
					// TODO vincent faire une méthode générique qui construit les job dynamiquement
					// on lance d'abord Wf
					
					// puis enoughState
					
					// et enfin les autres 
					
					// On lance d'abord le enoughState
					JobExecutor enoughState = null;
					for (IProperties property : properties) {
						if (property.getName().equals("EnoughState")) {
							TMPProperty = property.clone();
							TMPProperty.setEtatInitial(swtView.getInitState());
							String nomJob = "Execution Alloy de " + activity.getName() + " : " + TMPProperty.getName() + "...";
							enoughState = RunFactory.getInstance().newJobExecutor(nomJob, swtView, activity, TMPProperty, null, counterExecution, executed);
							jobPoolExecutor.addJob(enoughState, true);
							break;
						}
					}
					
					// Puis ensuite les autres
					for (IProperties property : properties) {
						if (!property.getName().equals("EnoughState")) {
							TMPProperty = property.clone();
							TMPProperty.setEtatInitial(swtView.getInitState());
							String nomJob = "Execution Alloy de " + activity.getName() + " : " + TMPProperty.getName() + "...";
							job = RunFactory.getInstance().newJobExecutor(nomJob, swtView, activity, TMPProperty, enoughState, counterExecution, executed);
							jobPoolExecutor.addJob(job, false);
						}
					}
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
	 * Méthode qui récupère la liste de IProperties à exécuter sur le(s) process.
	 */
	protected abstract List<IProperties> getProperties() throws PropertiesException;

}
