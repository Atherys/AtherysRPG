package com.atherys.rpg.api.stat;

import com.atherys.rpg.api.util.Prototype;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

@ConfigSerializable
public class Attribute implements Prototype<Attribute> {

    @Setting
    private AttributeType type;

    @Setting
    private double value;

    public Attribute(AttributeType type, double value) {
        this.type = type;
        this.value = value;
    }

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
