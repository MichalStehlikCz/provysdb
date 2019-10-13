package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.*;

import javax.annotation.Nonnull;
import java.math.BigInteger;
import java.util.*;

/**
 * Implementation of code builder - tool to build SQL text with formatting.
 *
 * @author stehlik
 */
class CodeBuilderImpl implements CodeBuilder {

    @Nonnull
    private final StringBuilder text;
    @Nonnull
    private final List<BindVariable> binds = new ArrayList<>(1);
    private boolean newLine = true;
    @Nonnull
    private CodeIdent ident = CodeIdentVoid.getInstance();
    @Nonnull
    private final Deque<CodeIdent> tempIdents = new ArrayDeque<>(5);

    /**
     * Default constructor for CodeBuilder.
     * Sets all fields to their default values.
     */
    CodeBuilderImpl() {
        this.text = new StringBuilder(100);
    }

    @Nonnull
    @Override
    public CodeBuilder append(String text) {
        if (newLine) {
            ident.use(this.text);
            newLine = false;
        }
        this.text.append(text);
        return this;
    }

    @Nonnull
    @Override
    public CodeBuilder append(char character) {
        return append(Character.toString(character));
    }

    @Nonnull
    @Override
    public CodeBuilder append(int number) {
        return append(Integer.toString(number));
    }

    @Nonnull
    @Override
    public CodeBuilder append(BigInteger number) {
        return append(number.toString());
    }

    @Nonnull
    @Override
    public CodeBuilder append(SqlIdentifier name) {
        return append(name.getText());
    }

    @Nonnull
    @Override
    public CodeBuilder append(SqlTableAlias alias) {
        return append(alias.getAlias());
    }

    @Nonnull
    @Override
    @SuppressWarnings("squid:S3457")
    public CodeBuilder appendWrapped(String text, int additionalIdent) {
        if (additionalIdent < -ident.get().length()) {
            throw new IllegalArgumentException("Negative ident for block cannot be bigger than current ident level");
        }
        boolean first = true;
        String tempIdent = null; // temporary ident; only used on second and consecutive lines
        for (var line : text.split("\n")) {
            if (first) {
                first = false;
            } else {
                if (tempIdent == null) {
                    tempIdent = String.format( "%1$-" + (ident.get().length() + additionalIdent + 1) + "s", "\n");
                }
                append(tempIdent);
            }
            append(line);
        }
        if (text.charAt(text.length() - 1) == '\n') {
            appendLine();
        }
        return this;
    }

    @Nonnull
    @Override
    public CodeBuilder appendWrapped(String text) {
        return appendWrapped(text, 0);
    }

    @Nonnull
    @Override
    public CodeBuilder appendLine() {
        if (this.newLine) {
            // if there is ident and line is empty, we must insert it to builder
            append("");
        }
        text.append('\n');
        this.newLine = true;
        return this;
    }

    @Nonnull
    @Override
    public CodeBuilder appendLine(String line) {
        append(line);
        return appendLine();
    }

    @Nonnull
    @Override
    public CodeIdentBuilder identBuilder() {
        return new CodeIdentBuilderImpl();
    }

    @Nonnull
    @Override
    public CodeBuilder setIdent(CodeIdent ident) {
        tempIdents.add(this.ident);
        // we create a copy of supplied ident to make sure it will not be changed externally
        this.ident = ident.copy();
        return this;
    }

    @Nonnull
    @Override
    public CodeBuilder setIdent(String ident) {
        return setIdent(CodeIdentSimple.of(ident));
    }

    @Nonnull
    @Override
    @SuppressWarnings("squid:S3457")
    public CodeBuilder setIdent(String ident, int chars) {
        if (chars < ident.length()) {
            throw new IllegalArgumentException("Ident length cannot be smaller than length of supplied ident prefix");
        }
        return setIdent(String.format("%1$" + chars + "s", ident));
    }

    @Nonnull
    @Override
    public CodeBuilder setIdent(String firstIdent, String ident) {
        return setIdent(CodeIdentFirst.of(firstIdent, ident));
    }

    @Nonnull
    @Override
    public CodeBuilder setIdent(String firstIdent, String ident, int chars) {
        if (chars < firstIdent.length()) {
            throw new IllegalArgumentException("Ident length cannot be smaller than length of supplied first line " +
                    "ident prefix");
        }
        if (chars < ident.length()) {
            throw new IllegalArgumentException("Ident length cannot be smaller than length of supplied ident prefix");
        }
        var format = "%1$" + chars + "s";
        return setIdent(String.format(format, firstIdent), String.format(format, ident));
    }

    @Nonnull
    @Override
    public CodeBuilder increasedIdent(int increaseBy) {
        return increasedIdent("", increaseBy);
    }

    @Nonnull
    @Override
    @SuppressWarnings("squid:S3457")
    public CodeBuilder increasedIdent(String ident, int increaseBy) {
        return setIdent(ident, this.ident.get().length() + increaseBy);
    }

    @Nonnull
    @Override
    public CodeBuilder increasedIdent(String firstIdent, String ident, int increaseBy) {
        return setIdent(firstIdent, ident, this.ident.get().length() + increaseBy);
    }

    @Nonnull
    @Override
    public CodeBuilder popIdent() {
        this.ident = tempIdents.pop();
        return this;
    }

    @Nonnull
    @Override
    public CodeBuilder addBind(BindVariable bind) {
        binds.add(Objects.requireNonNull(bind));
        return this;
    }

    @Nonnull
    @Override
    public String build() {
        return this.text.toString();
    }

    @Nonnull
    @Override
    public List<BindVariable> getBinds() {
        return Collections.unmodifiableList(binds);
    }
}
