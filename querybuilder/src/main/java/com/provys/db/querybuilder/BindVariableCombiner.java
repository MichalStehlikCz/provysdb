package com.provys.db.querybuilder;

import com.provys.common.exception.InternalException;
import com.provys.common.exception.ProvysException;
import com.provys.db.query.elements.Element;
import com.provys.db.query.names.BindMap;
import com.provys.db.query.names.BindName;
import com.provys.db.query.names.BindVariable;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Combines bind variables with the same name into single item.
 * Rules for combination are (1) when two variables of different types are being combined,
 * more specific type prevails; if types are not super / sub type, exception is thrown and (2)
 * if value and null is combined, values prevails, if two different values are met, exception is
 * thrown
 */
@SuppressWarnings("UnusedReturnValue")
public final class BindVariableCombiner {

  private final Map<BindName, BindVariable> variableByName = new HashMap<>(5);

  /**
   * Value of field variableByName. Note that returned map might reflect changes made to this
   * combiner.
   *
   * @return value of field variableByName
   */
  public Map<BindName, BindVariable> getVariableByName() {
    return Collections.unmodifiableMap(variableByName);
  }

  /**
   * Retrieve list of bind variables, held in this combiner. Note that returned collection might
   * reflect changes made to this combiner.
   *
   * @return list of bind variables, held in this combiner
   */
  public Collection<BindVariable> getBinds() {
    return Collections.unmodifiableCollection(variableByName.values());
  }

  /**
   * Retrieve collected bind map.
   *
   * @return collected bind map
   */
  public BindMap getBindMap() {
    return new BindMap(variableByName);
  }

  private static ProvysException getCannotCombineException(BindVariable bind, String text) {
    return new InternalException("Cannot combine bind variable " + bind.getName() + ": " + text);
  }

  private static Class<?> combineType(BindVariable old, BindVariable bind) {
    
    if (bind.getType().isAssignableFrom(old.getType())) {
      return old.getType();
    }
    if (old.getType().isAssignableFrom(bind.getType())) {
      return bind.getType();
    }
    throw getCannotCombineException(old,
        "types " + old.getType() + " and " + bind.getType() + " are not compatible");
  }

  private static @Nullable Serializable combineValue(BindVariable old, BindVariable bind) {
    var oldValue = old.getValue();
    var bindValue = bind.getValue();
    if ((bindValue == null) || bindValue.equals(oldValue)) {
      if ((oldValue != null) && !bind.getType().isInstance(oldValue)) {
        throw getCannotCombineException(old,
            "value " + oldValue + " is not compatible with type " + bind.getType());
      }
      return oldValue;
    }
    if (oldValue == null) {
      if (!old.getType().isInstance(bindValue)) {
        throw getCannotCombineException(old,
            "value " + bindValue + " is not compatible with type " + old.getType());
      }
      return bindValue;
    }
    throw getCannotCombineException(old,
        "values " + oldValue + " and " + bindValue + " are not compatible");
  }

  public BindVariableCombiner add(BindVariable bind) {
    var old = variableByName.get(bind.getName());
    BindVariable result;
    if (old != null) {
      // if both variables are the same, we can leave original mapping
      if (old.equals(bind)) {
        return this;
      }
      // merge types
      var resultType = combineType(old, bind);
      // merge values
      var resultValue = combineValue(old, bind);
      // merged result is the same as original mapping, we can leave original mapping
      if ((old.getType() == resultType) && Objects.equals(old.getValue(), resultValue)) {
        return this;
      }
      if ((bind.getType() == resultType) && Objects.equals(bind.getValue(), resultValue)) {
        result = bind;
      } else {
        // Result cannot be just bind name (with object), as old would be used in such situation
        // which means it is serializable.
        // And type belonged to one of original bind variables and is not Object, thus it is
        // immutable
        @SuppressWarnings("Immutable")
        var merged = new BindVariable(old.getName(), resultType.asSubclass(Serializable.class),
            resultValue);
        result = merged;
      }
    } else {
      result = bind;
    }
    variableByName.put(result.getName(), result);
    return this;
  }

  public BindVariableCombiner add(Iterable<? extends BindVariable> binds) {
    for (var bind : binds) {
      add(bind);
    }
    return this;
  }

  public BindVariableCombiner addElement(@Nullable Element<?> element) {
    if (element != null) {
      add(element.getBinds());
    }
    return this;
  }

  public BindVariableCombiner addElements(Iterable<? extends Element<?>> elements) {
    for (var element : elements) {
      addElement(element);
    }
    return this;
  }

  public BindVariableCombiner addBuilder(BuilderBase builder) {
    builder.appendBinds(this);
    return this;
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BindVariableCombiner that = (BindVariableCombiner) o;
    return variableByName.equals(that.variableByName);
  }

  @Override
  public int hashCode() {
    return variableByName.hashCode();
  }

  @Override
  public String toString() {
    return "BindVariableCombiner{"
        + "variableByName=" + variableByName
        + '}';
  }
}
