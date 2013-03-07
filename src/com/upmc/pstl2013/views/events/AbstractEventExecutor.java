package com.upmc.pstl2013.views.events;

import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;
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
		this.saveOption(swtView);
		
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
			showToView(e.getMessage());
		}
		
		// 4. On enregistre dans les préférences les propriétés
		this.saveProperties(properties);
	}

	/**
	 * Méthode qui récupère la liste de IProperties à exécuter sur le(s) process.
	 */
	protected abstract List<IProperties> getProperties() throws PropertiesException;

	/**
	 * Met à jour les préférences des propriétés.
	 * @param la liste des {@link IProperties}.
	 */
	private void saveProperties(List<IProperties> properties) {
		if (properties != null) {
			StringBuilder sb = new StringBuilder();
			for (IProperties prop : properties) {
				sb.append(prop.getClass().getSimpleName());
				sb.append("|");
			}
			try {
				ConfPropertiesManager.getInstance().setProperties(sb.toString());
			} catch (Exception e) {
				showToView(e.getMessage());
			}
		}
	}
	
	/**
	 * Met à jour les préférences des options.
	 * @param swtView
	 */
	private void saveOption(SwtView swtView) {
		
		// 1. On spécifie les préférence à la ConfPropertiesManager
		try {
			ConfPropertiesManager.getInstance().setTimeOut(String.valueOf(swtView.getTimeout()));
			ConfPropertiesManager.getInstance().setNbNodes(swtView.getNbNodesMax());
			ConfPropertiesManager.getInstance().setNbThreads(swtView.getNbThread());
		} catch (Exception e) {
			showToView(e.getMessage());
		}
		
		// 2. On enregistre dans le fichier les conf
		try {
			ConfPropertiesManager.getInstance().store();
		} catch (IOException e) {
			showToView(e.getMessage());
		}
	}

	private void showToView(String msg){
		log.error(msg);
		Display.getDefault().asyncExec(new RunnableUpdateExecutor(swtView, msg));
	}
}
