package com.provys.db.querybuilder.elements;

import com.provys.db.querybuilder.SelectExpressionBuilder;
import com.provys.provysdb.sql.CodeBuilder;
import com.provys.provysdb.sql.SimpleName;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Used as basis for all "normal" ColumnExpression types.
 *
 * @param <T> is type of variable represented by column
 */
abstract class ColumnExpressionBase<T> implements SelectExpressionBuilder<T> {

  @Override
  public @Nullable SimpleName getAlias() {
    return null;
  }

  @Override
  public Optional<SimpleName> getOptAlias() {
    return Optional.empty();
  }

  @Override
  public SelectExpressionBuilder<T> as(SimpleName newAlias) {
    return ColumnExpressionAliasWrapper.of(this, newAlias);
  }

  @Override
  public SelectExpressionBuilder<T> as(String newAlias) {
    return ColumnExpressionAliasWrapper.of(this, newAlias);
  }

  private static void appendAlias(CodeBuilder builder, SimpleName alias) {
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
