package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * Class contains Jackson annotation for {@link SqlExpression}. This way, we avoid cyclical
 * dependency between SqlExpression and its implementations (even though it is only formal... if we
 * create another implementation of SqlExpression, it will fail on serialisation)
 */
@SuppressWarnings("MarkerInterface")
@JsonRootName("EXPRESSION")
@JsonTypeInfo(use = Id.NAME, include = As.WRAPPER_OBJECT)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SqlLiteral.class, name = "LITERAL"),
    @JsonSubTypes.Type(value = SqlLiteralNVarchar.class, name = "LITERALNVARCHAR"),
    @JsonSubTypes.Type(value = SqlFunction.class, name = "FUNCTION"),
    @JsonSubTypes.Type(value = SqlExpressionColumn.class, name = "COLUMN"),
    @JsonSubTypes.Type(value = SqlExpressionBind.class, name = "BINDEXPRESSION"),
})
public interface SqlExpressionMixIn {

}
