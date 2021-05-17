package org.playuniverse.school.numbertheory.ui;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;

import org.playuniverse.school.numbertheory.window.BWindow;
import org.playuniverse.school.numbertheory.window.event.MouseClickEvent;
import org.playuniverse.school.numbertheory.window.event.PanePaintEvent;
import org.playuniverse.school.numbertheory.window.event.WindowCloseEvent;
import org.playuniverse.school.numbertheory.window.event.WindowPaintEvent;

import com.syntaxphoenix.syntaxapi.event.Event;
import com.syntaxphoenix.syntaxapi.event.EventHandler;
import com.syntaxphoenix.syntaxapi.event.EventListener;
import com.syntaxphoenix.syntaxapi.event.EventManager;
import com.syntaxphoenix.syntaxapi.event.EventMethod;

public abstract class AbstractUI implements EventListener {

    private static Method getMethod(Object instance, String name, Class<?> clazz) {
        try {
            return instance.getClass().getDeclaredMethod(name, clazz);
        } catch (NoSuchMethodException | SecurityException e0) {
            try {
                return AbstractUI.class.getDeclaredMethod(name, clazz);
            } catch (NoSuchMethodException | SecurityException e1) {
                return null;
            }
        }
    }

    private boolean enabled = false;

    protected final EventManager eventManager;
    protected final BWindow window;

    public AbstractUI(int width, int height, EventManager eventManager) {
        window = new BWindow(width, height);
        this.eventManager = eventManager;

        register("onPaintPane", PanePaintEvent.class);
        register("onPaintWindow", WindowPaintEvent.class);
        register("onClick", MouseClickEvent.class);
        register("onClose", WindowCloseEvent.class);
    }

    public void register(String name, Class<? extends Event> clazz) {
        eventManager.registerEvent(new EventMethod(this, getMethod(this, name, clazz)));
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @EventHandler
    public final void onPaintPane(PanePaintEvent event) {
        if (!enabled)
            return;
        if (window.getPane().getId() != event.getPane().getId())
            return;
        drawPane(event, event.getGraphics());
    }

    @EventHandler
    public final void onPaintWindow(WindowPaintEvent event) {
        if (!enabled)
            return;
        if (window.getId() != event.getWindow().getId())
            return;
        drawWindow(event, event.getGraphics());
    }

    @EventHandler
    public final void onClick(MouseClickEvent event) {
        if (!enabled)
            return;
        if (window.getId() != event.getWindow().getId())
            return;
        click(event, event.getMouse());
    }

    @EventHandler
    public final void onClose(WindowCloseEvent event) {
        if (!enabled)
            return;
        if (window.getId() != event.getWindow().getId())
            return;
        close(event, event.getClose());
    }

    protected abstract void close(WindowCloseEvent event, WindowEvent close);

    protected abstract void click(MouseClickEvent event, MouseEvent mouse);

    protected abstract void drawPane(PanePaintEvent event, Graphics graphics);

    protected abstract void drawWindow(WindowPaintEvent event, Graphics graphics);

    public void show() {
        setEnabled(true);
        window.setVisible(true);
    }

    public void hide() {
        window.setVisible(false);
        setEnabled(false);
    }

    public void minimize() {
        window.getHandle().setExtendedState(Frame.ICONIFIED);
    }

    public void maximize() {
        window.getHandle().setExtendedState(Frame.NORMAL);
    }

}
