package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * Class contains Jackson annotation for {@link SelectClause}. This way, we avoid cyclical
 * dependency between SelectClause and its implementations (even though it is only formal... if
 * we create another implementation of SelectClause, it will fail on serialisation)
 */
@SuppressWarnings("MarkerInterface")
@JsonRootName("SELECTCLAUSE")
@JsonTypeInfo(use = Id.NAME, include = As.WRAPPER_OBJECT)
@JsonSubTypes({
    @JsonSubTypes.Type(value = SelectClauseColumns.class, name = "COLUMNS"),
})
public interface SelectClauseMixIn {

}
