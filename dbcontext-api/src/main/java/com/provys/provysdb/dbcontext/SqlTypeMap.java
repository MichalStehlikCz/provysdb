package com.provys.provysdb.dbcontext;

import com.provys.provysdb.dbcontext.SqlTypeAdapter;

import javax.annotation.Nonnull;

/**
 * Used to hold set of {link SqlTypeAdapter} and retrieve particular adapter for given class
 */
public interface SqlTypeMap {

    /**
     * Retrieve adapter for given class
     *
     * @param type is class for which adapter is to be retrieved
     * @param <T> is type parameter, binding supplied type and returned adapter
     * @return type adapter to be used with given class
     */
    @Nonnull
    <T> SqlTypeAdapter<T> getAdapter(Class<T> type);
}
