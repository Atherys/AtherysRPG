package com.atherys.rpg.api.attribute;

import java.util.Collection;
import java.util.Optional;

public interface AttributeCarrier {

    /**
     * Retrieves all attributes carried by this AttributeCarrier.
     *
     * @return The list of Attributes applied to this carrier
     */
    Collection<Attribute> getAttributes();

    /**
     * Adds an additional attribute to this AttributeCarrier
     *
     * @param attribute The attribute to be added
     */
    <T extends Attribute> void addAttribute(T attribute);

    /**
     * Retrieves an Attributed based on its String id.
     * Only 1 attribute is permitted to be placed on any AttributeCarrier with the same String id.
     *
     * @param id The String id
     * @return The attribute, if available. If not, an empty optional.
     */
    Optional<? extends Attribute> getAttributeById(String id);

}
