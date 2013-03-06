package com.upmc.pstl2013.views;

import org.eclipse.core.resources.IFile;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.views.events.JobExecutor;


public class RunFactory {
	
	private static final RunFactory instance = new RunFactory();

	private RunFactory() {}

	/**
	 * Renvoie l'unique instance de la Factory.
	 * 
	 * @return {@link Factory}.
	 */
	public static RunFactory getInstance() {
		return instance;
	}
	
	/**
	 * Créé un {@link JobExecutor}
	 * @param name le nom de l'exécutor.
	 * @param swtView la vue de l'interface graphique.
	 * @param UMLFile le fichier UML a exécuter.
	 * @param property la propriété d'exécution.
	 * @param jobToWait le job qu'il faut attendre avant de s'exécuter ou null s'il n'y a pas besoin.
	 * @param counterExecution le numéro de l'exécution.
	 */
	public JobExecutor newJobExecutor(String name, SwtView swtView, IFile UMLFile, IProperties property, JobExecutor jobToWait, int counterExecution) {
		return new JobExecutor(name, swtView, UMLFile, property, jobToWait, counterExecution);
	}
}
