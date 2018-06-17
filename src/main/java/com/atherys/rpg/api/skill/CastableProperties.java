package com.atherys.rpg.api.skill;

import org.spongepowered.api.text.Text;

import javax.annotation.Nullable;
import java.util.Map;
import java.util.Optional;

/**
 * An interface representing all properties a Castable may need to use during its existence.
 * All CastableProperties must be deep-copyable.
 */
public interface CastableProperties {

    String PERMISSION = "permission";
    String COOLDOWN = "cooldown";
    String COST = "cost";
    String MOUSE_COMBO = "mouse-combo";
    String DESCRIPTION = "description";

    String getPermission();

    double getResourceCost();

    long getCooldown();

    MouseButtonCombo getCombo();

    Text getDescription();

    Map<String,Object> getMeta();

    CastableProperties copy();

    /**
     * Mutates this instance by inheriting the properties of the provided object.
     * If these CastableProperties lack a property from the parent, they should deep-copy it.
     * @param parent The parent to copy properties from
     * @return The new, mutated instance
     */
    CastableProperties inheritFrom(CastableProperties parent);

    @Nullable
    default Object get(String key) {
        if (key.equals(PERMISSION)) return getPermission();
        if (key.equals(COST)) return getResourceCost();
        if (key.equals(MOUSE_COMBO)) return getCombo();
        if (key.equals(DESCRIPTION)) return getDescription();
        if (key.equals(COOLDOWN)) return getCooldown();
        return getMeta().get(key);
    }

    default <T> Optional<T> get(String key, Class<T> clazz) {
        Object object = get(key);
        if (object == null) return Optional.empty();
        if (clazz.isAssignableFrom(object.getClass())) return Optional.of((T) object);
        else return Optional.empty();
    }

    default Optional<String> getString(String key) {
        return get(key, String.class);
    }

    default Optional<Double> getDouble(String key) {
        return get(key, Double.class);
    }

    default Optional<Integer> getInteger(String key) {
        return get(key, Integer.class);
    }

    default Optional<Long> getLong(String key) {
        return get(key, Long.class);
    }

    default Optional<Float> getFloat(String key) {
        return get(key, Float.class);
    }

    default Optional<Byte> getByte(String key) {
        return get(key, Byte.class);
    }

    default Optional<Short> getShort(String key) {
        return get(key, Short.class);
    }
}
