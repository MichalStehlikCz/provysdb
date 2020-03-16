package sqlbuilder;

import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Bind value connects type and value to bind name.
 *
 * @param <T> is type of value this bind variable can hold
 */
public interface BindValueBuilder<T> extends SelectExpressionBuilder<T>, BindWithType {

  /**
   * Value assigned to bind variable.
   *
   * @return value assigned to bind variable, null if no value has been assigned
   */
  @Nullable T getValue();

  /**
   * Get value assigned to bind variable. This variant does runtime type conversion and bypasses
   * compile time type checking; typed variant of this method should be used when possible
   *
   * @param returnType is type parameter should have
   * @param <U>  is type parameter, representing type of returned value
   * @return value associated with this bind value, empty optional if no value has been defined yet
   */
  <U> @Nullable U getValue(Class<U> returnType);

  /**
   * Combine type of this bind value with supplied type. Can only specialize type and checks it in
   * runtime. Also verifies that supplied type is compatible with bind value's current value. If
   * supplied type is parent of current type, operation is ignored and returns self
   *
   * @param newType is new type bind should have
   * @param <U> is type parameter, denoting required type
   * @return bind value with required type and same name and value as a current one
   */
  <U extends T> BindValueBuilder<U> type(Class<U> newType);

  /**
   * Return bind value with the same name and type, but with the new value.
   *
   * @param newValue is value to be assigned to new bind
   * @return bind variable with the same name as old one, but with the new value
   */
  BindValueBuilder<T> value(@Nullable T newValue);

  /**
   * Return bind value with the same name and type, but with the new value. Unsafe version, used
   * when bind variable type is not known, performs type checking at runtime. Only to be used when
   * variable type has been lost, for example because it has been stored in list in the meantime
   *
   * @param newValue is value to be assigned to new bind
   * @return bind variable with the same name as old one, but with the new value
   */
  BindValueBuilder<T> valueUnsafe(@Nullable Object newValue);

  /**
   * Method can be used when constructing statement and merging its parts. It combines two bind
   * values; they should have the same name and type. It verifies their values; if they have
   * different non-null values, exception is raised. Otherwise it uses one of variables to be
   * combined, preferring one with the value
   *
   * @param other is bind variable this variable should be combined with
   * @return this or other bind variable, depending which has more complete information
   */
  BindValueBuilder<T> combine(BindValueBuilder<?> other);
}
