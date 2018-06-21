package com.atherys.rpg.mutators;

import com.atherys.rpg.api.character.Mutator;
import com.atherys.rpg.api.character.RPGCharacter;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.item.ItemType;

/**
 * A Mutator which will grand the RPG character the ability to equip items of the given ItemType in their main hand,
 * and will set the amount of damage the tree will deal with them.
 */
public class WeaponMutator implements Mutator {

    @Expose private ItemType itemType;
    @Expose private double damage;

    public WeaponMutator(ItemType itemType, double damage) {
        this.itemType = itemType;
        this.damage = damage;
    }

    @Override
    public void mutate(RPGCharacter character) {
        // TODO
    }
}
