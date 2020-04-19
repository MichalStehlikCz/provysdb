package com.provys.db.defaultdb.types;

import com.google.errorprone.annotations.Immutable;
import com.provys.common.exception.InternalException;
import com.provys.db.dbcontext.DbPreparedStatement;
import com.provys.db.dbcontext.DbResultSet;
import java.io.Serializable;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

/**
 * Supports reading values of given type from {@link DbResultSet} and binding them to {@link
 * DbPreparedStatement}.
 *
 * @param <T> is type this adapter is associated with
 */
@Immutable
public interface SqlTypeAdapter<T> extends Serializable {

  /**
   * Class this adapter converts to database values.
   *
   * @return class this adapter is associated with
   */
  Class<T> getType();

  /**
   * Sql type associated with this adapter.
   *
   * @return com.provys.db.sql type associated with this adapter
   */
  int getSqlType();

  /**
   * JDBC / database type associated with this adapter.
   *
   * @return name of type associated with this adapter (on JDBC level), empty optional for
   *     non-object types
   */
  default Optional<String> getTypeName() {
    return Optional.empty();
  }

  /**
   * Read value from result set. Throw exception when value in given column is null.
   *
   * @param resultSet   is result set value should be read from
   * @param columnIndex is column value should be read from
   * @return read column value
   */
  @NonNull T readNonNullValue(DbResultSet resultSet, int columnIndex);

  /**
   * Read value from result set. Throw exception when value in given column is null.
   *
   * @param resultSet   is result set value should be read from
   * @param columnLabel is name of column value should be read from
   * @return read column value
   */
  @NonNull T readNonNullValue(DbResultSet resultSet, String columnLabel);

  /**
   * Read value from result set, allow reading null values.
   *
   * @param resultSet   is result set value should be read from
   * @param columnIndex is column value should be read from
   * @return read column value
   */
  @Nullable T readNullableValue(DbResultSet resultSet, int columnIndex);

  /**
   * Read value from result set, allow reading null values.
   *
   * @param resultSet   is result set value should be read from
   * @param columnLabel is name of column value should be read from
   * @return read column value
   */
  @Nullable T readNullableValue(DbResultSet resultSet, String columnLabel);

  /**
   * Read value from result set, allow reading null values.
   *
   * @param resultSet   is result set value should be read from
   * @param columnIndex is column value should be read from
   * @return read column value
   */
  default Optional<@NonNull T> readOptionalValue(DbResultSet resultSet, int columnIndex) {
    return Optional.ofNullable(readNullableValue(resultSet, columnIndex));
  }

  /**
   * Read value from result set, allow reading null values.
   *
   * @param resultSet   is result set value should be read from
   * @param columnLabel is name of column value should be read from
   * @return read column value
   */
  default Optional<@NonNull T> readOptionalValue(DbResultSet resultSet, String columnLabel) {
    return Optional.ofNullable(readNullableValue(resultSet, columnLabel));
  }

  /**
   * Bind value to variable.
   *
   * @param statement      is prepared statement where value should be bind
   * @param parameterIndex is index of parameter whose value should be set
   * @param value          is value to be set
   */
  void bindValue(DbPreparedStatement statement, int parameterIndex, @Nullable T value);

  /**
   * Returns true if type adapter is used for can accept value of source type (implicit conversion
   * exists). In general, implicit conversions are allowed from type with less values or smaller
   * precision to type with more values / higher precision, but not the other way around
   *
   * @param sourceType is source type for conversion
   * @return true if such implicit conversion is possible and false otherwise
   */
  default boolean isAssignableFrom(Class<?> sourceType) {
    return sourceType == getType();
  }

  /**
   * Convert value to type adapter is used for using implicit conversion, throw exception if
   * implicit conversion is not possible.
   *
   * @param value is source value
   * @return converted value
   */
  default @PolyNull T convert(@PolyNull Object value) {
    if (value == null) {
      return null;
    }
    if (getType().isInstance(value)) {
      @SuppressWarnings("unchecked") // should be safe as long as we do not use parametrised types
      var result = (@NonNull T) value;
      return result;
    }
    throw new InternalException(
        "Conversion not supported from " + value.getClass() + " to " + getType());
  }
}
