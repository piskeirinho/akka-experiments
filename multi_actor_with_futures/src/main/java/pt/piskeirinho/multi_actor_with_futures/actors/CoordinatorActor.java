package pt.piskeirinho.multi_actor_with_futures.actors;

import akka.actor.UntypedActor;

public class CoordinatorActor extends UntypedActor {

	public CoordinatorActor() {
	}

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println(getSelf().path() + " | CoordinatorActor: " + msg);
		// getContext().system().shutdown();
	}

}
