package pt.piskeirinho.multi_actor_harvester.actors;

import pt.piskeirinho.multi_actor_harvester.Configs;
import pt.piskeirinho.multi_actor_harvester.Utils;
import akka.actor.UntypedActor;

public class CoordinatorActor extends UntypedActor {
	private long startTime;
	int i = 0;

	public CoordinatorActor(long startTime) {
		this.startTime = startTime;
	}

	@Override
	public void onReceive(Object arg0) throws Exception {
		String repoOutMessage = (String) arg0;
		System.out.println("3)" + Utils.getElapsedTime(startTime) + " "
				+ repoOutMessage);
		i++;
		if (i == Configs.repositoriesURLs.size()) {
			getContext().system().shutdown();
		}
	}

}
