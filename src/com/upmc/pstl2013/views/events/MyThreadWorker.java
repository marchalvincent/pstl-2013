package com.upmc.pstl2013.views.events;

import org.apache.log4j.Logger;


public class MyThreadWorker extends Thread {

	private final Logger log = Logger.getLogger(MyThreadWorker.class);
	private final int numWorker;
	private JobExecutor job;
	private MyJobPoolExecutor pool;

	public MyThreadWorker(MyJobPoolExecutor pool, int numWorker) {
		super();
		this.numWorker = numWorker;
		this.pool = pool;
	}

	public int getNum() {
		return numWorker;
	}

	@Override
	public void run() {
		log.debug("Lancement du worker n°" + numWorker);
		while (true) {
			// on se synchronize sur la liste et on récupère le 1er élément.
			synchronized (pool) {

				if (pool.hasJob()) {
					// on prend un objet à la tête de la liste
					job = pool.getJob();
					log.debug("Le worker n°" + numWorker + " prend un job.");
				}
				else {
					// si la liste est vide, on se supprime de la liste des workers puis on break
					pool.removeWorker(this);
					break;
				}

			}

			// à ce stade là du code, on a un job a exécuter
			if (job != null) {
				job.schedule();
				try {
					job.join();
				} catch (InterruptedException e) {
					log.warn("Le worker n°" + numWorker + " a été interrompu.");
				}
			}
			job = null;
		}
		log.debug("Fin du worker n°" + numWorker);
	}

	/**
	 * Arrête le job en cours d'exécution ainsi que 
	 */
	@SuppressWarnings("deprecation")
	public void killJob() {
		job.getThread().stop();
	}
}
