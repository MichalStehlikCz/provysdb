package sqlparser.impl;

import com.provys.provysdb.sql.CodeBuilder;
import com.provys.provysdb.sql.NamePath;
import com.provys.provysdb.sql.SimpleName;
import com.provys.provysdb.sqlfactory.Sql;
import sqlparser.SpaceMode;
import sqlparser.SqlTokenType;
import java.util.List;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents name or keyword. Ordinary identifier is one that is not surrounded by double quotation
 * marks
 */
final class ParsedName extends ParsedTokenBase implements SimpleName {

  private final SimpleName name;

  ParsedName(int line, int pos, String name) {
    super(line, pos);
    this.name = Sql.name(name);
  }

  ParsedName(int line, int pos, SimpleName name) {
    super(line, pos);
    this.name = Objects.requireNonNull(name);
  }

  @Override
  public SqlTokenType getTokenType() {
    return SqlTokenType.NAME;
  }

  @Override
  public SpaceMode spaceBefore() {
    return SpaceMode.NORMAL;
  }

  @Override
  public SpaceMode spaceAfter() {
    return SpaceMode.NORMAL;
  }

  @Override
  public String getText() {
    return name.getText();
  }

  @Override
  public List<String> getDbNames() {
    return name.getDbNames();
  }

  @Override
  public boolean match(NamePath other) {
    return name.match(other);
  }

  @Override
  public void append(CodeBuilder builder) {
    name.append(builder);
  }

  @Override
  public String getDbName() {
    return name.getDbName();
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
    ParsedName that = (ParsedName) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (name != null ? name.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ParsedName{"
        + "name=" + name
        + ", " + super.toString() + '}';
  }
}
