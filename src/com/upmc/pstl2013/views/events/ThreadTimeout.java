package com.upmc.pstl2013.views.events;

import java.util.List;

import org.apache.log4j.Logger;

public class ThreadTimeout extends Thread {

	
	private List<JobExecutor> listJobsExec;
	private long timeout;
	private Logger log = Logger.getLogger(ThreadTimeout.class);


	public ThreadTimeout(List<JobExecutor> listJobsExec,int timeout)
	{
		this.listJobsExec = listJobsExec;
		this.timeout = timeout;
	}
	
	
	@Override
	public void run()
	{
		long endTime, timeSpend = 0;
		long startTime = System.nanoTime();
		
		while (timeSpend < timeout)
		{
			try {
				Thread.sleep(1000);
				endTime = System.nanoTime();
				timeSpend = (endTime - startTime) / 1000000000; // en sec
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (JobExecutor job : listJobsExec) {
			if (job.getResult() == null)
			{
				if (job.cancel())
					log.info("job " + job.getName() + " was stopped !");
				else
					log.info("job " + job.getName() + " couldn't be stopped !");
			}
		}
	}
}
