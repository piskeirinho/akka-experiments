package pt.piskeirinho.multi_actor_harvester;

import pt.piskeirinho.multi_actor_harvester.actors.CoordinatorActor;
import pt.piskeirinho.multi_actor_harvester.actors.WorkerActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.actor.UntypedActorFactory;
import akka.routing.RoundRobinRouter;

public class MultiActorHarvester {
	private ActorSystem system;
	private ActorRef router;
	private int numberOfWorkers = 10;

	public MultiActorHarvester() {

		system = ActorSystem.create("LoadGeneratorApp");

		final ActorRef appManager = system.actorOf(new Props(
				new UntypedActorFactory() {
					private static final long serialVersionUID = 5668899944784966304L;

					public UntypedActor create() {
						return new CoordinatorActor(System.nanoTime());
					}
				}), "CoordinatorActor");

		router = system.actorOf(new Props(new UntypedActorFactory() {
			private static final long serialVersionUID = -517854977585983524L;

			public UntypedActor create() {
				return new WorkerActor(appManager, System.nanoTime());
			}
		}).withRouter(new RoundRobinRouter(numberOfWorkers)));
	}

	private void run() {
		for (String repo : Configs.repositoriesURLs) {
			router.tell(repo);
		}
	}

	public static void main(String[] args) {
		new MultiActorHarvester().run();
	}

}
