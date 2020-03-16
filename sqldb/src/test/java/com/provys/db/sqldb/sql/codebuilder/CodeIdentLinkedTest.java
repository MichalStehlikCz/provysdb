package com.provys.db.sqldb.sql.codebuilder;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CodeIdentLinkedTest {

  @Test
  void getTest() {
    var ident = CodeIdentSimple.of("aaa");
    var linkedIdent = CodeIdentSimple.of("bb");
    var codeIdent = CodeIdentLinked.of(ident, linkedIdent);
    assertThat(codeIdent.get()).isEqualTo("bbaaa");
    assertThat(codeIdent.get()).isEqualTo("bbaaa");
  }

  @Test
  void useTest() {
    var ident = CodeIdentFirst.of("aaa", "bb");
    var linkedIdent = CodeIdentFirst.of("1", "2");
    var codeIdent = CodeIdentLinked.of(ident, linkedIdent);
    var builder = new StringBuilder();
    codeIdent.use(builder);
    assertThat(builder.toString()).isEqualTo("1aaa");
    builder.append("x");
    assertThat(codeIdent.get()).isEqualTo("2bb");
    codeIdent.use(builder);
    assertThat(builder.toString()).isEqualTo("1aaax2bb");
    builder.append("y");
    assertThat(codeIdent.get()).isEqualTo("2bb");
    codeIdent.use(builder);
    assertThat(builder.toString()).isEqualTo("1aaax2bby2bb");
  }

  @Test
  void copyTest() {
    var ident = CodeIdentFirst.of("aaa", "bb");
    var linkedIdent = CodeIdentFirst.of("1", "2");
    var codeIdent = CodeIdentLinked.of(ident, linkedIdent);
    var copy = codeIdent.copy();
    assertThat(copy).isInstanceOf(CodeIdentLinked.class);
    assertThat(copy.get()).isEqualTo("1aaa");
    copy.use(new StringBuilder());
    assertThat(copy.get()).isEqualTo("2bb");
    assertThat(codeIdent.get()).isEqualTo("1aaa");
  }
}