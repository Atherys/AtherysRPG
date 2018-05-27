package com.atherys.rpg.mutators;

import com.atherys.rpg.api.character.Mutator;
import com.atherys.rpg.api.character.RPGCharacter;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.item.ItemType;

/**
 * A Mutator which will grand the RPG character the ability to equip items of the given ItemType in their armor slots,
 * and will set the amount of defense the player will receive from wearing them.
 */
public class ArmorMutator implements Mutator {

    @Expose private ItemType itemType;
    @Expose private double defense;

    public ArmorMutator(ItemType itemType, double defense) {
        this.itemType = itemType;
        this.defense = defense;
    }

    @Override
    public void mutate(RPGCharacter character) {
        // TODO
    }
}
