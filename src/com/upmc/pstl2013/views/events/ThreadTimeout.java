package com.upmc.pstl2013.views.events;

import java.util.List;
import org.apache.log4j.Logger;

public class ThreadTimeout extends Thread {


	private List<JobExecutor> listJobsExec;
	private long timeout;
	private Logger log = Logger.getLogger(ThreadTimeout.class);


	public ThreadTimeout(List<JobExecutor> listJobsExec,int timeout) {
		this.listJobsExec = listJobsExec;
		this.timeout = timeout;
	}


	@Override
	public void run() {
		long endTime, timeSpend = 0;
		long startTime = System.nanoTime();
		Boolean isEnd = false;
		System.out.println("TIMEOUT : " + timeout);
		while ((timeSpend < timeout) && !isEnd) {
			try {
				Thread.sleep(1000);
				endTime = System.nanoTime();
				timeSpend = (endTime - startTime) / 1000000000; // en sec
				
				for (JobExecutor job : listJobsExec) {
					if((job.getResult() != null))
					{
						isEnd = true;
						break;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		if(!isEnd){
			for (JobExecutor job : listJobsExec) {
				if (job.getResult() == null) {
					if (job.cancel() || !job.getThread().isInterrupted())
						log.info("job " + job.getName() + " was stopped !");
					else
						log.info("job " + job.getName() + " couldn't be stopped !");
				}
			}
		}
	}
}
