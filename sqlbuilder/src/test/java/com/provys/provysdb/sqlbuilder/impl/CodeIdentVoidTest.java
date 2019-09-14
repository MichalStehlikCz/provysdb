package com.provys.provysdb.sqlbuilder.impl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CodeIdentVoidTest {

    @Test
    void getTest() {
        var codeIdent = CodeIdentVoid.getInstance();
        assertThat(codeIdent.get()).isEmpty();
        assertThat(codeIdent.get()).isEmpty();
    }

    @Test
    void useTest() {
        var codeIdent = CodeIdentVoid.getInstance();
        var builder = new StringBuilder();
        codeIdent.use(builder);
        assertThat(builder.toString()).isEmpty();
        builder.append("a");
        assertThat(codeIdent.get()).isEmpty();
        codeIdent.use(builder);
        assertThat(builder.toString()).isEqualTo("a");
        builder.append("b");
        assertThat(codeIdent.get()).isEmpty();
        codeIdent.use(builder);
        assertThat(builder.toString()).isEqualTo("ab");
    }

    @Test
    void copyTest() {
        var codeIdent = CodeIdentVoid.getInstance();
        assertThat(codeIdent.copy()).isInstanceOf(CodeIdentVoid.class);
    }
}