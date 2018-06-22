package com.atherys.rpg.api.attribute;

import org.spongepowered.api.util.Identifiable;

import java.util.Optional;
import java.util.Set;

public interface AttributeCarrier extends Identifiable {

    /**
     * Retrieves all attributes carried by this AttributeCarrier.
     *
     * @return The list of Attributes applied to this carrier
     */
    Set<Attribute> getAttributes();

    /**
     * Checks if this AttributeCarrier has the given attribute applied to them
     *
     * @param attribute The attribute to test for
     * @return Whether or not the attribute was found.
     */
    default boolean hasAttribute(Attribute<?> attribute) {
        return getAttributes().contains(attribute);
    }

    /**
     * Adds an additional attribute to this AttributeCarrier
     *
     * @param attribute The attribute to be added
     * @return Whether or not the attribute was successfully added.
     */
    default boolean addAttribute(Attribute<?> attribute) {
        return getAttributes().add(attribute);
    }

    /**
     * Removes an attribute from this AttributeCarrier
     *
     * @param attribute The attribute to be removed
     * @return Whether or not the attribute was successfully removed.
     */
    default boolean removeAttribute(Attribute<?> attribute) {
        return getAttributes().remove(attribute);
    }

    /**
     * Retrieves an Attributed based on its String id.
     * Only 1 attribute is permitted to be placed on any AttributeCarrier with the same String id.
     *
     * @param id The String id
     * @return The attribute, if available. If not, an empty optional.
     */
    default Optional<? extends Attribute> getAttributeById(String id) {
        for (Attribute attribute : getAttributes()) if (attribute.getId().equals(id)) return Optional.of(attribute);
        return Optional.empty();
    }

}
