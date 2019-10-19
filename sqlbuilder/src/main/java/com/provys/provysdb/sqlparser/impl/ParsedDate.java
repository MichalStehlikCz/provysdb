package com.provys.provysdb.sqlparser.impl;

import com.provys.common.datatype.DtDate;
import com.provys.common.datatype.DtDateTime;
import com.provys.common.datatype.DtTimeS;
import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.LiteralT;

public class ParsedDate extends ParsedLiteralBase<DtDateTime> {

    /**
     * Create date literal from string value (originally inside quotes)
     *
     * @return parsed literal
     */
    static ParsedDate ofText(int line, int pos, String text) {
        if (text.length() == 10) {
            return new ParsedDate(line, pos, DtDateTime.ofDate(DtDate.parseIso(text)));
        }
    }

    ParsedDate(int line, int pos, LiteralT<DtDateTime> value) {
        super(line, pos, value);
    }

    @Override
    public void append(CodeBuilder builder) {
        builder.append("DATE'");
        if (getValue().getTime().equals(DtTimeS.zero())) {
            builder.append(getValue().getDate().toIso());
        } else {
            builder.append(getValue().toIso());
        }
        builder.append('\'');
    }
}
