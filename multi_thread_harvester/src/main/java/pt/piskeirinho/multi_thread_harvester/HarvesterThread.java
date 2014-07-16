package pt.piskeirinho.multi_thread_harvester;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Observable;

import pt.keep.common.oai.RecordType;
import pt.keep.common.oai.harvester.Harvester;
import pt.piskeirinho.multi_thread_harvester.config_and_utils.Configs;
import pt.piskeirinho.multi_thread_harvester.config_and_utils.Utils;

public class HarvesterThread extends Observable implements Runnable {

	private String url;
	private long startTime;

	public HarvesterThread(String url, long startTime) {
		this.url = url;
		this.startTime = startTime;
	}

	@Override
	public void run() {
		String repo = url;
		Utils.output("1 - Starting to harvest \"" + repo + "\"",
				Utils.getElapsedTime(startTime));
		boolean errorObtainingNext = false;
		int i = 0;
		Harvester harvester = new Harvester(repo);
		Utils.output("2 - Instantiated harvester for \"" + repo + "\"",
				Utils.getElapsedTime(startTime));
		harvester.setRequestRetries(Configs.DEFAULT_RETRIES);
		harvester.setRequestTimeout(Configs.DETAULT_TIMEOUT);
		try {
			Iterator<RecordType> listRecordsIterator = harvester
					.getListRecordsIterator(null, null, Configs.SET_SPEC,
							Configs.METADATA_PREFIX, Configs.REQUEST_HEADERS);
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
		Utils.output("3 - Done harvesting \"" + repo + "\"",
				Utils.getElapsedTime(startTime));
		setChanged();
		notifyObservers("\"" + repo + "\" has " + i + " documents");
	}

}
