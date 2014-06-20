package pt.piskeirinho.multi_actor_harvester;

import java.util.Iterator;

import pt.keep.common.oai.RecordType;
import pt.keep.common.oai.harvester.Harvester;
import pt.piskeirinho.multi_actor_harvester.config_and_utils.Configs;
import pt.piskeirinho.multi_actor_harvester.config_and_utils.Utils;

public class SequentialHarvester implements Runnable {
	private Harvester harvester = null;
	private long startTime;

	public SequentialHarvester() {
		startTime = System.nanoTime();
	}

	public void run() {
		boolean errorObtainingNext = false;
		int i = 0;
		for (String repo : Configs.repositoriesURLs) {
			Utils.output("1 - Starting to harvest \"" + repo + "\"",
					Utils.getElapsedTime(startTime));
			harvester = new Harvester(repo);
			Utils.output("2 - Instantiated harvester for \"" + repo + "\"",
					Utils.getElapsedTime(startTime));
			harvester.setRequestRetries(Configs.DEFAULT_RETRIES);
			harvester.setRequestTimeout(Configs.DETAULT_TIMEOUT);
			try {
				Iterator<RecordType> listRecordsIterator = harvester
						.getListRecordsIterator(null, null, Configs.SET_SPEC,
								Configs.METADATA_PREFIX);
				while (listRecordsIterator.hasNext() && !errorObtainingNext) {
					try {
						listRecordsIterator.next();
						i++;
					} catch (Exception e) {
						System.err.println(e);
						errorObtainingNext = true;
					}
				}
			} catch (Exception e) {
				System.err.println(e);
			}
			Utils.output("3 - Done harvesting \"" + repo + "\" - it has " + i
					+ " documents", Utils.getElapsedTime(startTime));
			i = 0;
		}
	}

	public static void main(String[] args) {
		new SequentialHarvester().run();
	}
}
