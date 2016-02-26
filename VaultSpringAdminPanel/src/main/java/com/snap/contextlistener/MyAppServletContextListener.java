package com.snap.contextlistener;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;



public class MyAppServletContextListener implements ServletContextListener{

	private ScheduledExecutorService scheduler;
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		scheduler.shutdownNow();	
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		
		scheduler = Executors.newSingleThreadScheduledExecutor();
	    scheduler.scheduleAtFixedRate(new VaultCron(), 0, 7, TimeUnit.HOURS);
		
	}

	
	
	
}
