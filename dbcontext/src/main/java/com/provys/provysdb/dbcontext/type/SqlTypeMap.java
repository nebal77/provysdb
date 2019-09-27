package com.provys.provysdb.dbcontext.type;

import com.provys.common.exception.InternalException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SqlTypeMap {

    private static final Logger LOG = LogManager.getLogger(SqlTypeMap.class);

    private final Map<Class<?>, SqlTypeAdapter<?>> adaptersByClass = new ConcurrentHashMap<>(10);

    public <T> void addAdapter(Class<T> type, SqlTypeAdapter<T> adapter) {
        var old = adaptersByClass.put(type, Objects.requireNonNull(adapter));
        if ((old!=null) && (!adapter.equals(old))) {
            LOG.info("Replaced mapping of Sql type adapter for class {}; old {}, new {}", type::getCanonicalName,
                    old::toString, adapter::toString);
        }
    }

    @Nullable
    private SqlTypeAdapter getAdapterSuper(Class<?> type) {
        Class currentType = type;
        // first try to find adapter for type in superclasses
        while (currentType != null) {
            var result = adaptersByClass.get(currentType);
            if (result != null) {
                return result;
            }
            currentType = currentType.getSuperclass();
        }
        return null;
    }

    @Nullable
    private SqlTypeAdapter getAdapterInterface(Class<?> type) {
        Deque<Class> classes = new ArrayDeque<>();
        Class currentType = type;
        // first put superclasses in queue
        while (currentType != null) {
            classes.add(currentType);
            currentType = currentType.getSuperclass();
        }
        // next go through queue, check if there is adapter for it and register its superinterfaces
        while (!classes.isEmpty()) {
            currentType = classes.pollFirst();
            var result = adaptersByClass.get(currentType);
            if (result != null) {
                return result;
            }
            for (var iface : currentType.getInterfaces()) {
                classes.addLast(iface);
            }
        }
        return null;
    }

    @Nonnull
    public <T> SqlTypeAdapter<T> getAdapter(Class<T> type) {
        // first try to find in supertype... mostly successful so better to try it before heavy weight search
        SqlTypeAdapter result = getAdapterSuper(type);
        // next go through class hierarchy once more, but this time use interfaces
        if (result == null) {
            result = getAdapterInterface(type);
            if (result == null) {
                throw new InternalException(LOG, "No sql type adapter found for class " + type);
            }
        }
        //noinspection unchecked
        return  (SqlTypeAdapter<T>) result;
    }
}
