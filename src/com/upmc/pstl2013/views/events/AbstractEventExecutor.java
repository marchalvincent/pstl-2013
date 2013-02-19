package com.upmc.pstl2013.views.events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFile;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Display;

import com.upmc.pstl2013.factory.Factory;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.properties.impl.EnoughState;
import com.upmc.pstl2013.properties.impl.PropertiesException;
import com.upmc.pstl2013.util.ConfPropertiesManager;
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
		List<JobExecutor> listJobsExec = new ArrayList<JobExecutor>();

		// 1. On récupère tous les fichiers UML
		List<IFile> UMLFileSelected = swtView.getUMLFilesSelected();

		// 2. On récupère toutes les propriétés seléctionnées
		List<IProperties> properties = null;
		try {
			properties = this.getProperties();
			// 3a. Pour chaque fichier
			for (IFile iFile : UMLFileSelected) {
				// 3b. Pour chaque propriété
				if (properties != null) {

					// Dans un premier temps, on exécute la propriété EnoughState pour avoir le nombre de state 
					// à utiliser avec les autres propriétés
					JobExecutor jobEnough = this.execute(listJobsExec, iFile, properties, true, null);
					
					
					// Puis ensuite on lance l'exécution pour les autres propriétés avec la référence du premier job
					this.execute(listJobsExec, iFile, properties, false, jobEnough);
				}
			}
			ThreadTimeout threadTimeout = new ThreadTimeout(listJobsExec, swtView.getTimeout());
			threadTimeout.start();
		} catch (PropertiesException e) {
			showToView(e.getMessage());
		}
		// 4. On enregistre dans les préférences les propriétés
		this.saveProperties(properties);
		// 5. On enregistre dans les préférences les options
		this.saveOption(swtView);
		
	}


	/**
	 * Exécute un fichier als pour une propriété donnée.
	 * @param listJobsExec La liste des {@link JobExecutor} en cours d'exécution.
	 * @param iFile Le fichier als à exécuter.
	 * @param properties La {@link IProperties} d'exécution.
	 * @param isEnoughState un booléen qui spécifie si on veut lancer la propriété {@link EnoughState} ou une autre.
	 * @param jobToWait JobExecutor le job qui exécute (a exécuté) la propriété EnoughState ou null si c'est celui ci qui doit le faire.
	 * @return JobExecutor Le {@link JobExecutor} qui vient d'être lancé.
	 */
	private JobExecutor execute(List<JobExecutor> listJobsExec, IFile iFile, List<IProperties> properties, boolean isEnoughState, JobExecutor jobToWait) {

		IProperties TMPProperty = null;
		JobExecutor jobExec = null;
		for (IProperties property : properties) {
			if ((isEnoughState && property.getClass().getSimpleName().equals("EnoughState")) 
					|| (!isEnoughState && !property.getClass().getSimpleName().equals("EnoughState"))) {

				// On créé une copie de la propriété pour des raisons de concurrence (une propriété est sujette à modification pendant l'exécution)
				TMPProperty = property.clone();

				// On lance le job
				String nomJob = "Execution Alloy de " + iFile.getName() + " : " + TMPProperty.getClass().getSimpleName() + "...";
				jobExec = Factory.getInstance().newJobExecutor(nomJob, swtView, iFile, TMPProperty, jobToWait, counterExecution);
				jobExec.setUser(true);
				
				swtView.getThreadPoolExecutor().execute(jobExec);
				//jobExec.schedule();
				
				listJobsExec.add(jobExec);
			}
		}

		return jobExec;
	}

	/**
	 * Méthode qui récupère la liste de IProperties à exécuter sur le(s) fichier(s) UML.
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
			ConfPropertiesManager.getInstance().setNbNodes(String.valueOf(swtView.getNbNodesEnough()));
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
