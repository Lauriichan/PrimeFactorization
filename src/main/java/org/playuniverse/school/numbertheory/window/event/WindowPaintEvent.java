package org.playuniverse.school.numbertheory.window.event;

import java.awt.Graphics;

import org.playuniverse.school.numbertheory.window.BWindow;

public class WindowPaintEvent extends PaintEvent {

	private final BWindow window;

	public WindowPaintEvent(BWindow window, Graphics graphics) {
		super(graphics);
		this.window = window;
	}

	public BWindow getWindow() {
		return window;
	}

}
