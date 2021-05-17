package org.playuniverse.school.numbertheory.ui.utils.io;

import com.syntaxphoenix.syntaxapi.event.Event;

public final class StreamActionEvent extends Event {

	public enum Action {
		FLUSH,
		CLOSE;
	}

	private final long listenerId;
	private final Action action;

	public StreamActionEvent(long listenerId, Action action) {
		this.listenerId = listenerId;
		this.action = action;
	}

	public long getListenerId() {
		return listenerId;
	}

	public Action getAction() {
		return action;
	}

}
