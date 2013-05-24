package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;


public class MyJobPoolExecutor {

	private final int nbMaxWorker;
	private Map<Integer, List<JobExecutor>> jobsPrio;
	private List<MyThreadWorker> workers;
	private final Logger log = Logger.getLogger(MyJobPoolExecutor.class);

	public MyJobPoolExecutor(int nbMaxWorker) {
		super();
		this.nbMaxWorker = nbMaxWorker;
		jobsPrio = new HashMap<Integer, List<JobExecutor>>();
		workers = new ArrayList<MyThreadWorker>();
	}
	
	public boolean hasJob() {
		return (!jobsPrio.isEmpty());
	}

	public JobExecutor getJob() {
		
		if (jobsPrio.isEmpty())
			return null;
		
		// on récupère la plus petite clé
		Set<Integer> entiers = jobsPrio.keySet();
		int priorite = Integer.MAX_VALUE;
		for (Integer integer : entiers) {
			if (integer < priorite)
				priorite = integer;
		}
		
		// puis le job
		Integer prio = new Integer(priorite);
		JobExecutor job = jobsPrio.get(prio).remove(0);
		
		// si la liste de cette priorité est vide, alors on supprime la liste
		if (jobsPrio.get(prio).isEmpty())
			jobsPrio.remove(prio);
		
		return job;
	}

	/**
	 * Ajoute un job a exécuter par le pool.
	 * @param job le {@link JobExecutor}.
	 * @param priority un int qui représente la priorité du job. 0 est plus prioritaire que 1.
	 */
	public void addJob(JobExecutor job, int priority) {
		Integer prio = new Integer(priority);
		synchronized (this) {
			// on vérifie s'il y a déjà une liste de créée pour cette priorité
			if (jobsPrio.get(prio) == null)
				jobsPrio.put(prio, new ArrayList<JobExecutor>());
			// puis on ajoute le job dans la liste correspondant a cette priorité
			jobsPrio.get(prio).add(job);
		}
	}

	/**
	 * Démarre le pool de thread statique.
	 */
	public void startWorkers() {

		log.debug("Lancement du pool statique.");
		// on définit le nombre minimum de Thread a lancer
		int nbMaxThread = jobsPrio.size();
		if (nbMaxWorker < nbMaxThread) {
			nbMaxThread = nbMaxWorker;
		}

		MyThreadWorker t;
		for (int i = 0; i < nbMaxThread; i++) {
			t = new MyThreadWorker(this, i+1);
			synchronized (workers) {
				workers.add(t);
			}
			t.start();
		}
	}

	/**
	 * Arrête tous les workers encore en cours d'exécution.
	 */
	@SuppressWarnings("deprecation")
	public String killWorkers() {
		jobsPrio.clear();
		StringBuilder sb = new StringBuilder();
		synchronized (workers) {
			for (MyThreadWorker t : workers) {
				if (t.isAlive()) {
					sb.append("The worker n°" + t.getNum() + " was stopped !\n");
					t.killJob();
					t.stop();
				}
			}
		}
		return sb.toString();
	}

	public void removeWorker(MyThreadWorker myThreadWorker) {
		synchronized (workers) {
			workers.remove(myThreadWorker);
		}
	}
	
	public boolean isFinish() {
		boolean bool = false;
		synchronized (workers) {
			bool = workers.isEmpty();
		}
		return bool;
	}
}
