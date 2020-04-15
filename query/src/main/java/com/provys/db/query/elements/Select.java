package com.provys.db.query.elements;

import com.google.errorprone.annotations.Immutable;

/**
 * Immutable object representing sql statement, described in structured way. This statement can be
 * used to retrieve {@code SelectStatement} object for execution.
 */
@Immutable
public interface Select extends SelectT<Select> {

}
