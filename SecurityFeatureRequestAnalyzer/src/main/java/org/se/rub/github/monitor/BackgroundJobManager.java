package org.se.rub.github.monitor;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class BackgroundJobManager implements ServletContextListener {

	public static BackgroundJobManager INSTANCE;

	private int frequency = 60;

	private ScheduledExecutorService scheduler;
	private ScheduledFuture<?> job;

	@Override
	public void contextInitialized(final ServletContextEvent sce) {
		INSTANCE = this;
		this.frequency = ConfigurationManager.getFrequency();
		this.scheduler = Executors.newSingleThreadScheduledExecutor();
		this.schedule();
	}

	@Override
	public void contextDestroyed(final ServletContextEvent sce) {
		this.scheduler.shutdown();
	}

	public void setFrequency(final int frequency) {
		System.out.println("Set frequency to " + frequency);
		this.job.cancel(true);
		this.frequency = frequency;
		this.schedule();
	}

	private void schedule() {
		this.job = this.scheduler.scheduleAtFixedRate(new GitHubQuery(System.currentTimeMillis()), 0, this.frequency,
				TimeUnit.SECONDS);
	}
}
