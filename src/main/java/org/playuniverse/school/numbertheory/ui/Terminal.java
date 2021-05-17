package org.playuniverse.school.numbertheory.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent.EventType;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import org.playuniverse.school.numbertheory.console.Console;
import org.playuniverse.school.numbertheory.console.flags.ConsoleFlags;
import org.playuniverse.school.numbertheory.math.PrimeHelper;
import org.playuniverse.school.numbertheory.ui.scroll.JScrollIndicator;
import org.playuniverse.school.numbertheory.ui.utils.io.ListenStream;
import org.playuniverse.school.numbertheory.ui.utils.io.StreamInputEvent;
import org.playuniverse.school.numbertheory.ui.utils.log.HexLogType;
import org.playuniverse.school.numbertheory.ui.utils.log.HexTool;
import org.playuniverse.school.numbertheory.window.BPane;
import org.playuniverse.school.numbertheory.window.event.MouseClickEvent;
import org.playuniverse.school.numbertheory.window.event.PanePaintEvent;
import org.playuniverse.school.numbertheory.window.event.WindowCloseEvent;
import org.playuniverse.school.numbertheory.window.event.WindowPaintEvent;

import com.syntaxphoenix.syntaxapi.event.EventHandler;
import com.syntaxphoenix.syntaxapi.event.EventManager;
import com.syntaxphoenix.syntaxapi.logging.color.ColorTools;
import com.syntaxphoenix.syntaxapi.logging.color.LogTypeMap;

public final class Terminal extends AbstractUI implements ActionListener, DocumentListener {

    private final ExecutorService service = Executors.newCachedThreadPool();

    private final Console console;
    private final JTextPane textArea;

    private final JTextField textField;

    private String previous = "";
    private boolean bypass = false;

    public Terminal(EventManager eventManager) {
        super(400, 640, eventManager);

        System.setOut(new ListenStream(window.getId(), System.out, eventManager));
        console = new Console(System.in, System.out, ConsoleFlags.exitSystemOnShutdown());
        console.getLogger().setFormat("%message%");

        LogTypeMap typeMap = console.getLogger().getTypeMap();
        typeMap.override(new HexLogType("info", "#C5518E"));

        window.setBorderless(true);
        window.setDragable(true);
        window.init();

        BPane pane = window.getPane();
        pane.setBorder(null);
        pane.setLayout(null);
        pane.setBackground(HexTool.parse("#1C1D20"));

        JScrollIndicator scroll = new JScrollIndicator(textArea = new JTextPane());
        scroll.setBounds(11, 40, 380, 550);

        window.add(scroll);
        textArea.setBackground(HexTool.parse("#232325"));
        textArea.setEditable(false);

        textField = new JTextField();
        textField.setForeground(HexTool.parse("#AF23B4"));
        textField.setFont(new Font("Lucida Sans", Font.PLAIN, 12));
        textField.setBackground(HexTool.parse("#232325"));
        textField.setBorder(BorderFactory.createEmptyBorder());
        textField.setBounds(11, 600, 380, 28);
        textField.addActionListener(this);
        textField.getDocument().addDocumentListener(this);
        window.add(textField);

        JLabel label = new JLabel("Pfm v0.1 by Lauriichan");
        label.setForeground(HexTool.parse("#6A136D"));
        label.setFont(new Font("Lucida Sans", Font.PLAIN, 11));
        label.setBounds(8, 4, 200, 20);
        window.add(label);

        window.getHandle().setLocation(20, 20);

        register("onLoggerInput", StreamInputEvent.class);
    }

    public Console getConsoleHandle() {
        return console;
    }

    @EventHandler
    public void onLoggerInput(StreamInputEvent event) {

        int length = textArea.getDocument().getLength();

        if (!event.getMessage().contains("|")) {
            textArea.setEditable(true);
            textArea.setCaretPosition(length);
            textArea.replaceSelection(event.getMessage());
            textArea.setEditable(false);
            return;
        }
        String[] part = event.getMessage().split("\\|", 2);

        Color color = ColorTools.hex2rgb(part[0]);
        StyleContext context = StyleContext.getDefaultStyleContext();
        AttributeSet set = context.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);
        set = context.addAttribute(set, StyleConstants.FontFamily, "Lucida Console");
        set = context.addAttribute(set, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

        try {
            textArea.setEditable(true);
            textArea.setCaretPosition(length);
            textArea.setCharacterAttributes(set, false);
            textArea.replaceSelection(part[1]);
            textArea.setEditable(false);
        } catch (NullPointerException ignore) {
            // Ignore
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String text = textField.getText();
        if (text.isEmpty()) {
            console.log("0 = 0");
            textField.setText("0");
            return;
        }

        bypass = true;
        textField.setText("Calculating prime factors of " + text + "...");
        textField.setEditable(false);
        service.submit(() -> {
            console.log(PrimeHelper.INSTANCE.toFractionString(Long.parseLong(text)));
            bypass = false;
            textField.setEditable(true);
            textField.setText("");
        });
    }

    @Override
    public void insertUpdate(DocumentEvent event) {
        changedUpdate(event);
    }

    @Override
    public void removeUpdate(DocumentEvent event) {
        changedUpdate(event);
    }

    @Override
    public void changedUpdate(DocumentEvent event) {
        if (event.getType() == EventType.REMOVE || bypass) {
            return;
        }
        String text = textField.getText();
        try {
            long value = Long.parseLong(text);
            long absValue = Math.abs(value);
            if (absValue != value) {
                SwingUtilities.invokeLater(() -> {
                    previous = "" + absValue;
                    textField.setText(previous);
                });
                return;
            }
            previous = text;
        } catch (NumberFormatException nfe) {
            SwingUtilities.invokeLater(() -> textField.setText(previous));
        }
    }

    /*
     * Handle window events
     */

    @Override
    protected void click(MouseClickEvent event, MouseEvent mouse) {

        if (mouse.getButton() != MouseEvent.BUTTON1) {
            return;
        }

        int x = mouse.getX();
        int y = mouse.getY();

        /*
         * Controls
         */
        if ((x <= 393 && x >= 375) && (y <= 24 && y >= 6)) {
            mouse.consume();
            console.shutdown();
            return;
        }
        if ((x <= 368 && x >= 350) && (y <= 24 && y >= 6)) {
            mouse.consume();
            minimize();
        }

    }

    @Override
    protected void drawWindow(WindowPaintEvent event, Graphics graphics) {}

    @Override
    protected void drawPane(PanePaintEvent event, Graphics graphics) {

        /*
         * Controls
         */

        graphics.setColor(HexTool.parse("#AF23B4"));
        // Close
        graphics.drawLine(378, 21, 390, 9);
        graphics.drawLine(378, 9, 390, 21);
        // Minimize
        graphics.drawLine(353, 21, 365, 21);

        /*
         * Shade
         */

        graphics.setColor(HexTool.parse("#1B1B1D"));
        // TextArea
        graphics.fillRect(8, 40, 386, 554);
        // TextField
        graphics.fillRect(8, 600, 386, 32);

    }

    @Override
    protected void close(WindowCloseEvent event, WindowEvent close) {
        shutdown();
    }

    public void shutdown() {
        setEnabled(false);
        try {
            console.shutdown();
        } catch (Exception ignore) {
        }
    }

}
