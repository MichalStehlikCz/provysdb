package com.provys.provysdb.dbcontext.type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SqlTypeMap {

    private final Map<Class<?>, SqlTypeAdapter<?>> adaptersByClass = new ConcurrentHashMap<>(10);

    <T> SqlTypeAdapter<T> getAdapter(Class<T> type) {
        var result = adaptersByClass.get(type);
        if (result == null) {
            if (type.getSuperclass() != null) {
                result = getAdapter(type.getSuperclass());
            } else {
                throw new
            }
        }
        //noinspection unchecked
        return  (SqlTypeAdapter<T>) result;
    }
}
