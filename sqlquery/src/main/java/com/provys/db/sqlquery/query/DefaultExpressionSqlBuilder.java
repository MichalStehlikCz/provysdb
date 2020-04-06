package com.provys.db.sqlquery.query;

import com.provys.common.exception.InternalException;
import com.provys.db.query.elements.Expression;
import com.provys.db.query.elements.ExpressionBind;
import com.provys.db.query.elements.ExpressionColumn;
import com.provys.db.query.elements.ExpressionFunction;
import com.provys.db.query.elements.Literal;
import com.provys.db.query.elements.LiteralNVarchar;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.MonotonicNonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public class DefaultExpressionSqlBuilder implements ExpressionSqlBuilder<SqlBuilder<?>> {

  private static final DefaultExpressionSqlBuilder DEFAULT = new DefaultExpressionSqlBuilder(
      ExpressionBindSqlBuilder.getInstance(),
      ExpressionColumnSqlBuilder.getInstance(),
      ExpressionFunctionSqlBuilder.getInstance(),
      LiteralSqlBuilder.getInstance(),
      LiteralNVarcharSqlBuilder.getInstance()
  );

  /**
   * Access default instance of this builder.
   *
   * @return singleton instance
   */
  public static DefaultExpressionSqlBuilder getDefault() {
    return DEFAULT;
  }

  private final ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionBind<?>>
      bindBuilder;
  private final ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionColumn<?>>
      columnBuilder;
  private final ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionFunction<?>>
      functionBuilder;
  private final ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super Literal<?>> literalBuilder;
  private final ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super LiteralNVarchar>
      literalNVarcharBuilder;

  public DefaultExpressionSqlBuilder(
      ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionBind<?>> bindBuilder,
      ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionColumn<?>> columnBuilder,
      ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionFunction<?>>
          functionBuilder,
      ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super Literal<?>> literalBuilder,
      ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super LiteralNVarchar>
          literalNVarcharBuilder) {
    this.bindBuilder = bindBuilder;
    this.columnBuilder = columnBuilder;
    this.functionBuilder = functionBuilder;
    this.literalBuilder = literalBuilder;
    this.literalNVarcharBuilder = literalNVarcharBuilder;
  }

  /**
   * Value of field bindBuilder.
   *
   * @return value of field bindBuilder
   */
  public ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionBind<?>> getBindBuilder() {
    return bindBuilder;
  }

  /**
   * Value of field columnBuilder.
   *
   * @return value of field columnBuilder
   */
  public ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionColumn<?>> getColumnBuilder() {
    return columnBuilder;
  }

  /**
   * Value of field functionBuilder.
   *
   * @return value of field functionBuilder
   */
  public ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionFunction<?>> getFunctionBuilder() {
    return functionBuilder;
  }

  /**
   * Value of field literalBuilder.
   *
   * @return value of field literalBuilder
   */
  public ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super Literal<?>> getLiteralBuilder() {
    return literalBuilder;
  }

  /**
   * Value of field literalNVarcharBuilder.
   *
   * @return value of field literalNVarcharBuilder
   */
  public ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super LiteralNVarchar> getLiteralNVarcharBuilder() {
    return literalNVarcharBuilder;
  }

  @Override
  public void append(SqlBuilder<?> sqlBuilder, Expression<?> expression) {
    if (expression instanceof ExpressionBind) {
      bindBuilder.append(sqlBuilder, (ExpressionBind<?>) expression);
    } else if (expression instanceof ExpressionColumn) {
      columnBuilder.append(sqlBuilder, (ExpressionColumn<?>) expression);
    } else if (expression instanceof ExpressionFunction) {
      functionBuilder.append(sqlBuilder, (ExpressionFunction<?>) expression);
    } else if (expression instanceof Literal) {
      literalBuilder.append(sqlBuilder, (Literal<?>) expression);
    } else if (expression instanceof LiteralNVarchar) {
      literalNVarcharBuilder.append(sqlBuilder, (LiteralNVarchar) expression);
    } else {
      throw new InternalException("Expression " + expression
          + " not supported by ExpressionSqlBuilder");
    }
  }

  /**
   * Builder for default expresion sql builder. If some partial builders are not specified, build
   * might either fail or use default builders.
   */
  public static class Builder {

    private @MonotonicNonNull
    ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionBind<?>> bindBuilder;
    private @MonotonicNonNull
    ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionColumn<?>> columnBuilder;
    private @MonotonicNonNull
    ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionFunction<?>> functionBuilder;
    private @MonotonicNonNull ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super Literal<?>>
        literalBuilder;
    private @MonotonicNonNull
    ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super LiteralNVarchar> literalNVarcharBuilder;

    /**
     * Value of field bindBuilder.
     *
     * @return value of field bindBuilder
     */
    public @Nullable ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionBind<?>>
    getBindBuilder() {
      return bindBuilder;
    }

    /**
     * Set value of field bindBuilder
     *
     * @param bindBuilder is new value to be set
     * @return self to enable chaining
     */
    public Builder setBindBuilder(
        ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionBind<?>> bindBuilder) {
      this.bindBuilder = bindBuilder;
      return this;
    }

    /**
     * Value of field columnBuilder.
     *
     * @return value of field columnBuilder
     */
    public @Nullable ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionColumn<?>>
    getColumnBuilder() {
      return columnBuilder;
    }

    /**
     * Set value of field columnBuilder
     *
     * @param columnBuilder is new value to be set
     * @return self to enable chaining
     */
    public Builder setColumnBuilder(
        ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionColumn<?>>
            columnBuilder) {
      this.columnBuilder = columnBuilder;
      return this;
    }

    /**
     * Value of field functionBuilder.
     *
     * @return value of field functionBuilder
     */
    public @Nullable ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionFunction<?>>
    getFunctionBuilder() {
      return functionBuilder;
    }

    /**
     * Set value of field functionBuilder
     *
     * @param functionBuilder is new value to be set
     * @return self to enable chaining
     */
    public Builder setFunctionBuilder(
        ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super ExpressionFunction<?>>
            functionBuilder) {
      this.functionBuilder = functionBuilder;
      return this;
    }

    /**
     * Value of field literalBuilder.
     *
     * @return value of field literalBuilder
     */
    public @Nullable ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super Literal<?>>
    getLiteralBuilder() {
      return literalBuilder;
    }

    /**
     * Set value of field literalBuilder
     *
     * @param literalBuilder is new value to be set
     * @return self to enable chaining
     */
    public Builder setLiteralBuilder(
        ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super Literal<?>> literalBuilder) {
      this.literalBuilder = literalBuilder;
      return this;
    }

    /**
     * Value of field literalNVarcharBuilder.
     *
     * @return value of field literalNVarcharBuilder
     */
    public @Nullable ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super LiteralNVarchar>
    getLiteralNVarcharBuilder() {
      return literalNVarcharBuilder;
    }

    /**
     * Set value of field literalNVarcharBuilder
     *
     * @param literalNVarcharBuilder is new value to be set
     * @return self to enable chaining
     */
    public Builder setLiteralNVarcharBuilder(
        ExpressionTypeSqlBuilder<? super SqlBuilder<?>, ? super LiteralNVarchar>
            literalNVarcharBuilder) {
      this.literalNVarcharBuilder = literalNVarcharBuilder;
      return this;
    }

    public DefaultExpressionSqlBuilder build(boolean useDefaults) {
      if (!useDefaults) {
        Objects.requireNonNull(bindBuilder, "Bind builder not specified");
        Objects.requireNonNull(columnBuilder, "Column builder not specified");
        Objects.requireNonNull(functionBuilder, "Function builder not specified");
        Objects.requireNonNull(literalBuilder, "Literal builder not specified");
        Objects.requireNonNull(literalNVarcharBuilder, "Literal NVarchar builder not specified");
      }
      if ((bindBuilder == null) && (columnBuilder == null) && (functionBuilder == null)
          && (literalBuilder == null) && (literalNVarcharBuilder == null)) {
        return getDefault();
      }
      var result = new DefaultExpressionSqlBuilder(
          (bindBuilder == null) ? getDefault().getBindBuilder() : bindBuilder,
          (columnBuilder == null) ? getDefault().getColumnBuilder() : columnBuilder,
          (functionBuilder == null) ? getDefault().getFunctionBuilder() : functionBuilder,
          (literalBuilder == null) ? getDefault().getLiteralBuilder() : literalBuilder,
          (literalNVarcharBuilder == null) ? getDefault().getLiteralNVarcharBuilder()
              : literalNVarcharBuilder
      );
      if (result.equals(getDefault())) {
        return getDefault();
      }
      return result;
    }
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DefaultExpressionSqlBuilder that = (DefaultExpressionSqlBuilder) o;
    return bindBuilder.equals(that.bindBuilder)
        && columnBuilder.equals(that.columnBuilder)
        && functionBuilder.equals(that.functionBuilder)
        && literalBuilder.equals(that.literalBuilder)
        && literalNVarcharBuilder.equals(that.literalNVarcharBuilder);
  }

  @Override
  public int hashCode() {
    int result = bindBuilder.hashCode();
    result = 31 * result + columnBuilder.hashCode();
    result = 31 * result + functionBuilder.hashCode();
    result = 31 * result + literalBuilder.hashCode();
    result = 31 * result + literalNVarcharBuilder.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "DefaultExpressionSqlBuilder{"
        + "bindBuilder=" + bindBuilder
        + ", columnBuilder=" + columnBuilder
        + ", functionBuilder=" + functionBuilder
        + ", literalBuilder=" + literalBuilder
        + ", literalNVarcharBuilder=" + literalNVarcharBuilder
        + '}';
  }
}
