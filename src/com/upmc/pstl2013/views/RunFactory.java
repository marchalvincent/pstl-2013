package com.upmc.pstl2013.views;

import org.eclipse.uml2.uml.Activity;
import com.upmc.pstl2013.properties.IProperties;
import com.upmc.pstl2013.views.events.JobExecutor;
import com.upmc.pstl2013.views.events.MyJobPoolExecutor;


public class RunFactory {
	
	private static final RunFactory instance = new RunFactory();

	private RunFactory() {}

	/**
	 * Renvoie l'unique instance de la Factory.
	 * 
	 * @return {@link RunFactory}.
	 */
	public static RunFactory getInstance() {
		return instance;
	}
	
	/**
	 * Créé un {@link JobExecutor}
	 * @param name le nom de l'exécutor.
	 * @param swtView la vue de l'interface graphique.
	 * @param activity l'activité a exécuter.
	 * @param property la propriété d'exécution.
	 * @param job le job qu'il faut attendre avant de s'exécuter ou null s'il n'y a pas besoin.
	 * @param counterExecution le numéro de l'exécution.
	 * @param executed un booléen qui dit si on doit exécuter la propriété Alloy, ou false si on ne fait que la génération.
	 */
	public JobExecutor newJobExecutor(String name, SwtView swtView, Activity activity, IProperties property, JobExecutor job, int counterExecution, boolean executed) {
		return new JobExecutor(name, swtView, activity, property, job, counterExecution, executed);
	}
	
	/**
	 * Créé un {@link MyJobPoolExecutor}.
	 * @param nbMaxThread le nombre maximum de thread à lancer en même temps.
	 */
	public MyJobPoolExecutor newJobPoolExecutor(int nbMaxThread) {
		return new MyJobPoolExecutor(nbMaxThread);
	}

	/**
	 * Créé un {@link DataView}. Permet de faire des opérations sur les données de la vue.
	 * @param swtView la {@link SwtView} en question.
	 */
	public DataView newDataView(SwtView swtView) {
		return new DataView(swtView);
	}
}
