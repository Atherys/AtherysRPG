package com.atherys.rpg.expression;

import com.udojava.evalex.AbstractFunction;

import java.math.BigDecimal;
import java.util.List;

public class ClampFunction extends AbstractFunction {

    public ClampFunction() {
        super("CLAMP", 3);
    }

    @Override
    public BigDecimal eval(List<BigDecimal> parameters) {
        BigDecimal value = parameters.get(0);
        BigDecimal min = parameters.get(1);
        BigDecimal max = parameters.get(2);

        if (value.compareTo(min) <= 0) {
            return min;
        }

        if (value.compareTo(max) >= 0) {
            return max;
        }

        return value;
    }
}
