package com.provys.db.querybuilder;

import com.provys.common.exception.InternalException;
import com.provys.common.exception.ProvysException;
import com.provys.common.types.TypeMap;
import com.provys.common.types.TypeMapImpl;
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
 * Combines bind variables with the same name into single item. Rules for combination are (1) when
 * two variables of different types are being combined, more specific type prevails; if types are
 * not super / sub type, exception is thrown and (2) if value and null is combined, values prevails,
 * if two different values are met, exception is thrown
 */
@SuppressWarnings("UnusedReturnValue")
public final class BindVariableCombiner {

  private final TypeMap typeMap = TypeMapImpl.getDefault();
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

  private static ProvysException getCannotCombineException(BindVariable bind, String text,
      @Nullable Throwable e) {
    return new InternalException("Cannot combine bind variable " + bind.getName() + ": " + text, e);
  }

  private static ProvysException getCannotCombineException(BindVariable bind, String text) {
    return getCannotCombineException(bind, text, null);
  }

  /**
   * Type combination - we return more specific type, if none is more specific than other, fail.
   *
   * @param old  is old bind variable
   * @param bind is new bind variable being combined with old
   * @return more specific of bind variable types
   */
  private Class<?> combineType(BindVariable old, BindVariable bind) {
    if (typeMap.isAssignableFrom(old.getType(), bind.getType())) {
      return bind.getType();
    }
    if (typeMap.isAssignableFrom(bind.getType(), old.getType())) {
      return old.getType();
    }
    throw getCannotCombineException(old,
        "types " + old.getType() + " and " + bind.getType() + " are not compatible");
  }

  private Serializable retypeValue(Class<? extends Serializable> resultType, Serializable value,
      BindVariable old) {
    try {
      return typeMap.convert(resultType, value);
    } catch (RuntimeException e) {
      throw getCannotCombineException(old,
          "cannot retype " + value + " to " + resultType, e);
    }
  }

  private @Nullable Serializable combineValue(Class<? extends Serializable> resultType,
      BindVariable old, @Nullable Serializable bindValue) {
    var oldValue = old.getValue();
    if ((bindValue == null) || bindValue.equals(oldValue)) {
      if (oldValue == null) {
        return null;
      }
      return retypeValue(resultType, oldValue, old);
    }
    if (oldValue == null) {
      return retypeValue(resultType, bindValue, old);
    }
    /* both are non-null, but potentially of different types... we need to convert them before
     comparison */
    var newValue = retypeValue(resultType, oldValue, old);
    if (newValue.equals(retypeValue(resultType, bindValue, old))) {
      return newValue;
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
      var resultType = combineType(old, bind).asSubclass(Serializable.class);
      // merge values
      var resultValue = combineValue(resultType, old, bind.getValue());
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
