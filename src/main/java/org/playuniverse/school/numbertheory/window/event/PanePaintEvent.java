package org.playuniverse.school.numbertheory.window.event;

import java.awt.Graphics;

import org.playuniverse.school.numbertheory.window.BPane;

public class PanePaintEvent extends PaintEvent {

	private final BPane pane;

	public PanePaintEvent(BPane pane, Graphics graphics) {
		super(graphics);
		this.pane = pane;
	}

	public BPane getPane() {
		return pane;
	}

}
