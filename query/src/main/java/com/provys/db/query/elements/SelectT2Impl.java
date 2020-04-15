package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindVariableCollector;
import java.util.Collection;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Select statement with two columns.
 *
 * @param <T1> is type of the first column
 * @param <T2> is the type of second column
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("SELECT2")
@JsonPropertyOrder({"COLUMN1", "COLUMN2", "FROM", "WHERE", "PARENTCONTEXT"})
@Immutable
final class SelectT2Impl<T1, T2> extends SelectTImpl implements SelectT2<T1, T2> {

  @JsonProperty("COLUMN1")
  private final SelectColumn<T1> column1;
  @JsonProperty("COLUMN2")
  private final SelectColumn<T2> column2;

  SelectT2Impl(SelectColumn<T1> column1,
      SelectColumn<T2> column2,
      FromClause fromClause,
      @Nullable Condition whereClause,
      @Nullable FromContext parentContext,
      @Nullable BindMap bindMap) {
    super(fromClause, whereClause, parentContext, bindMap);
    this.column1 = (bindMap == null) ? column1 : column1.mapBinds(bindMap);
    this.column2 = (bindMap == null) ? column2 : column2.mapBinds(bindMap);
  }

  @JsonCreator
  SelectT2Impl(@JsonProperty("COLUMN1") SelectColumn<T1> column1,
      @JsonProperty("COLUMN2") SelectColumn<T2> column2,
      @JsonProperty("FROM") FromClause fromClause,
      @JsonProperty("WHERE") @Nullable Condition whereClause,
      @JsonProperty("PARENTCONTEXT") @Nullable FromContext parentContext) {
    this(column1, column2, fromClause, whereClause, parentContext, null);
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
  public SelectColumn<T2> getColumn2() {
    return column2;
  }

  @Override
  public Class<T2> getType2() {
    return column2.getType();
  }

  @Override
  public SelectClause getSelectClause() {
    return new SelectClauseColumns(List.of(column1, column2));
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return new BindVariableCollector()
        .add(column1)
        .add(column2)
        .add(getFromClause())
        .add(getWhereClause())
        .getBindsByName()
        .values();
  }

  @Override
  public SelectT2<T1, T2> mapBinds(BindMap bindMap) {
    return new SelectT2Impl<>(column1, column2, getFromClause(),
        getWhereClause(), getParentContext(), bindMap);
  }

  @Override
  public void apply(QueryConsumer consumer) {
    consumer.select(column1, column2, getFromClause(), getWhereClause());
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
    SelectT2Impl<?, ?> selectT2 = (SelectT2Impl<?, ?>) o;
    return column1.equals(selectT2.column1)
        && column2.equals(selectT2.column2);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + column1.hashCode();
    result = 31 * result + column2.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SelectT2Impl{"
        + "column1=" + column1
        + ", column2=" + column2
        + ", " + super.toString() + '}';
  }
}
