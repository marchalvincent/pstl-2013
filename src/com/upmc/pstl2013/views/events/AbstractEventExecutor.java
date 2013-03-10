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

	/**
	 * Constructor
	 * @param {@link SwtView} 
	 */
	public AbstractEventExecutor(SwtView swtView) {
		super();
		this.swtView = swtView;
		counterExecution = 0;
	}

	@Override
	public void mouseDown(MouseEvent evt) {
		counterExecution++;
		
		// 1. On enregistre dans les préférences les options
		this.swtView.saveOption(swtView);
		
		// 2. On créé notre pool de job
		int nbThreads = ConfPropertiesManager.getInstance().getNbThreads();
		MyJobPoolExecutor jobPoolExecutor = EventFactory.getInstance().newJobPoolExecutor(nbThreads);

		// 3. On récupère tous les process
		List<Activity> activitiesSelected = swtView.getActivitiesSelected();

		// 4. On récupère toutes les propriétés seléctionnées
		List<IProperties> properties = null;
		IProperties TMPProperty = null;
		JobExecutor job = null;
		try {
			properties = this.getProperties();
			// 5a. Pour chaque activité
			for (Activity activity : activitiesSelected) {
				// 5b. Pour chaque propriété
				if (properties != null) {
					
					// On lance d'abord le enoughState
					JobExecutor enoughState = null;
					for (IProperties property : properties) {
						if (property.getClass().getSimpleName().equals("EnoughState")) {
							TMPProperty = property.clone();
							String nomJob = "Execution Alloy de " + activity.getName() + " : " + TMPProperty.getClass().getSimpleName() + "...";
							enoughState = RunFactory.getInstance().newJobExecutor(nomJob, swtView, activity, TMPProperty, null, counterExecution);
							jobPoolExecutor.addJob(enoughState, true);
							break;
						}
					}
					
					// Puis ensuite les autres
					for (IProperties property : properties) {
						if (!property.getClass().getSimpleName().equals("EnoughState")) {
							TMPProperty = property.clone();
							String nomJob = "Execution Alloy de " + activity.getName() + " : " + TMPProperty.getClass().getSimpleName() + "...";
							job = RunFactory.getInstance().newJobExecutor(nomJob, swtView, activity, TMPProperty, enoughState, counterExecution);
							jobPoolExecutor.addJob(job, false);
						}
					}
				}
			}
			jobPoolExecutor.startWorkers();
			
			JobTimeout threadTimeout = new JobTimeout(jobPoolExecutor, swtView.getTimeout(), swtView);
			threadTimeout.schedule();
		} catch (PropertiesException e) {
			swtView.showToView(e.getMessage());
			log.error(e.getMessage());
		}
		
		// 4. On enregistre dans les préférences les propriétés
		swtView.saveProperties(properties);
	}

	/**
	 * Méthode qui récupère la liste de IProperties à exécuter sur le(s) process.
	 */
	protected abstract List<IProperties> getProperties() throws PropertiesException;

}
