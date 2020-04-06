package com.provys.db.query.elements;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class contains Jackson annotation for {@link SelectT1}. This way, we formally avoid cyclical
 * dependency between SelectT1 and its implementation
 */
@SuppressWarnings("MarkerInterface")
@JsonSerialize(as = SelectT1Impl.class)
interface SelectT1MixIn {

}
