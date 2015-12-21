package pt.piskeirinho.akka.multi_actor_harvester.actors;

import java.util.Iterator;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import pt.keep.common.oai.RecordType;
import pt.keep.common.oai.harvester.Harvester;
import pt.piskeirinho.akka.commons.Configs;
import pt.piskeirinho.akka.commons.Utils;

public class WorkerActor extends UntypedActor {
	private ActorRef coordinatorController;
	private long startTime;

	public WorkerActor(ActorRef coordinatorController, long startTime) {
		this.coordinatorController = coordinatorController;
		this.startTime = startTime;
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		String repo = (String) arg0;
		Utils.output("1 - Starting to harvest \"" + repo + "\"", Utils.getElapsedTime(startTime));
		boolean errorObtainingNext = false;
		int i = 0;
		Harvester harvester = new Harvester(repo);
		Utils.output("2 - Instantiated harvester for \"" + repo + "\"", Utils.getElapsedTime(startTime));
		harvester.setRequestRetries(Configs.DEFAULT_RETRIES);
		harvester.setRequestTimeout(Configs.DETAULT_TIMEOUT);
		try {
			Iterator<RecordType> listRecordsIterator = harvester.getListRecordsIterator(null, null, Configs.SET_SPEC,
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
		Utils.output("3 - Done harvesting \"" + repo + "\"", Utils.getElapsedTime(startTime));
		coordinatorController.tell("\"" + repo + "\" has " + i + " documents", getSelf());
	}
}
