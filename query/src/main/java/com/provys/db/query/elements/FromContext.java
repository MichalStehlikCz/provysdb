package com.provys.db.query.elements;

import com.provys.db.query.names.NamePath;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Used to look up source table when column is being created. Column to be created gets alias (path)
 * and uses it to look up source table, ans also evaluate which part of path is needed to uniquely
 * identify table and thus should be used as table specification in SQL
 */
public interface FromContext {

  /**
   * Get from element for specified alias. Depending on type of context, might either throw an
   * exception (if it is context of full select statement) or return null (see
   * {@link UnknownFromContext}, used when condition is constructed outside select statement)
   *
   * @param alias is alias used in column expression
   * @return from element with corresponding alias
   */
  @Nullable FromElement getFromElement(NamePath alias);

  /**
   * Get default alias to be used for given from element. If from element is from parent context and
   * there is from element with the same alias in this context, no alias can be used to access it
   * and call fails. Otherwise if from element has simple alias, it is easy and its alias should be
   * used. If element has composed alias, shortest version of alias that identifies given element is
   * used. If element has no alias and is the only such element, null is returned, otherwise
   * exception is thrown.
   *
   * @param fromElement is element we evaluate alias for
   * @return alias (in shortest form) usable to access driven from element
   */
  @Nullable NamePath getDefaultAlias(FromElement fromElement);

  /**
   * Get default alias based on supplied alias. Should look up element and then normalise its alias.
   * If from element for given alias is not found, it is expected that alias belongs to element
   * outside this context and alias is returned untouched
   *
   * @param alias is alias to be normalized
   * @return normalized alias (e.g. with minimal length that still identifies given from element)
   */
  default NamePath getDefaultAlias(NamePath alias) {
    var fromElement = getFromElement(alias);
    if (fromElement != null) {
      return Objects.requireNonNull(getDefaultAlias(fromElement),
          "Unexpected null default alias for element, retrieved using alias");
    }
    return alias;
  }
}
