package org.playuniverse.school.numbertheory.window;

import com.syntaxphoenix.syntaxapi.event.EventManager;
import com.syntaxphoenix.syntaxapi.logging.ILogger;

public final class WindowHandler {

	private WindowHandler() {}

	/*
	 * 
	 */

	private static EventManager HANDLER;
	private static ILogger LOGGER;

	public static void setLogger(ILogger logger) {
		if (LOGGER != null)
			return;
		LOGGER = logger;
	}

	public static ILogger getLogger() {
		return LOGGER;
	}

	public static void setEventManager(EventManager manager) {
		if (HANDLER != null)
			return;
		HANDLER = manager;
	}

	public static EventManager getEventManager() {
		return HANDLER;
	}

}
