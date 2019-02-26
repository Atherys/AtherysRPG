package com.atherys.rpg.character;

import com.atherys.rpg.api.stat.AttributeType;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.Living;

import java.util.Map;

public class ArmorEquipableCharacter<T extends Living & ArmorEquipable> extends SimpleCharacter<T> {
    public ArmorEquipableCharacter(T entity, Map<AttributeType, Double> attributes) {
        super(entity, attributes);
    }
}
