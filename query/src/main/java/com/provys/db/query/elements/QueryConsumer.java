package com.provys.db.query.elements;

import com.provys.db.query.functions.BuiltInFunction;
import com.provys.db.query.names.BindVariable;
import com.provys.db.query.names.NamePath;
import com.provys.db.query.names.SimpleName;
import java.util.Collection;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface QueryConsumer extends ExpressionConsumer, SelectConsumer, SelectClauseConsumer,
    SelectColumnConsumer {

  /**
   * Consume from clause based on supplied from elements.
   *
   * @param fromElements is collection of from elements this from clause is based on
   */
  void from(Collection<? extends FromElement> fromElements);

  /**
   * Consume from element, based on supplied table and optionally using supplied alias.
   *
   * @param tableName is name, identifying data source
   * @param alias     is alias to be used by columns to refer to this source
   */
  void fromTable(NamePath tableName, @Nullable SimpleName alias);

  /**
   * Consume from element, based on select statement.
   *
   * @param select is select statement from element is based on
   * @param alias  is alias used to refer to this from element
   */
  void fromSelect(SelectT<?> select, @Nullable SimpleName alias);

  /**
   * Consume from element, based on dual pseudo-table (or however no table in from is represented).
   *
   * @param alias is alias used to refer to this from element
   */
  void fromDual(@Nullable SimpleName alias);

  /**
   * Consume keyword. Used by tokens produced by parsing sql using tokenizer.
   *
   * @param keyword is keyword to be consumed
   */
  void keyword(String keyword);

  /**
   * Consume name (potentially segmented). Used by tokens, produced by parsing sql using tokenizer.
   *
   * @param namePath is name to be processed
   */
  void name(NamePath namePath);

  /**
   * Consume symbol. Used by tokens, produced by parsing sql using tokenizer.
   *
   * @param symbol is symbol to be processed
   */
  void symbol(String symbol);

  /**
   * Consume simple (rest of line) comment - {@code --} in SQL.
   *
   * @param comment is comment text
   */
  void simpleComment(String comment);

  /**
   * Consume multi-line comment - {code /*} in SQL.
   *
   * @param comment is comment text
   */
  void longComment(String comment);
}
