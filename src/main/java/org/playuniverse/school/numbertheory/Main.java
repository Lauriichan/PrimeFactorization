package org.playuniverse.school.numbertheory;

import javax.swing.BorderFactory;
import javax.swing.UIManager;

import org.playuniverse.school.numbertheory.ui.Terminal;
import org.playuniverse.school.numbertheory.window.WindowHandler;

import com.syntaxphoenix.syntaxapi.event.EventManager;

public final class Main {

    public static void main(String[] args) throws Exception {

        EventManager eventManager = new EventManager();
        Terminal terminal = new Terminal(eventManager);

        UIManager.put("List.focusCellHighlightBorder", BorderFactory.createEmptyBorder());
        WindowHandler.setLogger(terminal.getConsoleHandle());
        WindowHandler.setEventManager(eventManager);

        terminal.show();

    }

}
