package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.impl.LiteralVarchar;

/**
 * String literal token
 */
public class ParsedVarchar extends ParsedLiteralBase<String> {

    ParsedVarchar(int line, int pos, String value) {
        super(line, pos, LiteralVarchar.of(value));
    }

    @Override
    public void append(CodeBuilder builder) {
        builder.append('\'').append(getValue().replace("'", "''"));
    }

    @Override
    public String toString() {
        return "SqlString{} " + super.toString();
    }
}
