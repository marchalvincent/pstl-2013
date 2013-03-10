package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.apache.log4j.Logger;


public class MyJobPoolExecutor {
	
	private final int nbMaxWorker;
	private LinkedList<JobExecutor> jobs;
	private List<MyThreadWorker> workers;
	private final Logger log = Logger.getLogger(MyJobPoolExecutor.class);
	
	public MyJobPoolExecutor(int nbMaxWorker) {
		super();
		this.nbMaxWorker = nbMaxWorker;
		jobs = new LinkedList<JobExecutor>();
		workers = new ArrayList<MyThreadWorker>();
	}
	
	public LinkedList<JobExecutor> jobs() {
		return jobs;
	}
	
	/**
	 * Ajoute un job a exécuter par le pool.
	 * @param job le {@link JobExecutor}.
	 * @param isPriority un boolean qui spécifie si le job est prioritaire ou non.
	 */
	public void addJob(JobExecutor job, boolean isPriority) {
		synchronized (jobs) {
			if (isPriority) {
				jobs.addFirst(job);
			}
			else {
				jobs.addLast(job);
			}
		}
	}
	
	/**
	 * Démarre le pool de thread statique.
	 */
	public void startWorkers() {
		
		log.debug("Lancement du pool statique.");
		// on définit le nombre minimum de Thread a lancer
		int nbMaxThread = jobs.size();
		if (nbMaxWorker < nbMaxThread) {
			nbMaxThread = nbMaxWorker;
		}
		
		MyThreadWorker t;
		for (int i = 0; i < nbMaxThread; i++) {
			t = new MyThreadWorker(this, i+1);
			workers.add(t);
			t.start();
		}
	}
	
	/**
	 * Arrête brutallement tous les workers encore en cours d'exécution.
	 */
	@SuppressWarnings("deprecation")
	public String killWorkers() {
		jobs.clear();
		StringBuilder sb = new StringBuilder();
		for (MyThreadWorker t : workers) {
			if (t.isAlive()) {
				sb.append("The worker n°" + t.getNum() + " was stopped !\n");
				t.stop();
			}
		}
		return sb.toString();
	}
}
