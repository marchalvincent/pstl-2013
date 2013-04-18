package com.upmc.pstl2013.util;

import org.apache.log4j.Logger;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import com.upmc.pstl2013.views.SwtView;
import com.upmc.pstl2013.views.events.MyJobPoolExecutor;

public class JobTimeout extends Job {

	private MyJobPoolExecutor jobPool;
	private long timeout;
	private SwtView swtView;
	private Logger log = Logger.getLogger(JobTimeout.class);


	public JobTimeout(MyJobPoolExecutor jobPool, int timeout, SwtView swtView) {
		super("TimeOut");
		this.jobPool = jobPool;
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
				synchronized (this) {
					this.wait(1000);
				}
				endTime = System.nanoTime();
				timeSpend = (endTime - startTime) / 1000000000; // en sec

				if (jobPool.isFinish()) {
					isEnd = true;
				}
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
		}

		String returns = jobPool.killWorkers();
		swtView.getDataView().showToViewUse(returns);

		log.debug("/!\\ Fin du thread timeOut.\n");
		return Status.OK_STATUS;
	}
}
