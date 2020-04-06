package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provys.common.exception.InternalException;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindVariableCollector;
import com.provys.db.query.names.SimpleName;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Select clause based on columns.
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("COLUMNS")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlFromClause
@JsonSerialize(using = SelectClauseColumnsSerializer.class)
@JsonDeserialize(using = SelectClauseColumnsDeserializer.class)
final class SelectClauseColumns implements SelectClause {

  private final List<SelectColumn<?>> columns;

  /**
   * Create select clause, based on supplied columns.
   *
   * @param columns are columns select clause should be based on
   * @param bindMap can be used to map variables in columns
   */
  SelectClauseColumns(Collection<? extends SelectColumn<?>> columns,
      @Nullable BindMap bindMap) {
    if (bindMap == null) {
      this.columns = List.copyOf(columns);
    } else {
      this.columns = columns.stream()
          .map(column -> column.mapBinds(bindMap))
          .collect(Collectors.toUnmodifiableList());
    }
  }

  /**
   * Simplified constructor, without bind translation.
   *
   * @param columns is collection of columns in new clause
   */
  SelectClauseColumns(Collection<? extends SelectColumn<?>> columns) {
    this(columns, null);
  }

  /**
   * Value of field columns.
   *
   * @return value of field columns
   */
  public List<SelectColumn<?>> getColumns() {
    return columns;
  }

  @Override
  public @Nullable SelectColumn<?> getColumnByAlias(SimpleName alias) {
    SelectColumn<?> result = null;
    for (var column : columns) {
      if (alias.equals(column.getAlias())) {
        if (result != null) {
          throw new InternalException(
              "Alias " + alias + "does not uniquely identify column in " + this + "; columns "
                  + result + " and " + column + " both match");
        }
        result = column;
      }
    }
    return result;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    var collector = new BindVariableCollector();
    for (var column : columns) {
      collector.add(column);
    }
    return collector.getBinds();
  }

  @Override
  public SelectClause mapBinds(BindMap bindMap) {
    var newColumns = columns.stream()
        .map(column -> column.mapBinds(bindMap))
        .collect(Collectors.toList());
    if (newColumns.containsAll(columns)) {
      // one side comparison is sufficient, both lists have same number of items
      return this;
    }
    return new SelectClauseColumns(newColumns);
  }

  @Override
  public void apply(QueryConsumer consumer) {
    consumer.selectColumns(columns);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SelectClauseColumns that = (SelectClauseColumns) o;
    return columns.equals(that.columns);
  }

  @Override
  public int hashCode() {
    return columns.hashCode();
  }

  @Override
  public String toString() {
    return "SelectClauseColumns{"
        + "columns=" + columns
        + '}';
  }
}
