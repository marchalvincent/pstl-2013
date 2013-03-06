package com.upmc.pstl2013.util;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.log4j.Logger;

public class MyRejectedExecutionHandelerImpl implements RejectedExecutionHandler
{
	private Logger log = Logger.getLogger(MyRejectedExecutionHandelerImpl.class);
	@Override
    public void rejectedExecution(Runnable runnable,ThreadPoolExecutor executor)
    {
		log.error(runnable.toString() + " : I've been rejected ! ");
    }
}
