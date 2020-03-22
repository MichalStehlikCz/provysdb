package com.provys.db.dbcontext;

import java.util.Optional;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Used to hold set of {link SqlTypeAdapter} and retrieve particular adapter for given class.
 */
public interface SqlTypeMap {

  /**
   * Retrieve adapter for given class.
   *
   * @param type is class for which adapter is to be retrieved
   * @param <T>  is type parameter, binding supplied type and returned adapter
   * @return type adapter to be used with given class
   */
  <T> SqlTypeAdapter<T> getAdapter(Class<T> type);

  /**
   * Retrieve class information for specified name.
   *
   * @param name is supplied name of type
   * @return type (class) this name represents (found via adapter lookup)
   */
  Class<?> getTypeByName(String name);

  /**
   * Retrieve name for specified class.
   *
   * @param type is supplied type
   * @return name supplied by adapter for given class
   */
  String getName(Class<?> type);

  /**
   * Sql type associated with supplied Java type.
   *
   * @param type is Java type we ask about
   * @return sql type associated with this adapter
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
   * Get literal corresponding to given value.
   *
   * @param value is value literal should be created for
   * @param type  is Java type of literal
   * @param <T>   represents Java type of literal for compile-time type safety
   * @return string representing literal for given value
   */
  <T> String getLiteral(@Nullable T value, Class<T> type);

  /**
   * Get literal corresponding to given value.
   *
   * @param value is value literal should be created for
   * @return string representing literal for given value
   */
  String getLiteral(Object value);

  /**
   * Append literal corresponding to given value to supplied builder.
   *
   * @param builder is {@code StringBuilder} literal should be appended to
   * @param value is value literal should be created for
   * @param type  is Java type of literal
   * @param <T>   represents Java type of literal for compile-time type safety
   */
  <T> void appendLiteral(StringBuilder builder, @Nullable T value, Class<T> type);

  /**
   * Append literal corresponding to given value.
   *
   * @param builder is {@code StringBuilder} literal should be appended to
   * @param value is value literal should be created for
   */
  void appendLiteral(StringBuilder builder, Object value);
}
