package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * Class contains Jackson annotation for {@link Select}. This way, we avoid cyclical
 * dependency between Select and its implementations (even though it is only formal... if
 * we create another implementation of Select, it will fail on serialisation)
 */
@SuppressWarnings("MarkerInterface")
@JsonRootName("SELECT")
@JsonTypeInfo(use = Id.NAME, include = As.WRAPPER_OBJECT)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SelectImpl.class, name = "SELECT"),
    @JsonSubTypes.Type(value = SelectT1.class, name = "SELECT1"),
    @JsonSubTypes.Type(value = SelectT2.class, name = "SELECT2"),
})
interface SelectMixIn {

}
