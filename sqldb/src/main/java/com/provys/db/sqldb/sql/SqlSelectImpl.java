package com.provys.db.sqldb.sql;

import static org.checkerframework.checker.nullness.NullnessUtil.castNonNull;

import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbConnection;
import com.provys.db.sql.BindMap;
import com.provys.db.sql.BindName;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Context;
import com.provys.db.sql.Condition;
import com.provys.db.sql.FromClause;
import com.provys.db.sql.FromContext;
import com.provys.db.sql.FromElement;
import com.provys.db.sql.NamePath;
import com.provys.db.sql.SegmentedName;
import com.provys.db.sql.Select;
import com.provys.db.sql.SelectClause;
import com.provys.db.sql.SelectStatement;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/**
 * Represents Sql select statement with default (= Oracle) syntax.
 */
final class SqlSelectImpl implements SqlSelect, FromContext {

  private final @Nullable FromContext parentContext;
  private final SqlSelectClause selectClause;
  private final SqlFromClause fromClause;
  private final @Nullable SqlCondition whereClause;
  private final Map<BindName, BindVariable> bindsByName;
  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;

  SqlSelectImpl(SqlContext<?, ?, ?, ?, ?, ?, ?> context, SelectClause selectClause,
      FromClause fromClause, @Nullable Condition whereClause, @Nullable FromContext parentContext,
      @Nullable BindMap bindMap) {
    this.context = context;
    this.parentContext = parentContext;
    this.fromClause = fromClause.transfer(context, bindMap);
    this.selectClause = selectClause.transfer(context, bindMap);
    if (whereClause == null) {
      this.whereClause = null;
    } else {
      this.whereClause = whereClause.transfer(context, bindMap);
    }
    // we need to collect binds from sub-elements, as bindMap can contain variables not present in
    // this statement
    this.bindsByName = Map.copyOf(new BindVariableCollector()
        .add(selectClause)
        .add(fromClause)
        .add(whereClause)
        .getBindsByName());
  }

  @Override
  public SqlContext<?, ?, ?, ?, ?, ?, ?> getContext() {
    return context;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    return new BindVariableCollector()
        .add(selectClause)
        .add(fromClause)
        .add(whereClause)
        .getBindsByName()
        .values();
  }

  @Override
  public <S extends Select> S transfer(Context<S, ?, ?, ?, ?, ?, ?> targetContext,
      @Nullable BindMap bindMap) {
    if (context.equals(targetContext) && ((bindMap == null) || bindMap.isSupersetOf(bindsByName))) {
      @SuppressWarnings("unchecked")
      var result = (S) this;
      return result;
    }
    return targetContext.select(selectClause, fromClause, whereClause, bindMap);
  }

  /**
   * Map of binds and their values.
   *
   * @return map of bind names and their respective values
   */
  Map<BindName, Object> getBindValues() {
    return bindsByName.values().stream()
        .filter(bindVariable -> bindVariable.getValue() != null)
        .collect(Collectors
            .toUnmodifiableMap(BindVariable::getName, bind -> castNonNull(bind.getValue())));
  }

  @Override
  public void append(CodeBuilder builder) {
    builder.appendLine("SELECT");
    selectClause.append(builder);
    builder.appendLine("FROM");
    fromClause.append(builder);
    if (whereClause != null) {
      builder.append("WHERE");
      whereClause.append(builder);
    }
  }

  @Override
  public SelectStatement parse() {
    var builder = CodeBuilderFactory.getCodeBuilder();
    append(builder);
    return new SelectStatementImpl(builder.build(), builder.getBindsWithPos(), getBindValues(),
        context);
  }

  @Override
  public SelectStatement parse(DbConnection connection) {
    var builder = CodeBuilderFactory.getCodeBuilder();
    append(builder);
    return new SelectStatementImpl(builder.build(), builder.getBindsWithPos(), getBindValues(),
        connection);
  }

  @Override
  public @Nullable FromElement getFromElement(NamePath alias) {
    // try to find alias in from clause
    var element = fromClause.getElementByAlias(alias);
    if (element == null) {
      // or inherit it from parent if possible...
      if (parentContext == null) {
        throw new NoSuchElementException("From element was not found using alias " + alias);
      }
      return parentContext.getFromElement(alias);
    }
    return element;
  }

  private NamePath getDefaultAliasInFromClause(NamePath alias, FromElement fromElement) {
    // find shortest alias that identifies element in from clause
    var segments = alias.getSegments();
    //noinspection ForLoopReplaceableByWhile
    for (var i = segments.size() - 1; i > 0; i--) {
      var subAlias = SegmentedName.ofSegments(segments.subList(i, segments.size()));
      if (fromClause.isUnique(subAlias)
          && fromElement.equals(fromClause.getElementByAlias(subAlias))) {
        return subAlias;
      }
    }
    return alias;
  }

  @RequiresNonNull("parentContext")
  private NamePath getDefaultAliasFromParent(NamePath alias, FromElement fromElement) {
    // get identifying alias from parent and prolong it until it doesn't interfere with fromClause
    var segments = alias.getSegments();
    assert parentContext != null;
    // we already know that element has alias, thus this is non-null
    var parentAlias = Objects.requireNonNull(parentContext.getDefaultAlias(fromElement));
    //noinspection ForLoopReplaceableByWhile
    for (var i = segments.size() - parentAlias.getSegments().size(); i > 0; i--) {
      var subAlias = SegmentedName.ofSegments(segments.subList(i, segments.size()));
      if (fromClause.isUnique(subAlias) && (fromClause.getElementByAlias(subAlias) == null)) {
        return subAlias;
      }
    }
    return alias;
  }

  @Override
  public @Nullable NamePath getDefaultAlias(FromElement fromElement) {
    var alias = fromElement.getAlias();
    if (alias == null) {
      // no alias only allowed when only single element is in from clause
      var elements = fromClause.getElements();
      if ((elements.size() != 1) || !elements.get(0).equals(fromElement)) {
        throw new InternalException("Cannot use from element without alias in query with "
            + "multiple elements");
      }
      return null;
    }
    // check if given element can be addressed by its alias
    var alElement = fromClause.getElementByAlias(alias);
    // true indicates that from element is present in from clause
    var inFrom = true;
    if ((alElement == null) && (parentContext != null)) {
      alElement = parentContext.getFromElement(alias);
      if (alElement == null) {
        // element is outside known context of this statement, but there is unknown context - we
        // cannot safely simplify supplied context
        return alias;
      }
      inFrom = false;
    }
    if (!fromElement.equals(alElement)) {
      throw new InternalException("Element " + fromElement + " cannot be addressed from this"
          + " query; alias " + alias + " leads to element " + alElement);
    }
    // if alias is simple, return it outright (99.9 % of cases)
    if (alias.isSimple()) {
      return alias;
    }
    // now try if any shorter alias is also usable
    if (inFrom) {
      return getDefaultAliasInFromClause(alias, fromElement);
    }
    castNonNull(parentContext); // inFrom is set to false in if with parentContext!=null...
    return getDefaultAliasFromParent(alias, fromElement);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlSelectImpl sqlSelect = (SqlSelectImpl) o;
    return Objects.equals(context, sqlSelect.context)
        && Objects.equals(selectClause, sqlSelect.selectClause)
        && Objects.equals(fromClause, sqlSelect.fromClause)
        && Objects.equals(whereClause, sqlSelect.whereClause);
  }

  @Override
  public int hashCode() {
    int result = context != null ? context.hashCode() : 0;
    result = 31 * result + (selectClause != null ? selectClause.hashCode() : 0);
    result = 31 * result + (fromClause != null ? fromClause.hashCode() : 0);
    result = 31 * result + (whereClause != null ? whereClause.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "SqlSelectImpl{"
        + "context=" + context
        + ", selectClause=" + selectClause
        + ", fromClause=" + fromClause
        + ", whereClause=" + whereClause
        + '}';
  }
}
