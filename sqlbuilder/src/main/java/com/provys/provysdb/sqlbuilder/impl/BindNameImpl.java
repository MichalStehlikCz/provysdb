package com.provys.provysdb.sqlbuilder.impl;

import com.provys.provysdb.sqlbuilder.BindName;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

public class BindNameImpl implements BindName {

    @Nonnull
    private static final Pattern PATTERN = Pattern.compile("(?:([A-Z][A-Z0-9_]*)|([0-9]*))");

    private final String name;

    @Nonnull
    private static String validate(String name) {
        var result = name.trim();
        if (!PATTERN.matcher(result).matches()) {
            throw new IllegalArgumentException("Invalid bind name " + name);
        }
        return result;
    }

    public BindNameImpl(String name) {
        this.name = validate(name);
    }

    @Nonnull
    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BindNameImpl bindName = (BindNameImpl) o;

        return name != null ? name.equals(bindName.name) : bindName.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "BindNameImpl{" +
                "name='" + name + '\'' +
                '}';
    }
}
