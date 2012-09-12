package pt.piskeirinho.multi_actor_harvester;

import java.util.Iterator;

import pt.keep.common.oai.RecordType;
import pt.keep.common.oai.harvester.Harvester;
import pt.piskeirinho.multi_actor_harvester.config_and_utils.Configs;
import pt.piskeirinho.multi_actor_harvester.config_and_utils.Utils;

public class SequentialHarvester {
	private Harvester harvester = null;
	private long startTime;

	public SequentialHarvester() {
		startTime = System.nanoTime();
	}

	public void run() {
		boolean errorObtainingNext = false;
		int i = 0;
		for (String repo : Configs.repositoriesURLs) {
			System.out.println("1)" + Utils.getElapsedTime(startTime) + ")"
					+ repo);
			harvester = new Harvester(repo);
			System.out.println("1.1)" + Utils.getElapsedTime(startTime) + ")"
					+ repo);
			harvester.setRequestRetries(Configs.DEFAULT_RETRIES);
			harvester.setRequestTimeout(Configs.DETAULT_TIMEOUT);
			try {
				Iterator<RecordType> listRecordsIterator = harvester
						.getListRecordsIterator(null, null, Configs.SET_SPEC,
								Configs.METADATA_PREFIX);
				System.out.println("1.2)" + Utils.getElapsedTime(startTime)
						+ ")" + repo);
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
			System.out.println(Utils.getElapsedTime(startTime) + " :> " + repo
					+ " - " + i);
			i = 0;
		}
	}

	public static void main(String[] args) {
		new SequentialHarvester().run();
	}
}
