package com.upmc.pstl2013.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.views.events.JobExecutor;
import com.upmc.pstl2013.views.events.RunnableUpdateExecutor;

public class JobTimeout extends Job {


	private List<JobExecutor> listJobsExec;
	private long timeout;
	private Logger log = Logger.getLogger(JobTimeout.class);
	private SwtView swtView;


	public JobTimeout(List<JobExecutor> listJobsExec,int timeout, SwtView swtView) {
		super("TimeOut");
		this.listJobsExec = listJobsExec;
		this.timeout = timeout;
		this.swtView = swtView;
	}


	@Override
	protected IStatus run(IProgressMonitor monitor) {
		long endTime, timeSpend = 0;
		long startTime = System.nanoTime();
		Boolean isEnd = false;
		log.info("Durée timeout : " + timeout);
		while ((timeSpend < timeout) && !isEnd) {
			try {
				Thread.sleep(1000);
				endTime = System.nanoTime();
				timeSpend = (endTime - startTime) / 1000000000; // en sec
				isEnd = true;
				for (JobExecutor job : listJobsExec) {
					if((job.getResult() != null))
					{
						isEnd = false;
						break;
					}
				}
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
		}

		if(!isEnd){
			for (JobExecutor job : listJobsExec) {
				if (job.getResult() == null) {
					if (job.cancel() || !job.getThread().isInterrupted())
					{
						log.info("job " + job.getName() + " was stopped !");
						showToView("/!\\The execution of " + job.getName() + " was stopped !");
					}
					else
					{
						log.info("job " + job.getName() + " couldn't be stopped !");
						showToView("/!\\The execution of " + job.getName() + " was stopped !");
					}
				}
			}
		}
		log.debug("/!\\ Fin du thread timeOut.\n");
		return Status.OK_STATUS;
	}

	private void showToView(String msg){
		Display.getDefault().asyncExec(new RunnableUpdateExecutor(swtView, msg));
	}


}
