package pt.piskeirinho.akka.multi_actor_harvester;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;
import pt.piskeirinho.akka.commons.Configs;
import pt.piskeirinho.akka.multi_actor_harvester.actors.CoordinatorActor;
import pt.piskeirinho.akka.multi_actor_harvester.actors.WorkerActor;

public class MultiActorHarvester implements Runnable {
	private ActorSystem system;
	private ActorRef router;
	private int numberOfWorkers = Configs.N_WORKERS;

	public MultiActorHarvester() {

		system = ActorSystem.create("MultiActorHarvester");

		ActorRef boss = system.actorOf(Props.create(CoordinatorActor.class, System.nanoTime()), "appManager");
		router = system.actorOf(
				new RoundRobinPool(numberOfWorkers).props(Props.create(WorkerActor.class, boss, System.nanoTime())),
				"router");
	}

	public void run() {
		for (String repo : Configs.repositoriesURLs) {
			router.tell(repo, router);
		}
	}

	public static void main(String[] args) {
		new MultiActorHarvester().run();
	}

}
