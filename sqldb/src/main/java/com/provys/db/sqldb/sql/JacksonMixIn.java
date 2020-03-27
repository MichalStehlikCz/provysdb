package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.provys.db.sql.BindVariable;

/**
 * Class defines mapping of Sql element classes to their MixIns for proper Jackson serialisation /
 * deserialization.
 */
public class JacksonMixIn extends SimpleModule {

  /**
   * Constructor for JacksonMixIn class - activates mix-in annotation interfaces.
   */
  @SuppressWarnings("nullness")
  public JacksonMixIn() {
    super("ProvysSqlElementMixIn");
    setMixInAnnotation(BindVariable.class, BindVariableMixIn.class);
    setMixInAnnotation(SqlExpression.class, SqlExpressionMixIn.class);
  }
}
