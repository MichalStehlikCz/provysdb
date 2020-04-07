package com.provys.db.query.elements;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Class contains Jackson annotation for {@link Select}. This way, we avoid cyclical
 * dependency between Select and its implementations (even though it is only formal... if
 * we create another implementation of Select, it will fail on serialisation)
 */
@SuppressWarnings("MarkerInterface")
@JsonSerialize(as = SelectImpl.class)
@JsonDeserialize(as = SelectImpl.class)
interface SelectMixIn {

}
