package com.provys.provysdb.sqlparser.impl;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SqlFactory;
import com.provys.provysdb.sqlbuilder.SqlIdentifier;
import com.provys.provysdb.sqlparser.SpaceMode;
import com.provys.provysdb.sqlparser.SqlTokenType;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents name or keyword. Ordinary identifier is one that is not surrounded by double quotation
 * marks
 */
final class ParsedIdentifier extends ParsedTokenBase implements SqlIdentifier {

  private final SqlIdentifier identifier;

  ParsedIdentifier(int line, int pos, String name) {
    super(line, pos);
    this.identifier = SqlFactory.name(name);
  }

  ParsedIdentifier(int line, int pos, SqlIdentifier identifier) {
    super(line, pos);
    this.identifier = Objects.requireNonNull(identifier);
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
    return identifier.getText();
  }

  @Override
  public String getDbName() {
    return identifier.getDbName();
  }

  @Override
  public void addSql(CodeBuilder builder) {
    builder.append(identifier.getText());
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
    ParsedIdentifier that = (ParsedIdentifier) o;
    return Objects.equals(identifier, that.identifier);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (identifier != null ? identifier.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "ParsedIdentifier{"
        + "identifier=" + identifier
        + ", " + super.toString() + '}';
  }
}
