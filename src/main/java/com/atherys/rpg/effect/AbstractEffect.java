package com.atherys.rpg.effect;

import com.atherys.rpg.api.effect.Applyable;

import java.util.Objects;

public abstract class AbstractEffect implements Applyable {

    private String id;
    private String name;

    protected AbstractEffect(String id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractEffect that = (AbstractEffect) object;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
