package com.provys.db.dbcontext;

import com.google.errorprone.annotations.Immutable;
import java.io.Serializable;
import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.checker.nullness.qual.PolyNull;

/**
 * Used to handle usage of Java types mapped to database types with statements and result set.
 */
@Immutable
public interface SqlTypeHandler extends Serializable {

  /**
   * Sql type associated with supplied Java type.
   *
   * @param type is Java type we ask about
   * @return com.provys.db.sql type associated with this adapter
   */
  int getSqlType(Class<?> type);

  /**
   * JDBC / database type associated with supplied Java type.
   *
   * @param type is Java type we ask about
   * @return name of type associated with this adapter (on JDBC level), empty optional for
   *     non-object types
   */
  Optional<String> getTypeName(Class<?> type);

  /**
   * Read value from result set. Throw exception when value in given column is null.
   *
   * @param resultSet   is result set value should be read from
   * @param columnIndex is column value should be read from
   * @param type        is Java type of column
   * @param <T>         represents Java type of column for compile-time type safety
   * @return read column value
   */
  <T> @NonNull T readNonNullValue(DbResultSet resultSet, int columnIndex, Class<T> type);

  /**
   * Read value from result set. Throw exception when value in given column is null.
   *
   * @param resultSet   is result set value should be read from
   * @param columnLabel is name of column value should be read from
   * @param type        is Java type of column
   * @param <T>         represents Java type of column for compile-time type safety
   * @return read column value
   */
  <T> @NonNull T readNonNullValue(DbResultSet resultSet, String columnLabel, Class<T> type);

  /**
   * Read value from result set, allow reading null values.
   *
   * @param resultSet   is result set value should be read from
   * @param columnIndex is column value should be read from
   * @param type        is Java type of column
   * @param <T>         represents Java type of column for compile-time type safety
   * @return read column value
   */
  <T> @Nullable T readNullableValue(DbResultSet resultSet, int columnIndex, Class<T> type);

  /**
   * Read value from result set, allow reading null values.
   *
   * @param resultSet   is result set value should be read from
   * @param columnLabel is name of column value should be read from
   * @param type        is Java type of column
   * @param <T>         represents Java type of column for compile-time type safety
   * @return read column value
   */
  <T> @Nullable T readNullableValue(DbResultSet resultSet, String columnLabel, Class<T> type);

  /**
   * Read value from result set, allow reading null values.
   *
   * @param resultSet   is result set value should be read from
   * @param columnIndex is column value should be read from
   * @param type        is Java type of column
   * @param <T>         represents Java type of column for compile-time type safety
   * @return read column value
   */
  <T> Optional<@NonNull T> readOptionalValue(DbResultSet resultSet, int columnIndex, Class<T> type);

  /**
   * Read value from result set, allow reading null values.
   *
   * @param resultSet   is result set value should be read from
   * @param columnLabel is name of column value should be read from
   * @param type        is Java type of column
   * @param <T>         represents Java type of column for compile-time type safety
   * @return read column value
   */
  <T> Optional<@NonNull T> readOptionalValue(DbResultSet resultSet, String columnLabel,
      Class<T> type);

  /**
   * Bind value to variable.
   *
   * @param statement      is prepared statement where value should be bind
   * @param parameterIndex is index of parameter whose value should be set
   * @param value          is value to be set
   * @param type           is Java type of bind variable
   * @param <T>            represents Java type of bind variable for compile-time type safety
   */
  <T> void bindValue(DbPreparedStatement statement, int parameterIndex, @Nullable T value,
      Class<T> type);

  /**
   * Bind value to variable.
   *
   * @param statement      is prepared statement where value should be bind
   * @param parameterIndex is index of parameter whose value should be set
   * @param value          is value to be set
   */
  void bindValue(DbPreparedStatement statement, int parameterIndex, Object value);

  /**
   * Returns true if target type can accept value of source type (implicit conversion exists). In
   * general, implicit conversions are allowed from type with less values or smaller precision
   * to type with more values / higher precision, but not the other way around
   *
   * @param targetType is target type for conversion
   * @param sourceType is source type for conversion
   * @return true if such implicit conversion is possible and false otherwise
   */
  boolean isAssignableFrom(Class<?> targetType, Class<?> sourceType);

  /**
   * Convert value to target type using implicit conversion, throw exception if implicit conversion
   * is not possible.
   *
   * @param targetType is target type for conversion
   * @param value is source value
   * @param <T> is type parameter corresponding to target type
   * @return converted value
   */
  <T> @PolyNull T convert(Class<T> targetType, @PolyNull Object value);
}
