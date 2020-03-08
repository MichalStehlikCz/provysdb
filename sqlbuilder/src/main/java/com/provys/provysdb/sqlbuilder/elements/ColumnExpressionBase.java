package com.provys.provysdb.sqlbuilder.elements;

import com.provys.provysdb.sqlbuilder.CodeBuilder;
import com.provys.provysdb.sqlbuilder.SelectExpressionBuilder;
import com.provys.provysdb.sqlbuilder.Identifier;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Used as basis for all "normal" ColumnExpression types.
 *
 * @param <T> is type of variable represented by column
 */
abstract class ColumnExpressionBase<T> implements SelectExpressionBuilder<T> {

  @Override
  public @Nullable Identifier getAlias() {
    return null;
  }

  @Override
  public Optional<Identifier> getOptAlias() {
    return Optional.empty();
  }

  @Override
  public SelectExpressionBuilder<T> as(Identifier newAlias) {
    return ColumnExpressionAliasWrapper.of(this, newAlias);
  }

  @Override
  public SelectExpressionBuilder<T> as(String newAlias) {
    return ColumnExpressionAliasWrapper.of(this, newAlias);
  }

  private static void appendAlias(CodeBuilder builder, Identifier alias) {
    builder.append(' ').append(alias.getText());
  }

  @Override
  public void addColumn(CodeBuilder builder) {
    appendExpression(builder);
    getOptAlias().ifPresent(alias -> appendAlias(builder, alias));
  }

  @Override
  public String toString() {
    return "ColumnExpressionBase{}";
  }
}
