package com.provys.provysdb.sqlbuilder;

import java.math.BigDecimal;

public interface NumberLiteral extends NumberExpression<NumberLiteral>,
    Literal<BigDecimal, NumberLiteral> {

}
