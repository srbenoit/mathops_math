package dev.mathops.math.expr.structure;

import dev.mathops.commons.CoreConstants;
import dev.mathops.math.expr.layout.Bounds;
import dev.mathops.math.expr.layout.ExpressionDisplayContext;
import dev.mathops.math.expr.layout.ILayoutImpl;
import dev.mathops.math.expr.layout.VariableReferenceLayout;

/**
 * An immutable variable reference.
 */
public final class VariableReference extends AbstractExpressionNode {

    /**
     * The variable name.  This string uniquely identifies the variable, and is used in an evaluation context to refer
     * to the variable value.  It also encodes scripts and accents attached to the variable name that affects how it is
     * presented visually.
     *
     * <p>The syntax for a variable name is:
     * <pre>
     * %presuperscript_presubscript%varname$superscript_subscript$@[style]
     * </pre>
     * <p>
     * The [style] portion is a string that may include:
     * <ul>
     * <li>P to display the variable name in a "Plain" rather than "Italic" posture (default is Italic)</li>
     * <li>At most one of D, S, C to choose “Double-Struck”, “Script”, or “Calligraphic” font (default is Roman)</li>
     * <li>An optional B to choose bold-face (default is no boldface)</li>
     * <li>At most one of v, d, dd, p, pp, ppp, h, w, t, o, oo, u, uu to add “vector”, “dot”, “double dot”, “prime”,
     * “double prime”, “triple prime”, “hat”, “wide hat”, “tilde”, “overline” “double overline”, “underline”, or
     * “double underline” accent (default is no accent)</li>
     * </ul>
     * If none of these styles are to be applied; the "@" character and style string can be omitted
     *
     * <p>
     * The variable name, superscripts, and subscripts can include backslash-escapes to access non-standard
     * characters like Greek letters, etc.
     * </p>
     */
    private final String variableName;

    /** The parsed pre-superscript; an empty string if none. */
    private final String preSuperscript;

    /** The parsed pre-subscript; an empty string if none. */
    private final String preSubscript;

    /** The name portion (may not be empty). */
    private final String name;

    /** The parsed superscript; an empty string if none. */
    private final String superscript;

    /** The parsed subscript; an empty string if none. */
    private final String subscript;

    /** The font posture for the variable name. */
    private final EFontPosture posture;

    /** The font family for the variable name. */
    private final EFontFamily family;

    /** The font weight. */
    private final EFontWeight weight;

    /** The accent. */
    private final EVariableAccent accent;

    /** The first index (array entry or matrix row), where 1 is the first entry or row; -1 if no first index. */
    private final int firstIndex;

    /** The second index (matrix column), where 1 is the first column, -1 if no second index. */
    private final int secondIndex;

    /** Layout information. */
    private VariableReferenceLayout layout = null;

    /**
     * Constructs a new {@code VariableReference}.
     *
     * @param theVariableName the variable name
     * @throws IllegalArgumentException if the variable name is null or malformed
     */
    public VariableReference(final String theVariableName, final int theFirstIndex, final int theSecondIndex) {

        super();

        if (theVariableName == null) {
            throw new IllegalArgumentException("Variable name may not be null");
        }

        this.variableName = theVariableName;
        this.firstIndex = theFirstIndex < 0 ? -1 : theFirstIndex;
        this.secondIndex = theSecondIndex < 0 ? -1 : theSecondIndex;

        // Parse components

        final int prescriptStart = theVariableName.indexOf('%');
        final int nameStart;
        if (prescriptStart == -1) {
            // There are no prescripts
            this.preSuperscript = CoreConstants.EMPTY;
            this.preSubscript = CoreConstants.EMPTY;
            nameStart = 0;
        } else if (prescriptStart > 0) {
            throw new IllegalArgumentException("'%' to start prescripts must be first character in variable name.");
        } else {
            final int prescriptEnd = theVariableName.indexOf('%', prescriptStart + 1);

            if (prescriptEnd == -1) {
                throw new IllegalArgumentException("Missing closing '%' on prescripts.");
            } else {
                // Both delimiters for prescripts have been found
                final int underscore = theVariableName.indexOf('_', 1);
                if (underscore > 0 && underscore < prescriptEnd) {
                    // There are both pre-superscript and pre-subscript parts
                    this.preSuperscript = theVariableName.substring(1, underscore);
                    this.preSubscript = theVariableName.substring(underscore + 1, prescriptEnd);
                } else {
                    // Only pre-superscript
                    this.preSuperscript = theVariableName.substring(1, prescriptEnd);
                    this.preSubscript = CoreConstants.EMPTY;
                }
                nameStart = prescriptEnd + 1;
            }
        }

        final int at = theVariableName.indexOf('@', nameStart);
        String styles = CoreConstants.EMPTY;

        final int scriptStart = theVariableName.indexOf('$', nameStart);

        if (scriptStart == -1) {
            // There are no scripts
            this.superscript = CoreConstants.EMPTY;
            this.subscript = CoreConstants.EMPTY;
            if (at == -1) {
                // Name goes to end of variable name, there is no style string
                this.name = theVariableName.substring(nameStart);
            } else if (at < nameStart) {
                throw new IllegalArgumentException("'@' character not allowed in prescripts'.");
            } else {
                this.name = theVariableName.substring(nameStart, at);
                styles = theVariableName.substring(at + 1);
            }
        } else {
            final int scriptEnd = theVariableName.indexOf('$', scriptStart + 1);

            if (scriptStart < nameStart) {
                throw new IllegalArgumentException("'(' character not allowed in prescripts'.");
            } else if (scriptEnd == -1) {
                throw new IllegalArgumentException("Missing closing '$' on scripts.");
            }

            // Both delimiters for scripts have been found
            final int underscore = theVariableName.indexOf('_', scriptStart + 1);
            if (underscore > 0 && underscore < scriptEnd) {
                // There are both superscript and subscript parts
                this.superscript = theVariableName.substring(scriptStart + 1, underscore);
                this.subscript = theVariableName.substring(underscore + 1, scriptEnd);
            } else {
                // Only superscript
                this.superscript = theVariableName.substring(scriptStart + 1, scriptEnd);
                this.subscript = CoreConstants.EMPTY;
            }

            this.name = theVariableName.substring(nameStart, scriptStart);
            if (this.name.isBlank()) {
                throw new IllegalArgumentException("Name portion of variable cannot be blank.");
            }

            if (at > scriptEnd) {
                styles = theVariableName.substring(at + 1);
            }
        }

        if (styles.isBlank()) {
            this.posture = EFontPosture.ITALIC;
            this.family = EFontFamily.ROMAN;
            this.weight = EFontWeight.PLAIN;
            this.accent = EVariableAccent.NONE;

        } else {
            this.posture = styles.indexOf('U') == -1 ? EFontPosture.ITALIC : EFontPosture.UPRIGHT;
            this.weight = styles.indexOf('B') == -1 ? EFontWeight.BOLD : EFontWeight.PLAIN;

            if (styles.indexOf('D') == -1) {
                if (styles.indexOf('S') == -1) {
                    if (styles.indexOf('C') == -1) {
                        this.family = EFontFamily.ROMAN;
                    } else {
                        this.family = EFontFamily.CALLIGRAPHIC;
                    }
                } else {
                    this.family = EFontFamily.SCRIPT;
                    if (styles.indexOf('C') > -1) {
                        throw new IllegalArgumentException("Cannot specify both Script and Calligraphic fonts");
                    }
                }
            } else {
                this.family = EFontFamily.DOUBLE_STRUCK;
                if (styles.indexOf('C') > -1) {
                    throw new IllegalArgumentException("Cannot specify both Double-Struck and Calligraphic fonts");
                }
                if (styles.indexOf('S') > -1) {
                    throw new IllegalArgumentException("Cannot specify both Script and Calligraphic fonts");
                }
            }

            if (styles.contains("ppp")) {
                this.accent = EVariableAccent.TRIPLE_PRIME;
            } else if (!styles.contains("pp")) {
                this.accent = EVariableAccent.DOUBLE_PRIME;
            } else if (!styles.contains("p")) {
                this.accent = EVariableAccent.PRIME;
            } else if (!styles.contains("dd")) {
                this.accent = EVariableAccent.DOUBLE_DOT;
            } else if (!styles.contains("d")) {
                this.accent = EVariableAccent.DOT;
            } else if (!styles.contains("oo")) {
                this.accent = EVariableAccent.DOUBLE_OVERLINE;
            } else if (!styles.contains("o")) {
                this.accent = EVariableAccent.OVERLINE;
            } else if (!styles.contains("uu")) {
                this.accent = EVariableAccent.DOUBLE_UNDERLINE;
            } else if (!styles.contains("u")) {
                this.accent = EVariableAccent.UNDERLINE;
            } else if (!styles.contains("v")) {
                this.accent = EVariableAccent.VECTOR;
            } else if (!styles.contains("h")) {
                this.accent = EVariableAccent.HAT;
            } else if (!styles.contains("w")) {
                this.accent = EVariableAccent.WIDE_HAT;
            } else if (!styles.contains("t")) {
                this.accent = EVariableAccent.TILDE;
            } else {
                this.accent = EVariableAccent.NONE;
            }
        }
    }

    /**
     * Gets the full variable name (which encodes font style, scrips, and accents, but not indexes.  This is the value
     * used to look up the variable's value in an evaluation context.
     *
     * @return the variable name
     */
    public String getVariableName() {

        return this.variableName;
    }

    /**
     * Gets the parsed pre-superscript.
     *
     * @return the pre-superscript; an empty string if none
     */
    public String getPreSuperscript() {

        return this.preSuperscript;
    }

    /**
     * Gets the parsed pre-subscript.
     *
     * @return the pre-subscript; an empty string if none
     */
    public String getPreSubscript() {

        return this.preSubscript;
    }

    /**
     * Gets the variable name (excluding any scripts or accents).
     *
     * @return the name (may not be empty)
     */
    public String getName() {

        return this.name;
    }

    /**
     * Gets the parsed superscript.
     *
     * @return the superscript; an empty string if none
     */
    public String getSuperscript() {

        return this.superscript;
    }

    /**
     * Gets the parsed subscript.
     *
     * @return the parsed subscript; an empty string if none
     */
    public String getSubscript() {

        return this.subscript;
    }

    /**
     * Gets the font posture for the variable name.
     *
     * @return the font posture
     */
    public EFontPosture getPosture() {

        return this.posture;
    }

    /**
     * Gets the font family for the variable name.
     *
     * @return the font family
     */
    public EFontFamily getFamily() {

        return this.family;
    }

    /**
     * Gets the font weight.
     *
     * @return the font weight
     */
    public EFontWeight getWeight() {

        return this.weight;
    }

    /**
     * Gets the accent.
     *
     * @return the accent (EVariableAccent.NONE if none)
     */
    public EVariableAccent getAccent() {

        return this.accent;
    }

    /**
     * Gets the first index (array entry or matrix row).
     *
     * @return the first index, where 1 is the first entry or row; -1 if no first index.
     */
    public int getFirstIndex() {

        return this.firstIndex;
    }

    /**
     * Gets the second index (matrix column).
     *
     * @return the second index, where 1 is the first column, -1 if no second index.
     */
    public int getSecondIndex() {

        return this.secondIndex;
    }

    /**
     * Gets the layout information.
     *
     * @return the layout information ({@code null} if this object has never been laid out)
     */
    public VariableReferenceLayout getLayout() {

        return this.layout;
    }

    /**
     * Gets the total number of tokens in this node.  The cursor and selection bounds exist between tokens, or before
     * the first token or after the last token.
     *
     * @return the number of tokens
     */
    public int getNumTokens() {

        int count = this.preSuperscript.length() + this.preSubscript.length() + this.name.length() +
                    this.superscript.length() + this.subscript.length();

        if (this.firstIndex > -1) {
            count += 2 + numDigits(this.firstIndex); // Brackets around index(es)
            if (this.secondIndex > -1) {
                count += 1 + numDigits(this.secondIndex); // Comma between indexes
            }
        }

        return count;
    }

    /**
     * Returns the number of digits in a non-negative index.
     *
     * @param index the index
     * @return the number of digits
     */
    private static int numDigits(final int index) {

        return index < 10 ? 1 : index < 100 ? 2 : index < 1000 ? 3 : index < 10000 ? 4 : index < 100000 ? 5 :
                index < 1000000 ? 6 : index < 10000000 ? 7 : index < 100000000 ? 8 : index < 1000000000 ? 9 : 10;
    }

    /**
     * Performs layout of a node using a provided implementation.
     *
     * @param context the display context
     * @param impl    the layout implementation
     */
    public void performLayout(final ExpressionDisplayContext context, final ILayoutImpl impl) {

        if (this.layout == null) {
            this.layout = new VariableReferenceLayout();
        }

        impl.layoutVariableReference(context, this, this.layout);
    }

    /**
     * Gets the overall bounds of the node.
     *
     * @return the bounds; {@code null} if the node has not been laid out
     */
    public Bounds getBounds() {

        return this.layout == null ? null : this.layout.overallBounds;
    }
}
