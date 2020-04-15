package com.provys.db.query.elements;

import static org.checkerframework.checker.nullness.NullnessUtil.castNonNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.google.errorprone.annotations.Immutable;
import com.provys.common.exception.InternalException;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SegmentedName;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.RequiresNonNull;

/**
 * Represents from context, defined by from clause and parent context. Used as basis for
 * construction of select statements, as these statements have exactly this context.
 */
@JsonAutoDetect(
    fieldVisibility = Visibility.NONE,
    setterVisibility = Visibility.NONE,
    getterVisibility = Visibility.NONE,
    isGetterVisibility = Visibility.NONE,
    creatorVisibility = Visibility.NONE
)
@JsonRootName("REGULARFROMCONTEXT")
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SqlFromClause
@Immutable
class RegularFromContext implements FromContext {

  @JsonProperty("PARENTCONTEXT")
  private final @Nullable FromContext parentContext;
  @JsonProperty("FROM")
  private final FromClause fromClause;

  RegularFromContext(FromClause fromClause, @Nullable FromContext parentContext,
      @Nullable BindMap bindMap) {
    this.parentContext = parentContext;
    this.fromClause = (bindMap == null) ? fromClause : fromClause.mapBinds(bindMap);
  }

  @JsonCreator
  RegularFromContext(@JsonProperty("FROM") FromClause fromClause,
      @JsonProperty("PARENTCONTEXT") @Nullable FromContext parentContext) {
    this(fromClause, parentContext, null);
  }

  /**
   * From clause this context is based on.
   *
   * @return from clause
   */
  public FromClause getFromClause() {
    return fromClause;
  }

  /**
   * Parent context.
   *
   * @return parent context this regular context is part of, null if this is top level query
   */
  public @Nullable FromContext getParentContext() {
    return parentContext;
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
    var parentAlias = castNonNull(parentContext.getDefaultAlias(fromElement));
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

  @SuppressWarnings("EqualsGetClass")
  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RegularFromContext that = (RegularFromContext) o;
    return fromClause.equals(that.fromClause)
        && Objects.equals(parentContext, that.parentContext);
  }

  @Override
  public int hashCode() {
    int result = fromClause.hashCode();
    result = 31 * result + (parentContext != null ? parentContext.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "RegularFromContext{"
        + "fromClause=" + fromClause
        + ", parentContext=" + parentContext
        + '}';
  }
}
