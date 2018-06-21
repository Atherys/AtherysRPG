package com.atherys.rpg.api.skill;

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
    String DESCRIPTION = "description";
    String MOUSE_COMBO = "mouse-combo";

    String getPermission();

    double getResourceCost();

    long getCooldown();

    MouseButtonCombo getCombo();

    String getDescription();

    Map<String, Object> getMeta();

    CastableProperties copy();

    /**
     * Mutates this instance by inheriting the properties of the provided object.
     * If these CastableProperties lack a property from the parent, they should deep-copy it.
     *
     * @param parent The parent to copy properties from
     * @return The new, mutated instance
     */
    CastableProperties inheritFrom(CastableProperties parent);

    /**
     * Retrieves the value behind the provided key
     *
     * @param key the key to look for
     * @return The value, or null if not found
     */
    @Nullable
    default Object get(String key) {
        if (key.equals(PERMISSION)) return getPermission();
        if (key.equals(COST)) return getResourceCost();
        if (key.equals(MOUSE_COMBO)) return getCombo();
        if (key.equals(COOLDOWN)) return getCooldown();
        if (key.equals(DESCRIPTION)) return getDescription();
        return getMeta().get(key);
    }

    /**
     * Retrieves the value behind the provided key, or a provided default value
     *
     * @param key          The key to look for
     * @param defaultValue The default value to return
     * @return The retrieved value, or the default, if the value behind the key is null OR not the same class as the provided default value.
     */
    @Nullable
    default Object getOrDefault(String key, Object defaultValue) {
        Object result = get(key);
        return result == null || !result.getClass().equals(defaultValue.getClass()) ? defaultValue : result;
    }

    /**
     * Retrieves the value behind the provided key
     *
     * @param key   The key to look for
     * @param clazz The class the result should be interpreted as
     * @return An optional containing the result, or empty, if the resulting value is null OR not the same class as required
     */
    default <T> Optional<T> get(String key, Class<T> clazz) {
        Object object = get(key);
        if (object == null) return Optional.empty();
        if (clazz.isAssignableFrom(object.getClass())) return Optional.of((T) object);
        else return Optional.empty();
    }

    /**
     * Retrieves the value behind the provided key, or a provided default
     *
     * @param key          The key to look for
     * @param defaultValue The default value
     * @param clazz        The class the result should be interpreted as
     * @return An optional containing the result, or the default value, if none is found OR is not the same class
     */
    default <T> Optional<T> getOrDefault(String key, T defaultValue, Class<T> clazz) {
        Object object = get(key);
        if (object == null) return Optional.of(defaultValue);
        if (clazz.isAssignableFrom(object.getClass())) return Optional.of((T) object);
        else return Optional.of(defaultValue);
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
