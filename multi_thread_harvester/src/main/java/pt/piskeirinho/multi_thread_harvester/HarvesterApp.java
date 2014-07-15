package pt.piskeirinho.multi_thread_harvester;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import pt.piskeirinho.multi_thread_harvester.config_and_utils.Configs;
import pt.piskeirinho.multi_thread_harvester.config_and_utils.Utils;

public class HarvesterApp implements Observer {

	public static final int NTHREDS = 5;
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

		ExecutorService executor = Executors.newFixedThreadPool(NTHREDS);

		for (String url : Configs.repositoriesURLs) {
			HarvesterThread harvesterThread = new HarvesterThread(url,
					harvesterApp.getStartTime());
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
