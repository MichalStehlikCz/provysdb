package sqlparser.impl;

import com.provys.provysdb.sql.CodeBuilder;
import sqlparser.SpaceMode;
import sqlparser.SqlTokenType;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents single line comment in Sql code.
 */
final class ParsedSingleLineComment extends ParsedTokenBase {

  private final String text;

  ParsedSingleLineComment(int line, int pos, String text) {
    super(line, pos);
    this.text = text.stripTrailing();
  }

  @Override
  public SqlTokenType getTokenType() {
    return SqlTokenType.COMMENT;
  }

  @Override
  public SpaceMode spaceBefore() {
    return SpaceMode.FORCE;
  }

  @Override
  public SpaceMode spaceAfter() {
    return SpaceMode.FORCE_NONE;
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append("--").appendLine(text);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    ParsedSingleLineComment that = (ParsedSingleLineComment) o;
    return Objects.equals(text, that.text);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (text != null ? text.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ParsedSingleLineComment{"
        + "text='" + text + '\''
        + ", " + super.toString() + '}';
  }
}
