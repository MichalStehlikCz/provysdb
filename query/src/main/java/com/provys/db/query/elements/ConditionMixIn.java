package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * Class contains Jackson annotation for {@link Condition}. This way, we avoid cyclical
 * dependency between Condition and its implementations (even though it is only formal... if we
 * create another implementation of Condition, it will fail on serialisation)
 */
@SuppressWarnings("MarkerInterface")
@JsonRootName("CONDITION")
@JsonTypeInfo(use = Id.NAME, include = As.WRAPPER_OBJECT)
@JsonSubTypes({
    @JsonSubTypes.Type(value = ConditionOperation.class, name = "CONDOP"),
})
public interface ConditionMixIn {

}
