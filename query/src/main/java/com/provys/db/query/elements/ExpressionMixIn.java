package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.provys.db.query.functions.BuiltInFunction;

/**
 * Class contains Jackson annotation for {@link Expression}. This way, we avoid cyclical
 * dependency between Expression and its implementations (even though it is only formal... if we
 * create another implementation of Expression, it will fail on serialisation)
 */
@SuppressWarnings("MarkerInterface")
@JsonRootName("EXPRESSION")
@JsonTypeInfo(use = Id.NAME, include = As.WRAPPER_OBJECT)
@JsonSubTypes({
    @JsonSubTypes.Type(value = Literal.class, name = "LITERAL"),
    @JsonSubTypes.Type(value = BuiltInFunction.class, name = "FUNCTION"),
    @JsonSubTypes.Type(value = ExpressionColumn.class, name = "COLUMN"),
    @JsonSubTypes.Type(value = ExpressionBind.class, name = "BIND"),
    @JsonSubTypes.Type(value = Condition.class, name = "CONDITION")
})
public interface ExpressionMixIn {

}
