package com.provys.db.sqldb.sql;

import com.provys.db.sql.CodeBuilder;
import com.provys.db.sql.Function;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Supports translation of function expression to its sql representation via templates.
 */
public interface SqlFunctionMap {

  /**
   * Map this function map is based on. It is strongly recommended to use {@code getTemplate} to
   * retrieve mapping, but this function can be used to adapt existing map via {@link
   * SqlFunctionMapBuilder}
   *
   * @return map this function map is based on
   */
  Map<Function, String> getTemplateByFunction();

  /**
   * Get template usable for given function.
   *
   * @param function is function we want to get template for
   * @return template, with {i} used as placeholder for i-th argument
   */
  String getTemplate(Function function);

  /**
   * Append evaluated template to builder.
   *  @param function is function to be applied
   * @param argumentAppend is procedure that appends given argument to builder
   * @param builder is builder where whole expression should be appended
   */
  void append(Function function, List<? extends Consumer<CodeBuilder>> argumentAppend,
      CodeBuilder builder);
}
