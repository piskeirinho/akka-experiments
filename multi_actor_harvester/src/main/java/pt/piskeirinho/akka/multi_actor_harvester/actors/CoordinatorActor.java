package pt.piskeirinho.akka.multi_actor_harvester.actors;

import akka.actor.UntypedActor;
import pt.piskeirinho.akka.commons.Configs;
import pt.piskeirinho.akka.commons.Utils;

public class CoordinatorActor extends UntypedActor {
	private long startTime;
	int i = 0;

	public CoordinatorActor(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		String repoOutMessage = (String) arg0;
		Utils.output("4 - " + repoOutMessage, Utils.getElapsedTime(startTime));
		i++;
		if (i == Configs.repositoriesURLs.size()) {
			getContext().system().shutdown();
		}
	}

}
