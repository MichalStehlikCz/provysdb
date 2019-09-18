package com.provys.provysdb.dbcontext;

import com.provys.common.exception.InternalException;
import com.provys.provysdb.sqlbuilder.BindVariable;
import com.provys.provysdb.sqlbuilder.Select;
import com.provys.provysdb.sqlbuilder.SqlName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Acts as executor for select statements prepared by {@link com.provys.provysdb.sqlbuilder.SelectBuilder}. Is
 * accessible via ProvysDbContext
 */
class SelectStatement {

    private static final Logger LOG = LogManager.getLogger(SelectStatement.class);

    @Nonnull
    private final ProvysDbContext dbContext;
    @Nonnull
    private final Select select;
    @Nonnull
    private final Map<SqlName, BindVariable> bindsByName;

    SelectStatement(ProvysDbContext dbContext, Select select) {
        this.dbContext = dbContext;
        this.select = select;
        this.bindsByName = select.getBinds().stream()
                .collect(Collectors.toConcurrentMap(BindVariable::getName, Function.identity()));
    }

    SelectStatement bindValue(SqlName name, @Nullable Object value) {
        var oldBindValue = bindsByName.get(name);
        if (oldBindValue == null) {
            throw new InternalException(LOG, "Bind variable does not exist: " + name);
        }
        if (value == null) {
            oldBindValue.getValue()
                    .ifPresent(old -> bindsByName.put(name, new BindVariable(name, oldBindValue.getType(), null)));
        } else if (value != oldBindValue.getValue().orElse(null)) {
            if (!oldBindValue.getType().isInstance(value)) {
                throw new InternalException(LOG, "Value has incorrect type for bind variable " + name +
                        "(bind type " + oldBindValue.getType() + ", supplied value type " + value + ")");
            }
            bindsByName.put(name, new BindVariable(name, oldBindValue.getType(), value));
        }
        return this;
    }
}
