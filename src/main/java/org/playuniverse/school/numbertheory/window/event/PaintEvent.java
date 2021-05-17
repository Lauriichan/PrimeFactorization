package org.playuniverse.school.numbertheory.window.event;

import java.awt.Graphics;

import com.syntaxphoenix.syntaxapi.event.Event;

public abstract class PaintEvent extends Event {
	private final Graphics graphics;

	public PaintEvent(Graphics graphics) {
		this.graphics = graphics;
	}

	public Graphics getGraphics() {
		return graphics;
	}

}
