package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.CodeIdent;

import javax.annotation.Nonnull;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implementation of code builder - tool to build SQL text with formatting.
 *
 * @author stehlik
 */
class CodeBuilderImpl implements CodeBuilder {

    @Nonnull
    private final StringBuilder text;
    private boolean newLine = false;
    @Nonnull
    private IdentStatus ident = new IdentStatus();
    @Nonnull
    private final Deque<IdentStatus> tempIdents = new ArrayDeque<>(5);

    /**
     * Default constructor for CodeBuilder.
     * Sets all fields to their default values.
     */
    public CodeBuilderImpl() {
        text = new StringBuilder(100);
    }

    @Override
    public CodeBuilder append(String text) {
        if (newLine) {
            ident.getIdent().use(this.text);
            newLine = false;
        }
        this.text.append(text);
        return this;
    }

    @Override
    public CodeBuilder appendWrapped(String text, int additionalIdent) {
        if (additionalIdent < -ident.getIdent().get().length()) {
            throw new IllegalArgumentException("Negative ident for block cannot be bigger than current ident level");
        }
        boolean first = true;
        String tempIdent = null; // temporary ident; only used on second and consecutive lines
        for (var line : text.split("\n")) {
            if (first) {
                first = false;
            } else {
                if (tempIdent == null) {
                    var format = "%-" + (ident.getIdent().get().length() + additionalIdent + 1) + "s";
                    tempIdent = String.format(format, "\n");
                }
                append(tempIdent);
            }
            append(line);
        }
        return this;
    }

    @Override
    public CodeBuilder appendWrapped(String text) {
        return appendWrapped(text, 0);
    }

    @Override
    public CodeBuilder appendLine() {
        newLine = true;
        return this;
    }

    @Override
    public CodeBuilder appendLine(String line) {
        append(line);
        return appendLine();
    }

    @Override
    public CodeBuilder increaseIdent(int chars) {
        return increaseIdent(" ", chars);
    }

    @Override
    public CodeBuilder decreaseIdent(int chars) {
        if (chars < 0) {
            throw new DecreaseByNegativeException();
        }
        if (chars>this.ident.getIdent().length()) {
            throw new NegativeIdentLengthException();
        }
        setIdent(this.ident.getIdent().substring(0
                , this.ident.getIdent().length() - chars));
        return this;
    }

    @Override
    public CodeBuilder decreaseIdentTo(int chars) {
        if (chars < 0) {
            throw new IllegalArgumentException("Negative ident level required " + chars);
        }
        if (chars > this.ident.getIdent().length()) {
            throw new IllegalArgumentException("Ident level set by decrease ident cannot be higher tan current " +
                    "ident level (old " + this.ident.getIdent().length() + ", new " + chars + ")");
        }
        setIdent(this.ident.getIdent().substring(0, chars));
        return this;
    }

    private String getIdent() {
        return this.ident.getIdent();
    }

    @Override
    public CodeBuilder setIdent(int chars) {
        setIdent("");
        return increaseIdent(chars);
    }

    private CodeBuilder setIdent(IdentStatus ident) {
        this.ident = ident;
        return this;
    }

    @Override
    public CodeBuilder setIdent(String ident) {
        this.ident = new IdentStatus(ident);
        return this;
    }

    @Override
    public CodeBuilder setIdent(String ident, int chars) {
        if (ident.length() > chars) {
            throw new SuppliedLengthTooShortException();
        }
        return setIdent(String.format("%1$" + chars + "s", ident));
    }

    @Override
    public CodeBuilder setIdent(String firstIdent, String regularIdent) {
        this.ident = new IdentStatus(firstIdent, regularIdent);
        return this;
    }

    @Override
    public CodeBuilder setIdent(String firstIdent, String regularIdent
            , int chars) {
        if ((firstIdent.length() > chars) | (regularIdent.length() > chars)) {
            throw new SuppliedLengthTooShortException();
        }
        this.setIdent(String.format("%1$" + chars + "s", regularIdent)
                , String.format("%1$" + chars + "s", firstIdent));
        return this;
    }

    private CodeBuilder setIdent(String firstIdent, String regularIdent
            , int chars, CombinationType combinationType) {
        if ((firstIdent.length() > chars) | (regularIdent.length() > chars)) {
            throw new SuppliedLengthTooShortException();
        }
        this.ident = new IdentStatus(
                String.format("%1$" + chars + "s", regularIdent)
                , String.format("%1$" + chars + "s", firstIdent)
                , combinationType);
        return this;
    }

    private String getFirstIdent() {
        return this.ident.getFirstIdent();
    }

    /**
     * @param firstIdent the firstIdent to set
     */
    public void setFirstIdent(String firstIdent) {
        this.ident.setFirstIdent(firstIdent);
    }

    @Override
    public CodeBuilder appendIdent(String text) {
        setIdent(getIdent() + text);
        return this;
    }

    @Override
    public CodeBuilder appendIdent(String firstIdent, String regularIdent) {
        // SetIdent cancels first ident - thus, we have to save first ident to
        // local variable
        setIdent(getIdent() + firstIdent, getIdent() + regularIdent);
        return this;
    }

    @Override
    public CodeBuilder increaseIdent(String ident, int increaseBy) {
        if (increaseBy < 0) {
            throw new IncreaseByNegativeException();
        }
        setIdent(ident, getIdent().length()+increaseBy);
        return this;
    }

    @Override
    public CodeBuilder increaseIdent(String firstIdent, String regularIdent
            , int increaseBy) {
        if (increaseBy < 0) {
            throw new IncreaseByNegativeException();
        }
        setIdent(firstIdent, regularIdent, getIdent().length()+increaseBy);
        return this;
    }

    /**
     * Put current ident on temporary idents stack.
     * Note that it should never be used without subsequent replacement of
     * ident
     */
    private void putTempIdent() {
        tempIdents.push(this.ident);
    }

    @Override
    public CodeBuilder setTempIdent(String ident) {
        putTempIdent();
        return setIdent(ident);
    }

    @Override
    public CodeBuilder setTempIdent(String firstIdent, String regularIdent) {
        putTempIdent();
        return setIdent(firstIdent, regularIdent);
    }

    @Override
    public CodeBuilder appendTempIdent(String text) {
        putTempIdent();
        return appendIdent(text);
    }

    @Override
    public CodeBuilder appendTempIdent(String firstIdent, String regularIdent) {
        putTempIdent();
        return appendIdent(firstIdent, regularIdent);
    }

    @Override
    public CodeBuilder increaseTempIdent(int increaseBy) {
        return increaseTempIdent("", increaseBy);
    }

    @Override
    public CodeBuilder increaseTempIdent(String ident, int increaseBy) {
        if (increaseBy < 0) {
            throw new IncreaseByNegativeException();
        }
        putTempIdent();
        setIdent(ident, getIdent().length()+increaseBy);
        return this;
    }

    private CodeBuilder increaseTempIdent(String firstIdent, String regularIdent
            , int increaseBy, CombinationType combinationType) {
        if (increaseBy < 0) {
            throw new IncreaseByNegativeException();
        }
        putTempIdent();
        setIdent(firstIdent, regularIdent, getIdent().length()+increaseBy
                , combinationType);
        return this;
    }

    @Override
    public CodeBuilder increaseTempIdent(String firstIdent, String regularIdent
            , int increaseBy) {
        return increaseTempIdent(firstIdent, regularIdent, increaseBy
                , CombinationType.NONE);
    }

    @Override
    public CodeBuilder increaseTempIdentAnd() {
        if (this.ident.getCombinationType() == CombinationType.AND) {
            this.ident.increaseTempLevel();
        } else {
            if (this.ident.getCombinationType() == CombinationType.OR) {
                appendLine("(");
                this.ident.setAddBracket(true);
            }
            increaseTempIdent("", "AND", 2, CombinationType.AND);
        }
        return this;
    }

    @Override
    public CodeBuilder increaseTempIdentOr() {
        if (this.ident.getCombinationType() == CombinationType.OR) {
            this.ident.increaseTempLevel();
        } else {
            if (this.ident.getCombinationType() == CombinationType.AND) {
                appendLine("(");
                this.ident.setAddBracket(true);
            }
            increaseTempIdent("", "OR", 2, CombinationType.OR);
        }
        return this;
    }

    @Override
    public CodeBuilder removeTempIdent() {
        if (this.ident.getTempLevel()>0) {
            this.ident.decreaseTempLevel();
        } else {
            setIdent(tempIdents.pop());
            if (this.ident.isAddBracket()) {
                appendLine(")");
                this.ident.setAddBracket(false);
            }
        }
        return this;
    }

    @Override
    public String build() {
        return this.text.toString();
    }

    private enum CombinationType{NONE, AND, OR}

    /**
     * Holds identation state of CodeBuilder.
     */
    private static class IdentStatus {
        @Nonnull
        private final CodeIdent ident;
        private int tempLevel = 0;

        IdentStatus() {
            this.ident = CodeIdentVoid.getInstance();
        }

        IdentStatus(CodeIdent ident) {
            this.ident = ident;
        }

        /**
         * @return the ident
         */
        @Nonnull
        CodeIdent getIdent() {
            return ident;
        }

        /**
         * @return the tempLevel
         */
        int getTempLevel() {
            return tempLevel;
        }

        /**
         * Increases temporary level by one.
         */
        void increaseTempLevel() {
            this.tempLevel++;
        }

        /**
         * Decreases temporary level by one.
         */
        boolean decreaseTempLevel() {
            return (0 == this.tempLevel--);
        }
    }
}
