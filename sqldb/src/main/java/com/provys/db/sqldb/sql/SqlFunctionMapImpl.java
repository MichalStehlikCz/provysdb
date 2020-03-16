package com.provys.db.sqldb.sql;

import static com.provys.db.sql.Function.*;

import com.provys.db.sql.Function;
import java.util.Map;
import java.util.Objects;
import org.checkerframework.checker.nullness.qual.Nullable;

public class SqlFunctionMapImpl implements SqlFunctionMap {

  private static final SqlFunctionMapImpl DEFAULT = new SqlFunctionMapImpl(Map.of(
      STRING_CHR, "CHR({0})",
      STRING_CONCAT, "{0}||{1}",
      DATE_SYSDATE, "SYSDATE",
      ANY_NVL, "NVL({0}, {1})"
  ));

  public static SqlFunctionMapImpl getDefault() {
    return DEFAULT;
  }

  private final Map<Function, String> templateByFunction;

  public SqlFunctionMapImpl(Map<Function, String> templateByFunction) {
    this.templateByFunction = Map.copyOf(templateByFunction);
  }

  @Override
  public Map<Function, String> getTemplateByFunction() {
    return templateByFunction;
  }

  @Override
  public String getTemplate(Function function) {
    return Objects.requireNonNull(templateByFunction.get(function),
        () -> "Template not found for function " + function);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlFunctionMapImpl that = (SqlFunctionMapImpl) o;
    return templateByFunction.equals(that.templateByFunction);
  }

  @Override
  public int hashCode() {
    return templateByFunction.hashCode();
  }

  @Override
  public String toString() {
    return "SqlFunctionMapImpl{"
        + "templateByFunction=" + templateByFunction
        + '}';
  }
}
