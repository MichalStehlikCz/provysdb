package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.google.errorprone.annotations.Immutable;
import com.provys.db.query.names.NamePath;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents source - from clause in Sql.
 */
@JsonRootName("FROM")
@JsonSerialize(using = FromClauseSerializer.class)
@JsonDeserialize(using = FromClauseDeserializer.class)
@Immutable
public interface FromClause extends Element<FromClause> {

  /**
   * Get element matching supplied alias. If there are multiple such elements, throw an exception.
   *
   * @param alias is alias used for look-up
   * @return element if exactly one such element exists, null otherwise
   */
  @Nullable FromElement getElementByAlias(NamePath alias);

  /**
   * Check if given alias is unique (or does not exist) in from clause.
   *
   * @param alias is alias to be matched
   * @return true if there is at most one element with specified alias, false if there are multiple
   *     such elements
   */
  boolean isUnique(NamePath alias);

  /**
   * Elements forming from clause.
   *
   * @return list of elements forming this clause
   */
  List<FromElement> getElements();
}
