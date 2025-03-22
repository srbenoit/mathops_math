package dev.mathops.math.expr.render.awt;

import com.formdev.flatlaf.FlatLightLaf;
import dev.mathops.commons.log.Log;
import dev.mathops.commons.ui.UIUtilities;
import dev.mathops.commons.ui.layout.StackedBorderLayout;
import dev.mathops.math.expr.layout.ExpressionDisplayContext;
import dev.mathops.math.expr.structure.Expression;
import dev.mathops.math.expr.structure.VariableReference;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple test harness that builds an expression then lays it out and renders it to a panel.
 */
public final class SwingTestHarness implements Runnable, ActionListener {

    /** A command to render the variable reference. */
    private static final String VAR_REF_CMD = "VAR_REF";

    /** A field into which the user can enter a base font size. */
    private JTextField fontSize;

    /** A field into which the user can enter a variable specification. */
    private JTextField variableSpec;

    /** A field into which the user can enter index 1. */
    private JTextField variableIndex1;

    /** A field into which the user can enter index 2. */
    private JTextField variableIndex2;

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

        final JPanel content = new JPanel(new StackedBorderLayout());
        frame.setContentPane(content);
        content.setPreferredSize(new Dimension(600, 400));

        // Row 1: Variable reference
        final JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        final JLabel lbl1a = new JLabel("Base Font Size: ");
        this.fontSize = new JTextField(4);

        row1.add(lbl1a);
        row1.add(this.fontSize);
        content.add(row1, StackedBorderLayout.NORTH);

        // Row 2: Variable reference
        final JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEADING));

        final JLabel lbl2a = new JLabel("Variable Reference: ");
        this.variableSpec = new JTextField(20);

        final JLabel lbl2b = new JLabel("[");
        this.variableIndex1 = new JTextField(1);
        final JLabel lbl2c = new JLabel(", ");
        this.variableIndex2 = new JTextField(1);
        final JLabel lbl2d = new JLabel("]   ");

        final JButton btn2 = new JButton("Render");
        btn2.setActionCommand(VAR_REF_CMD);
        btn2.addActionListener(this);

        row2.add(lbl2a);
        row2.add(this.variableSpec);
        row2.add(lbl2b);
        row2.add(this.variableIndex1);
        row2.add(lbl2c);
        row2.add(this.variableIndex2);
        row2.add(lbl2d);
        row2.add(btn2);
        content.add(row2, StackedBorderLayout.NORTH);

        UIUtilities.packAndCenter(frame);
    }

    /**
     * Called when an action is invoked.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(final ActionEvent e) {

        final String cmd = e.getActionCommand();

        if (VAR_REF_CMD.equals(cmd)) {
            renderVariableRef();
        }
    }

    /**
     * Renders the variable reference.
     */
    private void renderVariableRef() {

        double size = 20.0;
        final String fontSizeText = this.fontSize.getText();
        if (fontSizeText != null && !fontSizeText.isBlank()) {
            try {
                size = Double.parseDouble(fontSizeText);
            } catch (final NumberFormatException ex) {
                Log.warning(ex);
            }
        }

        final String varSpecText = this.variableSpec.getText();
        if (!varSpecText.isBlank()) {
            int index1 = -1;
            final String index1Text = this.variableIndex1.getText();
            if (index1Text != null && !index1Text.isBlank()) {
                try {
                    index1 = Integer.parseInt(index1Text);
                } catch (final NumberFormatException ex) {
                    Log.warning(ex);
                }
            }

            int index2 = -1;
            final String index2Text = this.variableIndex2.getText();
            if (index2Text != null && !index2Text.isBlank()) {
                try {
                    index2 = Integer.parseInt(index2Text);
                } catch (final NumberFormatException ex) {
                    Log.warning(ex);
                }
            }

            final VariableReference ref = new VariableReference(varSpecText, index1, index2);
            final Expression expr = new Expression(ref);

            final ExpressionDisplayContext context = new ExpressionDisplayContext(size);

            final AWTExprLayoutAndRender renderer = new AWTExprLayoutAndRender();
            renderer.layout(context, expr);

            // TODO: Render and display the generated BufferedImage
        }
    }

    /**
     * Executes the application.
     *
     * @param args command-line arguments
     */
    public static void main(final String... args) {

        FlatLightLaf.setup();
        SwingUtilities.invokeLater(new SwingTestHarness());
    }
}
