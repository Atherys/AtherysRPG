package com.atherys.rpg.api.skill;

import com.atherys.rpg.api.exception.CastException;
import com.atherys.rpg.api.util.LivingRepresentable;

import java.util.Map;
import java.util.Optional;

/**
 * An object which can carry, and therefore use, {@link Castable}s.
 * The properties of castables are dependent wholly on the {@link CastableCarrier}.
 * This means implementations must separate the two.
 */
public interface CastableCarrier extends LivingRepresentable {

    /**
     * Retrieves all castables usable by this CastableCarrier, with their properties.
     *
     * @return a map containing castables and their properties
     */
    Map<Castable, CastableProperties> getCastables();

    /**
     * Casts a castable with the properties stored within this carrier
     *
     * @param castable the castable
     * @param args     arguments
     * @return a {@link CastResult}
     */
    CastResult cast(Castable castable, long timestamp, String... args) throws CastException;

    /**
     * Checks whether or not this CastableCarrier contains the given Castable
     *
     * @param castable the castable to be checked for
     * @return Whether or not it is contained within this object
     */
    default boolean hasCastable(Castable castable) {
        return getCastables().containsKey(castable);
    }

    /**
     * Adds a castable with the specified properties to this CastableCarrier
     *
     * @param castable   The castable to be added
     * @param properties The castable's properties
     */
    default void putCastable(Castable castable, CastableProperties properties) {
        getCastables().put(castable, properties);
    }

    /**
     * Adds a {@link Castable} with its default properties to this object
     *
     * @param castable The castable to be added
     */
    default void addCastable(Castable castable) {
        getCastables().put(castable, castable.getDefaultProperties());
    }

    /**
     * Removes a castable along with its properties from this object
     *
     * @param castable The castable to be removed
     */
    default void removeCastable(Castable castable) {
        getCastables().remove(castable);
    }

    /**
     * Retrieves a castable by its id, if it is present on this object
     *
     * @param id The id to search for
     * @return An optional containing the castable, or empty if not found
     */
    default Optional<? extends Castable> getCastableById(String id) {
        for (Castable castable : getCastables().keySet()) if (castable.getId().equals(id)) return Optional.of(castable);
        return Optional.empty();
    }

    /**
     * Attempts to cast a Castable by its id, with the provided arguments
     *
     * @param castableId the id of the castable to be searched for and cast, if found
     * @param args       the args to be passed on
     * @return A {@link CastResult}
     * @throws CastException {@link CastErrors#noSuchSkill()} if no castable with that id was found
     */
    default CastResult castById(String castableId, String... args) throws CastException {
        Optional<? extends Castable> castable = getCastableById(castableId);

        if (castable.isPresent()) {
            return castable.get().cast(this, System.currentTimeMillis(), args);
        } else {
            throw CastErrors.noSuchSkill();
        }
    }

    /**
     * Retrieves the properties for the castable, if present, from this CastableCarrier
     *
     * @param castable The castable whose properties are to be retrieved
     * @return An optional containing the castable properties, or empty if the castable is not contained within this object.
     */
    default Optional<CastableProperties> getProperties(Castable castable) {
        return Optional.ofNullable(getCastables().get(castable));
    }

    /**
     * A utility method to quickly and dirtily retrieve a property from this CastableCarrier for the given castable.
     *
     * @param castable the castable whose property is to be retrieved
     * @param key      The key to be retrieved
     * @return the property, or null if none is found.
     */
    default Object getProperty(Castable castable, String key) {
        return getProperties(castable).map(castableProperties -> castableProperties.get(key));
    }

    /**
     * A utility method to quickly and dirtily retrieve a property from this CastableCarrier for the given castable.
     *
     * @param castable the castable whose property is to be retrieved
     * @param key      The key to be retrieved
     * @param clazz    The class it is to be retrieved as
     * @return the property, or null if none is found.
     */
    default <T> T getProperty(Castable castable, String key, Class<T> clazz) {
        return searchProperty(castable, key, clazz).orElse(null);
    }

    /**
     * A utility method for safely retrieving a property from this CastableCarrier for the given castable.
     *
     * @param castable the castable whose property is to be retrieved
     * @param key      The key to be retrieved
     * @return an optional containing the property, or an empty optional if none is found.
     */
    default Optional<Object> searchProperty(Castable castable, String key) {
        return getProperties(castable).flatMap(castableProperties -> Optional.ofNullable(castableProperties.get(key)));
    }

    /**
     * A utility method for safely retrieving a property from this CastableCarrier for the given castable.
     *
     * @param castable the castable whose property is to be retrieved
     * @param key      The key to be retrieved
     * @param clazz    The class it is to be retrieved as
     * @return an optional containing the property, or an empty optional if none is found.
     */
    default <T> Optional<T> searchProperty(Castable castable, String key, Class<T> clazz) {
        return getProperties(castable).flatMap(castableProperties -> castableProperties.get(key, clazz));
    }
}
