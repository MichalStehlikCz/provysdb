package com.provys.db.sqlquery.queryold;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.provys.common.exception.InternalException;
import com.provys.db.query.BindMap;
import com.provys.db.query.BindVariable;
import com.provys.db.query.CodeBuilder;
import com.provys.db.query.Context;
import com.provys.db.query.FromClause;
import com.provys.db.query.FromElement;
import com.provys.db.query.NamePath;
import com.provys.db.sqlquery.codebuilder.CodeBuilder;
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
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("FROMCLAUSE")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlFromClause
@JsonSerialize(using = SqlFromClauseImplSerializer.class)
@JsonDeserialize(using = SqlFromClauseImplDeserializer.class)
final class SqlFromClauseImpl implements SqlFromClause {

  //@JsonProperty("ELEMS")
  //@JacksonXmlElementWrapper(useWrapping = false)
  private final List<SqlFromElement> fromElements;
  private final SqlContext<?, ?, ?, ?, ?, ?, ?> context;

  SqlFromClauseImpl(SqlContext<?, ?, ?, ?, ?, ?, ?> context,
      Collection<? extends FromElement> fromElements, @Nullable BindMap bindMap) {
    this.context = context;
    this.fromElements = fromElements.stream()
        .map(fromElement -> fromElement.transfer(context, bindMap))
        .collect(Collectors.toUnmodifiableList());
  }

  /**
   * Constructor used for deserialization from Json / Xml.
   *
   * @param fromElements is collection of from elements in new clause
   */
  SqlFromClauseImpl(Collection<? extends FromElement> fromElements) {
    this(SqlContextImpl.getNoDbInstance(), fromElements, null);
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

  @SuppressWarnings("SuspiciousGetterSetter") // intentional, as normal getter uses inherited type
  List<SqlFromElement> getSqlElements() {
    return fromElements;
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
