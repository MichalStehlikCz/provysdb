package com.provys.db.query.elements;

import com.provys.common.exception.InternalException;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Support class - used to build bind variable collection based on several supplied collections.
 * Throws an exception when there are two different bind variables with the same name.
 */
@SuppressWarnings("UnusedReturnValue")
public final class BindVariableCollector {

  private final Map<BindName, BindVariable> bindsByName;

  /**
   * Create empty bind variable collector.
   */
  public BindVariableCollector() {
    this.bindsByName = new HashMap<>(5);
  }

  /**
   * Create bind variable collector and initialize it with supplied binds.
   *
   * @param binds is collection of binds that should be initially placed in collector
   */
  public BindVariableCollector(Collection<? extends BindVariable> binds) {
    bindsByName = new HashMap<>(binds.size());
    add(binds);
  }

  /**
   * Get collected bind variables.
   *
   * @return collected bind variables
   */
  public Collection<BindVariable> getBinds() {
    return Collections.unmodifiableCollection(bindsByName.values());
  }

  /**
   * Get collected bind variables, indexed by name.
   *
   * @return collected bind variables indexed by name
   */
  public Map<BindName, BindVariable> getBindsByName() {
    return Collections.unmodifiableMap(bindsByName);
  }

  /**
   * Put supplied bind to collection, check for conflicts.
   *
   * @param bind is bind variable to be added
   * @return self to support fluent build
   */
  public BindVariableCollector add(BindVariable bind) {
    var oldBind = bindsByName.putIfAbsent(bind.getName(), bind);
    if ((oldBind != null) && !oldBind.equals(bind)) {
      throw new InternalException("Duplicate bind variable for name " + bind.getName() + ": "
          + oldBind + " vs. " + bind);
    }
    return this;
  }

  /**
   * Put all supplied binds to collection, check for conflicts.
   *
   * @param binds is collection of binds to be added
   * @return self to support fluent build
   */
  public BindVariableCollector add(Iterable<? extends BindVariable> binds) {
    for (var bind : binds) {
      add(bind);
    }
    return this;
  }

  /**
   * Put all supplied binds to collection, check for conflicts.
   *
   * @param element is element whose binds we want to add
   * @return self to support fluent build
   */
  public BindVariableCollector add(@Nullable Element<?> element) {
    if (element != null) {
      add(element.getBinds());
    }
    return this;
  }

  @Override
  public String toString() {
    return "BindVariableCollector{"
        + "bindsByName=" + bindsByName
        + '}';
  }
}
