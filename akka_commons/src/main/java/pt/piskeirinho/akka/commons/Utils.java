package pt.piskeirinho.akka.commons;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Utils {
	private static final Logger LOGGER = LoggerFactory.getLogger(Utils.class);

	// static {
	// ConsoleAppender console = new ConsoleAppender(); // create appender
	// // configure the appender
	// String PATTERN = "%d [%p|%c|%C{1}] %m%n";
	// console.setLayout(new PatternLayout(PATTERN));
	// console.setThreshold(Level.FATAL);
	// console.activateOptions();
	// // add appender to any Logger (here is root)
	// Logger.getRootLogger().addAppender(console);
	// }

	public static double getElapsedTime(long startTime) {
		return ((double) (System.nanoTime() - startTime)) / 1000000000.0;
	}

	public static void output(String message, double elapsedTime) {
		LOGGER.info("[" + new DecimalFormat("00000.0000000000").format(elapsedTime) + "] " + message);
	}
}
