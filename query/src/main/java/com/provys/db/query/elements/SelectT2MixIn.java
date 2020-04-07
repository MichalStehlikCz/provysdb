package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class contains Jackson annotation for {@link SelectT2}. This way, we formally avoid cyclical
 * dependency between SelectT2 and its implementation
 */
@SuppressWarnings("MarkerInterface")
@JsonSerialize(as = SelectT2Impl.class)
@JsonDeserialize(as = SelectT2Impl.class)
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from Select
interface SelectT2MixIn {

}
