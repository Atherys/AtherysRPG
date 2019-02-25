package com.atherys.rpg.character;

import com.atherys.rpg.api.stat.Attribute;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.Living;

import java.util.Set;

public class ArmorEquipableCharacter<T extends Living & ArmorEquipable> extends SimpleCharacter<T> {
    public ArmorEquipableCharacter(T entity, Set<Attribute> attributes) {
        super(entity, attributes);
    }
}
