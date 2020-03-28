package com.provys.db.sqldb.sql;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * Class contains Jackson annotation for {@link SqlSelectColumn}. This way, we avoid cyclical
 * dependency between SqlSelectColumn and its implementations (even though it is only formal... if
 * we create another implementation of SqlSelectColumn, it will fail on serialisation)
 */
@SuppressWarnings("MarkerInterface")
@JsonRootName("SELECTCOLUMN")
@JsonTypeInfo(use = Id.NAME, include = As.WRAPPER_OBJECT)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SqlColumnExpression.class, name = "EXPRESSION"),
})
public interface SqlSelectColumnMixIn {

}
