package com.atherys.rpg.api.character;

import com.atherys.rpg.api.attribute.AttributeCarrier;
import com.atherys.rpg.api.effect.ApplyableCarrier;
import com.atherys.rpg.api.resource.ResourceUser;
import com.atherys.rpg.api.skill.CastableCarrier;

import java.util.UUID;

public interface RPGCharacter extends ApplyableCarrier, AttributeCarrier, ResourceUser, CastableCarrier {

    UUID getUUID();

}
