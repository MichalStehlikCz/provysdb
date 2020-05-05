package com.provys.db.sqlquery.codebuilder;

import static org.assertj.core.api.Assertions.*;

import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SegmentedName;
import java.util.stream.Stream;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.jupiter.api.Test;
import java.math.BigInteger;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class CodeBuilderImplTest {

  @Test
  void appendTest() {
    assertThat(new CodeBuilderImpl().append("abc").append("def").build()).isEqualTo("abcdef");
  }

  @Test
  void append2Test() {
    assertThat(new CodeBuilderImpl().append('x').append('\n').build()).isEqualTo("x\n");
  }

  @Test
  void append3Test() {
    assertThat(new CodeBuilderImpl().append(123).build()).isEqualTo("123");
  }

  @Test
  void append4Test() {
    assertThat(new CodeBuilderImpl().append(BigInteger.valueOf(125874698)).build())
        .isEqualTo("125874698");
  }

  static Stream<@Nullable Object[]> appendNamePathTest() {
    return Stream.of(
        new Object[]{SegmentedName.valueOf("sys.v$session"), "sys.v$session"}
        , new Object[]{SegmentedName.valueOf("v$session"), "v$session"}
        , new Object[]{SegmentedName.valueOf("schema.\"Test\""), "schema.\"Test\""}
    );
  }

  @ParameterizedTest
  @MethodSource
  void appendNamePathTest(NamePath value, String result) {
    CodeBuilder builder = new TestCodeBuilder()
        .append(value);
    assertThat(builder.build()).isEqualTo(result);
    assertThat(builder.getBindsWithPos()).isEmpty();
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
    assertThat(
        new CodeBuilderImpl().setIdent("ab").appendLine().setIdent("cde").appendLine().build())
        .isEqualTo("ab\ncde\n");
  }

  @Test
  void appendLine1Test() {
    assertThat(
        new CodeBuilderImpl().setIdent("ab").appendLine("xyz").setIdent("cde").appendLine("yy")
            .build())
        .isEqualTo("abxyz\ncdeyy\n");
  }

  @Test
  void identBuilderTest() {
    assertThat(new CodeBuilderImpl().identBuilder()).isInstanceOf(CodeIdentBuilderImpl.class);
  }

  @Test
  void setIdentTest() {
    assertThat(
        new CodeBuilderImpl().appendLine("abc").setIdent(CodeIdentSimple.of("s")).appendLine("xyz")
            .build())
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

  /**
   * setIdent with length 0 should pass
   */
  @Test
  void setIdentLength0Test() {
    assertThat(new CodeBuilderImpl()
        .increasedIdent(", ", 3)
        .appendLine("abc")
        .setIdent("", 0)
        .appendLine("xyz")
        .build())
        .isEqualTo(" , abc\nxyz\n");
  }

  /**
   * setIdent with non-zero length should pass
   */
  @Test
  void setIdentLength1Test() {
    assertThat(new CodeBuilderImpl()
        .increasedIdent(", ", 3)
        .appendLine("abc")
        .setIdent("", 1)
        .appendLine("def")
        .setIdent("x", 1)
        .appendLine("ghi")
        .build())
        .isEqualTo(" , abc\n def\nxghi\n");
  }

  @Test
  void increasedIdentTest() {
    assertThat(
        new CodeBuilderImpl().setIdent("  ").appendLine("abc").increasedIdent(4).appendLine("xyz")
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
    assertThat(new CodeBuilderImpl().appendLine("abc").setIdent("  ").appendLine("def").popIdent()
        .appendLine("ghi")
        .build())
        .isEqualTo("abc\n  def\nghi\n");
  }

  @Test
  void popIdent2Test() {
    assertThat(new CodeBuilderImpl()
        .appendLine("abc")
        .setIdent("  ")
        .appendLine("def")
        .increasedIdent(2)
        .appendLine("ghi")
        .popIdent()
        .appendLine("jkl")
        .popIdent()
        .appendLine("mno")
        .build())
        .isEqualTo("abc\n  def\n    ghi\n  jkl\nmno\n");
  }
}