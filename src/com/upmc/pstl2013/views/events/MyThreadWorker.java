package com.upmc.pstl2013.views.events;

import java.util.LinkedList;
import org.apache.log4j.Logger;


public class MyThreadWorker extends Thread {
	
	private final Logger log = Logger.getLogger(MyThreadWorker.class);
	private final LinkedList<JobExecutor> jobs;
	private final int numWorker;
	
	public MyThreadWorker(MyJobPoolExecutor pool, int numWorker) {
		super();
		this.jobs = pool.jobs();
		this.numWorker = numWorker;
	}
	
	public int getNum() {
		return numWorker;
	}
	
	@Override
	public void run() {
		JobExecutor job = null;
		log.debug("Lancement du worker n°" + numWorker);
		while (true) {
			// on se synchronize sur la liste et on récupère le 1er élément.
			synchronized (jobs) {
				
				if (!jobs.isEmpty()) {
					// on prend un objet à la tête de la liste
					job = jobs.removeFirst();
					log.debug("Le worker n°" + numWorker + " prend un job.");
				}
				else {
					// si la liste est vide, on break
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
		}
		log.debug("Fin du worker n°" + numWorker);
	}
}
