package pt.piskeirinho.multi_actor_harvester;

public final class Utils {
	public static double getElapsedTime(long startTime) {
		return ((double) (System.nanoTime() - startTime)) / 1000000000.0;
	}
}
