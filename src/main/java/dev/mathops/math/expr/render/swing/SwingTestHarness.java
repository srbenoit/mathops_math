package dev.mathops.math.expr.render.swing;

import dev.mathops.commons.ui.UIUtilities;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Dimension;

/**
 * A simple test harness that builds an expression then lays it out and renders it to a panel.
 */
public final class SwingTestHarness implements Runnable {

    /**
     * Constructs a new {@code SwingTestHarness}.
     */
    private SwingTestHarness() {

        // No action
    }

    /**
     * Constructs the UI in the AWT event thread.
     */
    public void run() {

        final JFrame frame = new JFrame("AWTExprLayoutAndRender Sandbox");

        final JPanel content = new JPanel(new BorderLayout());
        frame.setContentPane(content);
        content.setPreferredSize(new Dimension(600, 400));




        UIUtilities.packAndCenter(frame);
    }

    /**
     * Executes the application.
     *
     * @param args command-line arguments
     */
    public static void main(final String... args) {

        SwingUtilities.invokeLater(new SwingTestHarness());
    }
}
