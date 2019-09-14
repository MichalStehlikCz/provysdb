package com.provys.provysdb.sqlbuilder.impl;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CodeBuilderImplTest {

    @Test
    void appendTest() {
        assertThat(new CodeBuilderImpl().append("abc").append("def").build()).isEqualTo("abcdef");
    }

    @Test
    void appendWrappedTest() {
        assertThat(new CodeBuilderImpl().setIdent("ab").appendWrapped("xyz\nwxd", 2).append("h")
                .build())
                .isEqualTo("abxyz\n    wxdh");
    }

    @Test
    void appendWrapped1Test() {
        assertThat(new CodeBuilderImpl().setIdent("ab").appendWrapped("xyz\nwxd").append("h").build())
                .isEqualTo("abxyz\n  wxdh");
    }

    @Test
    void appendLineTest() {
        assertThat(new CodeBuilderImpl().setIdent("ab").appendLine().setIdent("cde").appendLine().build())
                .isEqualTo("ab\ncde\n");
    }

    @Test
    void appendLine1Test() {
        assertThat(new CodeBuilderImpl().setIdent("ab").appendLine("xyz").setIdent("cde").appendLine("yy").build())
                .isEqualTo("abxyz\ncdeyy\n");
    }

    @Test
    void identBuilderTest() {
        assertThat(new CodeBuilderImpl().identBuilder()).isInstanceOf(CodeIdentBuilderImpl.class);
    }

    @Test
    void setIdentTest() {
        assertThat(new CodeBuilderImpl().appendLine("abc").setIdent(CodeIdentSimple.of("s")).appendLine("xyz").build())
                .isEqualTo("abc\nsxyz\n");
    }

    @Test
    void setIdent1Test() {
        assertThat(new CodeBuilderImpl().appendLine("abc").setIdent("prs").appendLine("xyz").build())
                .isEqualTo("abc\nprsxyz\n");
    }

    @Test
    void setIdent2Test() {
        assertThat(new CodeBuilderImpl().appendLine("abc").setIdent("s", 3).appendLine("xyz").build())
                .isEqualTo("abc\n  sxyz\n");
    }

    @Test
    void setIdent3Test() {
        assertThat(new CodeBuilderImpl().appendLine("abc").setIdent("A", "B").appendLine("xyz")
                .appendLine("uvw").build())
                .isEqualTo("abc\nAxyz\nBuvw\n");
    }

    @Test
    void setIdent4Test() {
        assertThat(new CodeBuilderImpl().appendLine("abc").setIdent("", ", ", 4).appendLine("xyz")
                .appendLine("uvw").build())
                .isEqualTo("abc\n    xyz\n  , uvw\n");
    }

    @Test
    void increasedIdentTest() {
        assertThat(new CodeBuilderImpl().setIdent("  ").appendLine("abc").increasedIdent(4).appendLine("xyz")
                .appendLine("uvw").build())
                .isEqualTo("  abc\n      xyz\n      uvw\n");
    }

    @Test
    void increasedIdent1Test() {
        assertThat(new CodeBuilderImpl().setIdent("  ").appendLine("abc").increasedIdent("x", 2)
                .appendLine("xyz").appendLine("uvw").build())
                .isEqualTo("  abc\n   xxyz\n   xuvw\n");
    }

    @Test
    void increasedIdent2Test() {
        assertThat(new CodeBuilderImpl().setIdent("  ").appendLine("abc")
                .increasedIdent(".", ":", 2).appendLine("xyz")
                .appendLine("uvw").build())
                .isEqualTo("  abc\n   .xyz\n   :uvw\n");
    }

    @Test
    void popIdentTest() {
        assertThat(new CodeBuilderImpl().appendLine("abc").setIdent("  ").appendLine("def").popIdent().appendLine("ghi")
                .build())
                .isEqualTo("abc\n  def\nghi\n");
    }
}