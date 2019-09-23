package com.provys.provysdb.dbcontext.type;

import com.provys.common.exception.InternalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SqlTypeMap {

    private static final Logger LOG = LogManager.getLogger(SqlTypeMap.class);

    private final Map<Class<?>, SqlTypeAdapter<?>> adaptersByClass = new ConcurrentHashMap<>(10);

    <T> SqlTypeAdapter<T> getAdapter(Class<T> type) {
        var result = adaptersByClass.get(type);
        if (result == null) {
            if (type.getSuperclass() != null) {
                result = getAdapter(type.getSuperclass());
            } else {
                throw new InternalException(LOG, "No sql type adapter found for class " + type);
            }
        }
        //noinspection unchecked
        return  (SqlTypeAdapter<T>) result;
    }
}
