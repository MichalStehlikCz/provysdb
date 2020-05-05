package com.provys.db.query.elements;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class contains Jackson annotation for {@link SelectT1}. This way, we formally avoid cyclical
 * dependency between SelectT1 and its implementation
 */
@SuppressWarnings("MarkerInterface")
@JsonRootName("SELECT1")
@JsonSerialize(as = SelectT1Impl.class)
@JsonDeserialize(as = SelectT1Impl.class)
@JsonTypeInfo(use = Id.NONE) // Needed to prevent inheritance from SelectT
interface SelectT1MixIn {

}
