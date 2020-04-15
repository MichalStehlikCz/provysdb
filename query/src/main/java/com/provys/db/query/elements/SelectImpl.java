package com.provys.db.query.elements;

import static org.checkerframework.checker.nullness.NullnessUtil.castNonNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.BindVariableCollector;
import java.util.Collection;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents Sql select statement with default (= Oracle) syntax.
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("SELECT")
@JsonPropertyOrder({"SELECT", "FROM", "WHERE", "PARENTCONTEXT"})
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from Select
@Immutable
final class SelectImpl extends SelectTImpl implements Select {

  @JsonProperty("SELECT")
  private final SelectClause selectClause;

  SelectImpl(SelectClause selectClause, FromClause fromClause, @Nullable Condition whereClause,
      @Nullable FromContext parentContext, @Nullable BindMap bindMap) {
    super(fromClause, whereClause, parentContext, bindMap);
    this.selectClause = (bindMap == null) ? selectClause : selectClause.mapBinds(bindMap);
  }

  @JsonCreator
  SelectImpl(@JsonProperty("SELECT") SelectClause selectClause,
      @JsonProperty("FROM") @JsonDeserialize(using = FromClauseDeserializer.class) FromClause
          fromClause,
      @JsonProperty("WHERE") @Nullable Condition whereClause,
      @JsonProperty("PARENTCONTEXT") @Nullable FromContext parentContext) {
    this(selectClause, Objects.requireNonNull(fromClause), whereClause, parentContext, null);
  }

  @Override
  public SelectClause getSelectClause() {
    return selectClause;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return new BindVariableCollector()
        .add(selectClause)
        .add(getFromClause())
        .add(getWhereClause())
        .getBindsByName()
        .values();
  }

  @Override
  public Select mapBinds(BindMap bindMap) {
    return new SelectImpl(selectClause, getFromClause(), getWhereClause(), getParentContext(),
        bindMap);
  }

  @Override
  public void apply(QueryConsumer consumer) {
    consumer.select(selectClause, getFromClause(), getWhereClause());
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
    SelectImpl select = (SelectImpl) o;
    return selectClause.equals(select.selectClause);
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + selectClause.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SelectImpl{"
        + "selectClause=" + selectClause
        + ", " + super.toString() + '}';
  }
}
