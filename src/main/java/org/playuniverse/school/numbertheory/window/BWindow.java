package org.playuniverse.school.numbertheory.window;

import static org.playuniverse.school.numbertheory.window.WindowHandler.getEventManager;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import org.playuniverse.school.numbertheory.window.event.MouseClickEvent;
import org.playuniverse.school.numbertheory.window.event.WindowCloseEvent;
import org.playuniverse.school.numbertheory.window.event.WindowPaintEvent;

import com.syntaxphoenix.syntaxapi.random.Keys;

public class BWindow {

	private static final Keys ID_GENERATOR = new Keys();

	protected final long id = ID_GENERATOR.makeLong();

	protected final JFrame window;
	protected final BPane bPane;
	protected final BWindow bWindow = this;
	protected boolean initialized = false;

	protected int motionX, motionY, cancel;
	protected boolean dragable, borderless;

	protected int width;
	protected int height;

	public BWindow() {
		this(800, 600);
	}

	public BWindow(int width, int height) {
		this.window = new JFrame() {
			private static final long serialVersionUID = 5750270060727046840L;

			@Override
			public void paint(Graphics graphics) {
				super.paint(graphics);
				getEventManager().call(new WindowPaintEvent(bWindow, graphics));
			}
		};
		this.bPane = new BPane();
		bPane.setDoubleBuffered(false);
		window.setContentPane(bPane);
		setWidthAndHeight(width, height);
	}

	public final long getId() {
		return id;
	}

	/*
	 * 
	 */

	public void init() {
		if (initialized)
			return;
		initialized = true;

		window.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent we) {
				getEventManager().call(new WindowCloseEvent(bWindow, we));
			}
		});

		if (borderless)
			window.setUndecorated(true);

		if (dragable) {
			window.addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent me) {
					motionX = me.getX();
					motionY = me.getY();

					MouseClickEvent event = new MouseClickEvent(bWindow, me);
					getEventManager().call(event);
					cancel = event.isCancelled() ? 1 : 0;
				}

				public void mouseDragged(MouseEvent me) {
					if (cancel == 0)
						window.setLocation(window.getLocation().x + me.getX() - motionX, window.getLocation().y + me.getY() - motionY);
				}
			});
			window.addMouseMotionListener(new MouseMotionAdapter() {
				public void mouseDragged(MouseEvent me) {
					if (cancel == 0)
						window.setLocation(window.getLocation().x + me.getX() - motionX, window.getLocation().y + me.getY() - motionY);
				}
			});
		}
	}

	/*
	 * 
	 */

	public JFrame getHandle() {
		return window;
	}

	public BPane getPane() {
		return bPane;
	}

	/*
	 * 
	 */

	public BWindow setDragable(boolean dragable) {
		if (!initialized)
			this.dragable = dragable;
		return this;
	}

	public BWindow setBorderless(boolean borderless) {
		if (!initialized)
			this.borderless = borderless;
		return this;
	}

	public boolean isDragable() {
		return dragable;
	}

	public boolean isBorderless() {
		return borderless;
	}

	/*
	 * 
	 */

	public BWindow setVisible(boolean visible) {
		if (initialized)
			window.setVisible(visible);
		return this;
	}

	public boolean isVisible() {
		return window.isVisible();
	}

	/*
	 * 
	 */

	public void add(Component component) {
		window.add(component);
	}

	public void remove(Component component) {
		window.remove(component);
	}

	/*
	 * 
	 */

	public BWindow setWidth(int width) {
		return setWidthAndHeight(width, height);
	}

	public BWindow setHeight(int height) {
		return setWidthAndHeight(width, height);
	}

	public BWindow setWidthAndHeight(int width, int height) {
		Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		window.setBounds(center.x - (width / 2), center.y - (height / 2), (this.width = width), (this.height = height));
		return this;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
