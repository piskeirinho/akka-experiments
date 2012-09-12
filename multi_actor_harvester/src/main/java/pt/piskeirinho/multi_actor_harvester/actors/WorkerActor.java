package pt.piskeirinho.multi_actor_harvester.actors;

import java.util.Iterator;

import pt.keep.common.oai.RecordType;
import pt.keep.common.oai.harvester.Harvester;
import pt.piskeirinho.multi_actor_harvester.Configs;
import pt.piskeirinho.multi_actor_harvester.Utils;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

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
		System.out.println("1)" + Utils.getElapsedTime(startTime) + ")" + repo);
		boolean errorObtainingNext = false;
		int i = 0;
		Harvester harvester = new Harvester(repo);
		System.out.println("1.1)" + Utils.getElapsedTime(startTime) + ")"
				+ repo);
		harvester.setRequestRetries(Configs.DEFAULT_RETRIES);
		harvester.setRequestTimeout(Configs.DETAULT_TIMEOUT);
		try {
			Iterator<RecordType> listRecordsIterator = harvester
					.getListRecordsIterator(null, null, Configs.SET_SPEC,
							Configs.METADATA_PREFIX);
			System.out.println("1.2)" + Utils.getElapsedTime(startTime) + ")"
					+ repo);
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
		System.out.println("2)" + Utils.getElapsedTime(startTime) + ")" + repo
				+ " " + i);
		coordinatorController.tell(repo + " > " + i);
	}
}
