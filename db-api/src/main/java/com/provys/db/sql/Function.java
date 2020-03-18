package com.provys.db.sql;

import com.provys.common.datatype.DtDateTime;
import com.provys.common.exception.InternalException;
import java.util.List;

/**
 * Supported functions.
 */
public enum Function {
  STRING_CHR(String.class, Integer.class),
  STRING_CONCAT(String.class, String.class, String.class),
  DATE_SYSDATE(DtDateTime.class),
  ANY_NVL(Object.class, 0, Object.class, Object.class);

  /**
   * Type that resulting expression will have. There is one special hack - in case function returns
   * same type as one of arguments (e.g. COALESCE), Object is used as return type and resultByType
   * is set to value >= 0.
   */
  private final Class<?> result;
  private final int resultAsArgument;
  /**
   * List of types of arguments. Special case are functions that have exactly two arguments and
   * the first argument has same type as result - system allows to repeat second argument of such
   * function, resulting in nested calls. This is also used for operators.
   */
  private final List<Class<?>> arguments;

  Function(Class<?> result, int resultAsArgument, Class<?>... arguments) {
    this.result = result;
    this.resultAsArgument = resultAsArgument;
    this.arguments = List.of(arguments);
    if (resultAsArgument > arguments.length - 1) {
      throw new InternalException(
          "Invalid function definition - type cannot be taken from an index that is after list "
              + "of arguments");
    }
  }

  Function(Class<?> result, Class<?>... arguments) {
    this(result, -1, arguments);
  }

  /**
   * Value of field result.
   *
   * @return value of field result
   */
  public Class<?> getResult() {
    return result;
  }

  /**
   * Value of field resultAsArgument.
   *
   * @return value of field resultAsArgument
   */
  public int getResultAsArgument() {
    return resultAsArgument;
  }

  /**
   * Value of field arguments.
   *
   * @return value of field arguments
   */
  public List<Class<?>> getArguments() {
    return arguments;
  }

  /**
   * Indicates if last argument is repeatable.
   *
   * @return if last argument is repeatable
   */
  public boolean lastArgumentRepeatable() {
    return (arguments.size() == 2) && (arguments.get(0) == result);
  }
}
