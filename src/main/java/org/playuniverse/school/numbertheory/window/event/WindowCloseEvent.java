package org.playuniverse.school.numbertheory.window.event;

import java.awt.event.WindowEvent;

import org.playuniverse.school.numbertheory.window.BWindow;

import com.syntaxphoenix.syntaxapi.event.Event;

public class WindowCloseEvent extends Event {

	private final BWindow window;
	private final WindowEvent close;

	public WindowCloseEvent(BWindow window, WindowEvent close) {
		this.window = window;
		this.close = close;
	}

	public BWindow getWindow() {
		return window;
	}

	public WindowEvent getClose() {
		return close;
	}

}
