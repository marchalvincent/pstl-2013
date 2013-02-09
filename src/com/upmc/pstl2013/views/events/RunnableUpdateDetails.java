package com.upmc.pstl2013.views.events;

import com.upmc.pstl2013.alloyExecutor.IFileResult;
import com.upmc.pstl2013.views.SwtView;

public class RunnableUpdateDetails implements Runnable {

	private SwtView swtView;
	private IFileResult iFileResult;

	public RunnableUpdateDetails(SwtView swtView, IFileResult iFileResult) {
		this.swtView = swtView;
		this.iFileResult = iFileResult;
	}

	@Override
	public void run() {
		swtView.updateTreeExecResult(iFileResult);

	}

}
