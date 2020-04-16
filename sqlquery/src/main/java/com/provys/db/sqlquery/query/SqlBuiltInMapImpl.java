package com.provys.db.sqlquery.query;

import static com.provys.db.query.functions.BuiltIn.ANY_NVL;
import static com.provys.db.query.functions.BuiltIn.COND_AND;
import static com.provys.db.query.functions.BuiltIn.COND_EQ_NONNULL;
import static com.provys.db.query.functions.BuiltIn.COND_EQ_NULLABLE;
import static com.provys.db.query.functions.BuiltIn.COND_GT_NONNULL;
import static com.provys.db.query.functions.BuiltIn.COND_GT_NULL_UNLIMITED;
import static com.provys.db.query.functions.BuiltIn.COND_GT_OR_EQ_NONNULL;
import static com.provys.db.query.functions.BuiltIn.COND_GT_OR_EQ_NULL_UNLIMITED;
import static com.provys.db.query.functions.BuiltIn.COND_LT_NONNULL;
import static com.provys.db.query.functions.BuiltIn.COND_LT_NULL_UNLIMITED;
import static com.provys.db.query.functions.BuiltIn.COND_LT_OR_EQ_NONNULL;
import static com.provys.db.query.functions.BuiltIn.COND_LT_OR_EQ_NULL_UNLIMITED;
import static com.provys.db.query.functions.BuiltIn.COND_NOT;
import static com.provys.db.query.functions.BuiltIn.COND_NOT_EQ_NONNULL;
import static com.provys.db.query.functions.BuiltIn.COND_NOT_EQ_NULLABLE;
import static com.provys.db.query.functions.BuiltIn.COND_OR;
import static com.provys.db.query.functions.BuiltIn.DATE_SYSDATE;
import static com.provys.db.query.functions.BuiltIn.STRING_CHR;
import static com.provys.db.query.functions.BuiltIn.STRING_CONCAT;
import static com.provys.db.sqlquery.query.SqlBuilderPosition.COMPARE;
import static com.provys.db.sqlquery.query.SqlBuilderPosition.EXPR_ADD;
import static com.provys.db.sqlquery.query.SqlBuilderPosition.EXPR_BRACKET;
import static com.provys.db.sqlquery.query.SqlBuilderPosition.IN_BRACKET;

import com.provys.db.query.functions.BuiltIn;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class SqlBuiltInMapImpl implements SqlBuiltInMap {

  private static final SqlBuiltInMapImpl DEFAULT;

  static {
    var map = new HashMap<BuiltIn, SqlBuiltInAppender>(19);
    map.put(STRING_CHR, new SqlTemplateAppender("CHR({0})", EXPR_BRACKET, IN_BRACKET));
    map.put(STRING_CONCAT, SqlRecursiveAppender.forTemplate("{0}||{1}", EXPR_ADD, EXPR_ADD));
    map.put(DATE_SYSDATE, new SqlTemplateAppender("SYSDATE", EXPR_BRACKET, IN_BRACKET));
    map.put(ANY_NVL, new SqlTemplateAppender("NVL({0}, {1})", EXPR_BRACKET, IN_BRACKET));

    map.put(COND_OR, new SqlCondOpAppender("OR", SqlBuilderPosition.COND_OR));
    map.put(COND_AND, new SqlCondOpAppender("AND", SqlBuilderPosition.COND_AND));
    map.put(COND_NOT, new SqlTemplateAppender("NOT {0}", SqlBuilderPosition.COND_NOT,
        SqlBuilderPosition.COND_NOT));

    map.put(COND_EQ_NONNULL, new SqlTemplateAppender("({0}={1})", EXPR_BRACKET, COMPARE));
    map.put(COND_EQ_NULLABLE, new SqlTemplateAppender("({0}={1})"
        + " OR (({0} IS NULL) AND ({1} IS NULL))", SqlBuilderPosition.COND_OR,
        SqlBuilderPosition.COND_OR));
    map.put(COND_NOT_EQ_NONNULL, new SqlTemplateAppender("({0}!={1})", EXPR_BRACKET, COMPARE));
    map.put(COND_NOT_EQ_NULLABLE, new SqlTemplateAppender("({0}!={1})"
        + " OR (({0} IS NULL) AND ({1} IS NOT NULL)) OR (({0} IS NOT NULL) AND ({1} IS NULL))",
        SqlBuilderPosition.COND_OR, SqlBuilderPosition.COND_OR));
    map.put(COND_LT_NONNULL, new SqlTemplateAppender("({0}<{1})", EXPR_BRACKET, COMPARE));
    map.put(COND_LT_NULL_UNLIMITED,
        new SqlTemplateAppender("({0}<{1}) OR ({1} IS NULL)", SqlBuilderPosition.COND_OR,
            SqlBuilderPosition.COND_OR));
    map.put(COND_LT_OR_EQ_NONNULL, new SqlTemplateAppender("({0}<={1})", EXPR_BRACKET, COMPARE));
    map.put(COND_LT_OR_EQ_NULL_UNLIMITED,
        new SqlTemplateAppender("({0}<={1}) OR ({1} IS NULL)", SqlBuilderPosition.COND_OR,
            SqlBuilderPosition.COND_OR));
    map.put(COND_GT_NONNULL, new SqlTemplateAppender("({0}>{1})", EXPR_BRACKET, COMPARE));
    map.put(COND_GT_NULL_UNLIMITED,
        new SqlTemplateAppender("({0}>{1}) OR ({1} IS NULL)", SqlBuilderPosition.COND_OR,
            SqlBuilderPosition.COND_OR));
    map.put(COND_GT_OR_EQ_NONNULL, new SqlTemplateAppender("({0}>={1})", EXPR_BRACKET, COMPARE));
    map.put(COND_GT_OR_EQ_NULL_UNLIMITED,
        new SqlTemplateAppender("({0}>={1}) OR ({1} IS NULL)", SqlBuilderPosition.COND_OR,
            SqlBuilderPosition.COND_OR));
    DEFAULT = new SqlBuiltInMapImpl(map);
  }

  public static SqlBuiltInMapImpl getDefault() {
    return DEFAULT;
  }

  private final Map<BuiltIn, SqlBuiltInAppender> appenderByBuiltIn;

  public SqlBuiltInMapImpl(Map<BuiltIn, SqlBuiltInAppender> appenderByBuiltIn) {
    this.appenderByBuiltIn = Map.copyOf(appenderByBuiltIn);
  }

  @Override
  public Map<BuiltIn, SqlBuiltInAppender> getAppenderByBuiltIn() {
    return appenderByBuiltIn;
  }

  @Override
  public SqlBuiltInAppender getAppender(BuiltIn builtIn) {
    var result = appenderByBuiltIn.get(builtIn);
    if (result == null) {
      throw new NoSuchElementException("Appender not found for built-in function / operator "
          + builtIn);
    }
    return result;
  }

  @Override
  public <B extends SqlBuilder<B>> void append(BuiltIn builtIn,
      List<? extends Consumer<? super B>> argumentAppend, B builder) {
    getAppender(builtIn).append(argumentAppend, builder);
  }

  @Override
  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SqlBuiltInMapImpl that = (SqlBuiltInMapImpl) o;
    return appenderByBuiltIn.equals(that.appenderByBuiltIn);
  }

  @Override
  public int hashCode() {
    return appenderByBuiltIn.hashCode();
  }

  @Override
  public String toString() {
    return "SqlBuiltInMapImpl{"
        + "appenderByBuiltIn=" + appenderByBuiltIn
        + '}';
  }
}
