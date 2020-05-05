package com.provys.db.sqlquerybuilder;

/**
 * Marker interface, used to allow dependency injection based on type instead of having to use bean
 * names.
 */
@SuppressWarnings("MarkerInterface")
public interface AdminQueryBuilderFactory extends SqlQueryBuilderFactory {

}
