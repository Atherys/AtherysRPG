package com.atherys.rpg.api.effect;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.skills.api.effect.ApplyableCarrier;
import com.atherys.skills.api.effect.TemporaryEffect;

import java.util.Map;
import java.util.stream.Collectors;

public class TemporaryAttributesEffect extends TemporaryEffect {
    private Map<AttributeType, Double> attributeIncreases;
    private Map<AttributeType, Double> attributeDecreases;

    public TemporaryAttributesEffect(String id, String name, long duration, Map<AttributeType, Double> attributes, boolean isPositive) {
        super(id, name, duration, isPositive);
        attributeIncreases = attributes;
        attributeDecreases = attributes.entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue() * -1));
    }

    @Override
    protected boolean apply(ApplyableCarrier<?> character) {
        character.getLiving().ifPresent(living -> {
            RPGCharacter<?> rpgCharacter = AtherysRPG.getInstance().getCharacterService().getOrCreateCharacter(living);
            AtherysRPG.getInstance().getAttributeFacade().mergeBuffAttributes(rpgCharacter, attributeIncreases);
        });
        return true;
    }

    @Override
    protected boolean remove(ApplyableCarrier<?> character) {
        character.getLiving().ifPresent(living -> {
            RPGCharacter<?> rpgCharacter = AtherysRPG.getInstance().getCharacterService().getOrCreateCharacter(living);
            AtherysRPG.getInstance().getAttributeFacade().mergeBuffAttributes(rpgCharacter, attributeDecreases);
        });

        return true;
    }
}
