package com.provys.db.query.names;

import com.google.errorprone.annotations.Immutable;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Immutable map, used for lookup of corresponding variable when transferring elements.
 */
@Immutable
public final class BindMap {

  // map is product of copyOf, e.g. immutable and both BindMap and BindVariable are immutable
  @SuppressWarnings("Immutable")
  private final Map<BindName, BindVariable> bindsByName;

  /**
   * Create BindMap based on supplied map.
   *
   * @param bindsByName is map that should be used as basis for this BindMap
   */
  public BindMap(Map<BindName, BindVariable> bindsByName) {
    this.bindsByName = Map.copyOf(bindsByName);
  }

  /**
   * Create BindMap based on supplied collection of bind variables.
   *
   * @param binds are variables that should be used as basis for this BindMap
   */
  public BindMap(Collection<BindVariable> binds) {
    this.bindsByName = binds.stream().collect(Collectors.toUnmodifiableMap(BindVariable::getName,
        Function.identity()));
  }

  /**
   * Return immutable map of bind variables indexed by name.
   *
   * @return map of bind variables indexed by name
   */
  public Map<BindName, BindVariable> getBindsByName() {
    return bindsByName;
  }

  /**
   * Find bind variable via name.
   *
   * @param name is name of {@link BindVariable} being looked up
   * @return bind variable with given name
   * @throws NoSuchElementException if bind variable with specified name is not found in map
   */
  public BindVariable get(BindName name) {
    var result = bindsByName.get(name);
    if (result == null) {
      throw new NoSuchElementException("Bind variable was not found by name " + name);
    }
    return result;
  }

  /**
   * Indicates if given map is superset of supplied collection.
   *
   * @param subset is collection we want to check is subset of this bind map
   * @return true if subset is subset of this map, false otherwise
   */
  public boolean isSupersetOf(Collection<BindVariable> subset) {
    return subset.stream()
        .allMatch(sub -> Objects.equals(sub, bindsByName.get(sub.getName())));
  }

  /**
   * Indicates if given map is superset of supplied map.
   *
   * @param subset is map we want to check is subset of this bind map
   * @return true if subset is subset of this map, false otherwise
   */
  public boolean isSupersetOf(Map<BindName, BindVariable> subset) {
    return isSupersetOf(subset.values());
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BindMap bindMap = (BindMap) o;
    return Objects.equals(bindsByName, bindMap.bindsByName);
  }

  @Override
  public int hashCode() {
    return bindsByName.hashCode();
  }

  @Override
  public String toString() {
    return "BindMap{"
        + "bindsByName=" + bindsByName
        + '}';
  }
}
