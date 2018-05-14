package com.atherys.rpg.api;

/**
 * A common interface for objects which have the following 2 components:<br>
 * 1. A non-unique String id to identify different types of object<br>
 * 2. A human-friendly String name, to be displayed
 */
public interface Identifyable {

    /**
     * A key to distinguish distinct types of objects. Is not expected to be unique.
     *
     * @return The String id
     */
    String getId();

    /**
     * A human-friendly name for this object, most likely to be used in UI
     *
     * @return The human-friendly String name
     */
    String getName();

}
