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
    <T extends Resource> T getResource();

    /**
     * Drain this ResourceUser's resource by the specified amount
     *
     * @param amount
     */
    void drainResource(double amount);

    /**
     * Fill this ResourceUser's resource by the specified amount
     *
     * @param amount
     */
    void fillResource(double amount);

}
