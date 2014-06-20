package pt.piskeirinho.multi_actor_harvester;

import pt.piskeirinho.multi_actor_harvester.actors.CoordinatorActor;
import pt.piskeirinho.multi_actor_harvester.actors.WorkerActor;
import pt.piskeirinho.multi_actor_harvester.config_and_utils.Configs;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;

public class MultiActorHarvester implements Runnable {
	private ActorSystem system;
	private ActorRef router;
	private int numberOfWorkers = 10;

	public MultiActorHarvester() {

		system = ActorSystem.create("LoadGeneratorApp");

		ActorRef boss = system.actorOf(
				Props.create(CoordinatorActor.class, System.nanoTime()),
				"appManager");
		router = system.actorOf(new RoundRobinPool(numberOfWorkers).props(Props
				.create(WorkerActor.class, boss, System.nanoTime())), "router");
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
