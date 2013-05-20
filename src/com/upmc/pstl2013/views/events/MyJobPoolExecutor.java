package com.upmc.pstl2013.views.events;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import org.apache.log4j.Logger;


public class MyJobPoolExecutor {

	private final int nbMaxWorker;
	private Queue<MyPriorityArrayList> jobs;
	private List<MyThreadWorker> workers;
	private final Logger log = Logger.getLogger(MyJobPoolExecutor.class);

	public MyJobPoolExecutor(int nbMaxWorker) {
		super();
		this.nbMaxWorker = nbMaxWorker;
		jobs = new PriorityQueue<MyPriorityArrayList>();
		workers = new ArrayList<MyThreadWorker>();
	}
	
	public boolean hasJob() {
		return (!jobs.isEmpty());
	}

	public JobExecutor getJob() {
		MyPriorityArrayList listePrioritaire = jobs.peek();
		if (listePrioritaire == null)
			return null;
		JobExecutor job = listePrioritaire.remove(0);
		// si la liste prioritaire est maintenant vide, on la supprime de la queue
		if (listePrioritaire.isEmpty())
			jobs.poll();
		return job;
	}

	/**
	 * Ajoute un job a exécuter par le pool.
	 * @param job le {@link JobExecutor}.
	 * @param priority un int qui représente la priorité du job. 0 est plus prioritaire que 1.
	 */
	public void addJob(JobExecutor job, int priority) {
		synchronized (this) {
			for(MyPriorityArrayList priorListe : jobs) {
				if(priority == priorListe.getPriority()) {
					priorListe.add(job);
					return;
				}
			}
			MyPriorityArrayList myPriorityArrayList = new MyPriorityArrayList(priority);
			myPriorityArrayList.add(job);
			jobs.add(myPriorityArrayList);
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
		jobs.clear();
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
