package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provys.common.types.ProvysClassDeserializer;
import com.provys.common.types.ProvysClassSerializer;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Expression based on column or property from one of the sources.
 *
 * @param <T> is type of value expression yields
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("COLUMN")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from Expression
public final class ExpressionColumn<T> implements Expression<T> {

  @JsonProperty("TABLE")
  private final @Nullable NamePath table;
  @JsonProperty("COLUMN")
  private final SimpleName column;
  @JsonProperty("TYPE")
  @JsonSerialize(using = ProvysClassSerializer.class)
  private final Class<T> type;

  /**
   * Create column expression, based on specified source (identified by alias), column name and
   * type. Expression is validated against supplied context.
   *
   * @param table is alias identifying source
   * @param column is name of column, evaluated in context of source
   * @param type is type that given expression should yield
   * @param fromContext is context in which sources are evaluated; if left empty, no validation is
   *                   performed
   */
  public ExpressionColumn(@Nullable NamePath table, SimpleName column, Class<T> type,
      @Nullable FromContext fromContext) {
    if ((table != null) && (fromContext != null)) {
      var fromElement = fromContext.getFromElement(table);
      if (fromElement != null) {
        // we found from element - we want to verify column against that element and use its alias
        fromElement.validateColumn(column, type);
        this.table = fromContext.getDefaultAlias(fromElement);
      } else {
        // probably alias pointing to outside context, use as it was specified
        this.table = table;
      }
    } else {
      // no table or no context, use alias as originally specified
      this.table = table;
    }
    this.column = column;
    this.type = type;
  }

  /**
   * Create column expression, based on specified source (identified by alias), column name and
   * type. Context is not supplied, thus bno validation is performed.
   *
   * @param table is alias identifying source
   * @param column is name of column, evaluated in context of source
   * @param type is type that given expression should yield
   */
  @JsonCreator
  public ExpressionColumn(@JsonProperty("TABLE") @Nullable NamePath table,
      @JsonProperty("COLUMN") SimpleName column,
      @JsonProperty("TYPE") @JsonDeserialize(using = ProvysClassDeserializer.class) Class<T> type) {
    this(table, column, type, null);
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return Collections.emptyList();
  }

  @Override
  public Expression<T> mapBinds(BindMap bindMap) {
    return this;
  }

  @Override
  public Class<T> getType() {
    return type;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ExpressionColumn<?> that = (ExpressionColumn<?>) o;
    return Objects.equals(table, that.table)
        && column.equals(that.column)
        && (type == that.type);
  }

  @Override
  public int hashCode() {
    int result = table != null ? table.hashCode() : 0;
    result = 31 * result + column.hashCode();
    result = 31 * result + type.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ExpressionColumn{"
        + "table=" + table
        + ", column=" + column
        + ", type=" + type
        + '}';
  }
}
