package com.atherys.rpg.api.resource;

/**
 * An object representing a user of {@link Resource}s.
 */
public interface ResourceUser {

    /**
     * Get the resource this user is using
     *
     * @return
     */
    Resource getResource();

    /**
     * Sets the resource this user is using
     *
     * @param resource the resource to be set
     */
    void setResource(Resource resource);

    /**
     * Drain this ResourceUser's resource by the specified amount
     *
     * @param amount
     */
    default void drainResource(double amount) {
        getResource().drain(amount);
    }

    /**
     * Fill this ResourceUser's resource by the specified amount
     *
     * @param amount
     */
    default void fillResource(double amount) {
        getResource().fill(amount);
    }

}
