package org.playuniverse.school.numbertheory.window;

import static org.playuniverse.school.numbertheory.window.WindowHandler.getEventManager;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.playuniverse.school.numbertheory.window.event.PanePaintEvent;

import com.syntaxphoenix.syntaxapi.random.Keys;

public class BPane extends JPanel {

	private static final Keys ID_GENERATOR = new Keys();
	private static final long serialVersionUID = -5959844774896293890L;

	/*
	 * 
	 */

	protected final long id = ID_GENERATOR.makeLong();

	public final long getId() {
		return id;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		getEventManager().call(new PanePaintEvent(this, g));
	}

}
