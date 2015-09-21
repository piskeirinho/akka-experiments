package pt.piskeirinho.multi_actor_with_futures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.dispatch.Futures;
import akka.dispatch.Mapper;
import akka.pattern.Patterns;
import akka.routing.RoundRobinPool;
import akka.util.Timeout;
import pt.piskeirinho.multi_actor_with_futures.actors.WorkerActor;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;

public class MultiActorWithFutures implements Runnable {
	private ActorSystem system;
	private ActorRef router;
	private ActorRef boss;
	private int numberOfWorkers = 3;
	public static List<String> array = Arrays.asList("a", "b", "c", "d", "e", "f", "g", "h", "i", "j");

	public MultiActorWithFutures() {

		system = ActorSystem.create("system");
		// boss = system.actorOf(Props.create(CoordinatorActor.class), "boss");
		router = system.actorOf(new RoundRobinPool(numberOfWorkers).props(Props.create(WorkerActor.class)), "router");
	}

	public void run() {
		List<Future<Object>> futures = new ArrayList<Future<Object>>();
		int i = 0;
		List<String> workToDo = new ArrayList<String>();
		for (String string : array) {
			if (i == numberOfWorkers) {
				futures.add(Patterns.ask(router, workToDo, new Timeout(Duration.create(60, TimeUnit.SECONDS))));
				workToDo = new ArrayList<String>();
				i = 0;
			}
			workToDo.add(string);
			i++;
		}

		if (workToDo.size() > 0) {
			futures.add(Patterns.ask(router, workToDo, new Timeout(Duration.create(60, TimeUnit.SECONDS))));
		}

		final Future<Iterable<Object>> futuresResult = Futures.sequence(futures, system.dispatcher());

		// final Future<String> transformed =
		futuresResult.map(new Mapper<Iterable<Object>, String>() {
			public String apply(Iterable<Object> coll) {
				final Iterator<Object> it = coll.iterator();
				String res = "";
				while (it.hasNext()) {
					res = res + " " + it.next() + " ";
				}
				System.out.println(">" + res);
				return res.trim();

			}
		}, system.dispatcher());

		// Patterns.pipe(transformed, system.dispatcher()).to(boss);

		System.out.println("End of method");

	}

	public static void main(String[] args) {
		MultiActorWithFutures multiActorHarvester = new MultiActorWithFutures();
		multiActorHarvester.run();
		array = Arrays.asList("a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1", "i1", "j1");
		multiActorHarvester.run();
	}

}
