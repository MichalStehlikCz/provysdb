package com.provys.db.sqldb.sql;

import com.provys.common.exception.InternalException;
import com.provys.db.sql.BindMap;
import com.provys.db.sql.BindVariable;
import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Context;
import com.provys.db.sql.FromClause;
import com.provys.db.sql.FromElement;
import com.provys.db.sql.NamePath;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Default implementation of from clause. Keeps list of from elements, exports them as comma
 * separated list, each from element on separate line. Implementation assumes that list is
 * relatively small, thus it is not necessary to index list by alias for faster access.
 */
final class SqlFromClauseImpl implements SqlFromClause {

  private final List<SqlFromElement> fromElements;
  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;

  SqlFromClauseImpl(SqlContext<?, ?, ?, ?, ?, ?, ?> context,
      Collection<? extends FromElement> fromElements, @Nullable BindMap bindMap) {
    this.context = context;
    this.fromElements = fromElements.stream()
        .map(fromElement -> fromElement.transfer(context, bindMap))
        .collect(Collectors.toUnmodifiableList());
  }

  @Override
  public @Nullable SqlFromElement getElementByAlias(NamePath alias) {
    SqlFromElement result = null;
    for (var element : fromElements) {
      if (element.match(alias)) {
        if (result != null) {
          throw new InternalException(
              "Alias " + alias + "does not uniquely identify element in " + this + "; elements "
                  + result + " and " + element + " both match");
        }
        result = element;
      }
    }
    return result;
  }

  @Override
  public boolean isUnique(NamePath alias) {
    boolean found = false;
    for (var element : fromElements) {
      if (element.match(alias)) {
        if (found) {
          return false;
        }
        found = true;
      }
    }
    return true;
  }

  @Override
  public List<FromElement> getElements() {
    return Collections.unmodifiableList(fromElements);
  }

  @Override
  public <F extends FromClause> F transfer(Context<?, ?, ?, F, ?, ?, ?> targetContext,
      @Nullable BindMap bindMap) {
    return targetContext.fromClause(Collections.unmodifiableList(fromElements), bindMap);
  }

  @Override
  public Context<?, ?, ?, ?, ?, ?, ?> getContext() {
    return context;
  }

  @Override
  public Collection<BindVariable> getBinds() {
    var collector = new BindVariableCollector();
    for (var fromElement : fromElements) {
      collector.add(fromElement);
    }
    return collector.getBinds();
  }

  @Override
  public void append(CodeBuilder builder) {
    builder.appendLine("FROM");
    builder.increasedIdent("    ", "  , ", 4);
    for (var fromElement : fromElements) {
      fromElement.append(builder);
      builder.appendLine();
    }
    builder.popIdent();
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlFromClauseImpl that = (SqlFromClauseImpl) o;
    return fromElements.equals(that.fromElements)
        && context.equals(that.context);
  }

  @Override
  public int hashCode() {
    int result = context.hashCode();
    result = 31 * result + fromElements.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "SqlFromClauseImpl{"
        + "fromElements=" + fromElements
        + ", context=" + context
        + '}';
  }
}
