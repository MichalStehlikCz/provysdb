package com.provys.provysdb.sqlbuilder.impl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CodeIdentSimpleTest {

    @Test
    void getTest() {
        var codeIdent = CodeIdentSimple.of(":::");
        assertThat(codeIdent.get()).isEqualTo(":::");
        assertThat(codeIdent.get()).isEqualTo(":::");
    }

    @Test
    void useTest() {
        var codeIdent = CodeIdentSimple.of("--");
        var builder = new StringBuilder();
        codeIdent.use(builder);
        assertThat(builder.toString()).isEqualTo("--");
        builder.append("a");
        assertThat(codeIdent.get()).isEqualTo("--");
        codeIdent.use(builder);
        assertThat(builder.toString()).isEqualTo("--a--");
        builder.append("b");
        assertThat(codeIdent.get()).isEqualTo("--");
        codeIdent.use(builder);
        assertThat(builder.toString()).isEqualTo("--a--b--");
    }

    @Test
    void copyTest() {
        var codeIdent = CodeIdentSimple.of("xxxx");
        var copy = codeIdent.copy();
        assertThat(copy).isInstanceOf(CodeIdentSimple.class);
        assertThat(copy.get()).isEqualTo("xxxx");
    }
}