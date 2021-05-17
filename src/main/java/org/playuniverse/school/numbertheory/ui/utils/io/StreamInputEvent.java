package org.playuniverse.school.numbertheory.ui.utils.io;

import com.syntaxphoenix.syntaxapi.event.Event;

public final class StreamInputEvent extends Event {

	private final long listenerId;
	private final String message;

	public StreamInputEvent(long listenerId, String message) {
		this.listenerId = listenerId;
		this.message = message;
	}

	public long getListenerId() {
		return listenerId;
	}

	public String getMessage() {
		return message;
	}

}
