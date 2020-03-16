package com.provys.db.sqldb.sql;

import com.provys.db.sql.Function;
import java.util.Map;

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
}
