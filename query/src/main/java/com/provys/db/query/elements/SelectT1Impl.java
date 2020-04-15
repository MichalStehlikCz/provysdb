package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindVariableCollector;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Select statement with single columns.
 *
 * @param <T1> is type of the first and only column
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("SELECT1")
@JsonPropertyOrder({"COLUMN", "FROM", "WHERE", "PARENTCONTEXT"})
@Immutable
final class SelectT1Impl<T1> extends SelectTImpl implements SelectT1<T1> {

  @JsonProperty("COLUMN")
  private final SelectColumn<T1> column1;

  SelectT1Impl(SelectColumn<T1> column1,
      FromClause fromClause,
      @Nullable Condition whereClause,
      @Nullable FromContext parentContext,
      @Nullable BindMap bindMap) {
    super(fromClause, whereClause, parentContext, bindMap);
    this.column1 = (bindMap == null) ? column1 : column1.mapBinds(bindMap);
  }

  @JsonCreator
  SelectT1Impl(@JsonProperty("COLUMN") SelectColumn<T1> column1,
      @JsonProperty("FROM") FromClause fromClause,
      @JsonProperty("WHERE") @Nullable Condition whereClause,
      @JsonProperty("PARENTCONTEXT") @Nullable FromContext parentContext) {
    this(column1, fromClause, whereClause, parentContext, null);
  }

  @Override
  public SelectColumn<T1> getColumn1() {
    return column1;
  }

  @Override
  public Class<T1> getType1() {
    return column1.getType();
  }

  @Override
  public SelectClause getSelectClause() {
    return new SelectClauseColumns(List.of(column1));
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return new BindVariableCollector()
        .add(column1)
        .add(getFromClause())
        .add(getWhereClause())
        .getBindsByName()
        .values();
  }

  @Override
  public SelectT1<T1> mapBinds(BindMap bindMap) {
    return new SelectT1Impl<>(column1, getFromClause(), getWhereClause(), getParentContext(),
        bindMap);
  }

  @Override
  public void apply(QueryConsumer consumer) {
    consumer.select(column1, getFromClause(), getWhereClause());
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
    SelectT1Impl<?> selectT1 = (SelectT1Impl<?>) o;
    return column1.equals(selectT1.column1);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + column1.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SelectT1Impl{"
        + "column1=" + column1
        + ", " + super.toString() + '}';
  }
}
