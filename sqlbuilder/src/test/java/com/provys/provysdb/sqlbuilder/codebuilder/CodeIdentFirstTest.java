package com.provys.provysdb.sqlbuilder.codebuilder;

import com.provys.provysdb.sqlbuilder.codebuilder.CodeIdentFirst;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CodeIdentFirstTest {

  @Test
  void getTest() {
    var codeIdent = CodeIdentFirst.of(":::", "-");
    assertThat(codeIdent.get()).isEqualTo(":::");
    assertThat(codeIdent.get()).isEqualTo(":::");
  }

  @Test
  void useTest() {
    var codeIdent = CodeIdentFirst.of("*", "///");
    var builder = new StringBuilder();
    assertThat(codeIdent.get()).isEqualTo("*");
    codeIdent.use(builder);
    assertThat(builder.toString()).isEqualTo("*");
    builder.append("a");
    assertThat(codeIdent.get()).isEqualTo("///");
    codeIdent.use(builder);
    assertThat(builder.toString()).isEqualTo("*a///");
    builder.append("b");
    assertThat(codeIdent.get()).isEqualTo("///");
    codeIdent.use(builder);
    assertThat(builder.toString()).isEqualTo("*a///b///");
  }

  @Test
  void copyTest() {
    var codeIdent = CodeIdentFirst.of("xxxx", "yyy");
    var copy = codeIdent.copy();
    assertThat(copy).isInstanceOf(CodeIdentFirst.class);
    assertThat(copy.get()).isEqualTo("xxxx");
    copy.use(new StringBuilder());
    assertThat(copy.get()).isEqualTo("yyy");
    assertThat(codeIdent.get()).isEqualTo("xxxx");
  }
}