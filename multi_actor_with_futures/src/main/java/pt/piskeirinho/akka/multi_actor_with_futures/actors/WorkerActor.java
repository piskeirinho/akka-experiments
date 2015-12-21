package pt.piskeirinho.akka.multi_actor_with_futures.actors;

import akka.actor.UntypedActor;

public class WorkerActor extends UntypedActor {

	public WorkerActor() {

	}

	@Override
	public void onReceive(Object msg) throws Exception {
		System.out.println(getSelf().path().toString() + "| WorkerActor: doing work; message: " + msg);
		Thread.sleep(2000);
		System.out.println(getSelf().path() + "| WorkerActor: done; message: " + msg);
		getSender().tell(msg, getSelf());
	}
}
