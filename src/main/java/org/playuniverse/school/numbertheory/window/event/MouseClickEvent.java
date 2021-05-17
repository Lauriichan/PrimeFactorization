package org.playuniverse.school.numbertheory.window.event;

import java.awt.event.MouseEvent;

import org.playuniverse.school.numbertheory.window.BWindow;

import com.syntaxphoenix.syntaxapi.event.Cancelable;
import com.syntaxphoenix.syntaxapi.event.Event;

public class MouseClickEvent extends Event implements Cancelable {

	private final BWindow window;
	private final MouseEvent event;

	private boolean cancelled = false;

	public MouseClickEvent(BWindow window, MouseEvent event) {
		this.window = window;
		this.event = event;
	}

	/*
	 * 
	 */

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	/*
	 * 
	 */

	public BWindow getWindow() {
		return window;
	}

	public MouseEvent getMouse() {
		return event;
	}

}
