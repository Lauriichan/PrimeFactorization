package org.playuniverse.school.numbertheory.ui.utils.io;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.playuniverse.school.numbertheory.ui.utils.io.StreamActionEvent.Action;

import com.syntaxphoenix.syntaxapi.event.EventManager;

public class ListenStream extends PrintStream {

	private final PrintStream passthrough;
	private final long listenerId;

	private final EventManager manager;

	public ListenStream(long listenerId, PrintStream passthrough, EventManager manager) {
		super(new ByteArrayOutputStream(), false);
		this.manager = manager;
		this.listenerId = listenerId;
		this.passthrough = passthrough;
	}

	/*
	 * Get manager
	 */

	private EventManager getManager() {
		return manager;
	}

	public PrintStream getPassthrough() {
		return passthrough;
	}

	/*
	 * Text input
	 */

	@Override
	public void print(String s) {

        // Call event
        getManager().call(new StreamInputEvent(listenerId, s));

		// Passthrough
		passthrough.print(s);
	}

	@Override
	public void println(String x) {

        // Call event
        getManager().call(new StreamInputEvent(listenerId, x + '\n'));

		// Passthrough
		passthrough.println(x);
	}

	/*
	 * Flush / Close
	 */

	@Override
	public void flush() {

		/*
		 * Flush passthrough and tell our listener that we're emptying
		 */

		passthrough.flush();

		// Call event
		getManager().call(new StreamActionEvent(listenerId, Action.FLUSH));

	}

	@Override
	public void close() {

		/*
		 * Close the passthrough and tell our listener that we're closing
		 */

		passthrough.close();

		// Call event
		getManager().call(new StreamActionEvent(listenerId, Action.CLOSE));

	}

}
