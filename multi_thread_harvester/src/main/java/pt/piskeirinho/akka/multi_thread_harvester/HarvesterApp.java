package pt.piskeirinho.akka.multi_thread_harvester;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pt.piskeirinho.akka.commons.Configs;
import pt.piskeirinho.akka.commons.Utils;

public class HarvesterApp implements Observer {

	private long startTime;

	public HarvesterApp() {
		setStartTime(System.nanoTime());
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			Utils.output("4 - " + arg, Utils.getElapsedTime(startTime));
		}
	}

	public static void main(String[] args) {
		HarvesterApp harvesterApp = new HarvesterApp();

		ExecutorService executor = Executors.newFixedThreadPool(Configs.N_THREDS);

		for (String url : Configs.repositoriesURLs) {
			HarvesterThread harvesterThread = new HarvesterThread(url, harvesterApp.getStartTime());
			harvesterThread.addObserver(harvesterApp);
			executor.execute(harvesterThread);
		}

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			System.err.println(e);
		}
	}
}
