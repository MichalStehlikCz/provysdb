package com.provys.db.query.elements;

/**
 * Immutable object representing sql statement, described in structured way. This statement can be
 * used to retrieve {@code SelectStatement} object for execution. If needed, it can also be cloned
 * to different dialect.
 */
@SuppressWarnings("CyclicClassDependency")// cyclic dependency on factory Context to support cloning
public interface Select extends Element<Select> {

}
