package com.provys.db.query.elements;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class contains Jackson annotation for {@link SelectT2}. This way, we formally avoid cyclical
 * dependency between SelectT2 and its implementation
 */
@SuppressWarnings("MarkerInterface")
@JsonSerialize(as = SelectT2Impl.class)
interface SelectT2MixIn {

}
