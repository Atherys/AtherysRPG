package com.atherys.rpg.api.stat;

import com.atherys.rpg.api.util.Prototype;

public class Attribute implements Prototype<Attribute> {

    private AttributeType type;

    private double value;

    public AttributeType getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    @Override
    public Attribute copy() {
        return null;
    }
}
