package com.atherys.rpg.api.character;

import com.atherys.rpg.api.attribute.AttributeCarrier;
import com.atherys.rpg.api.character.player.ProgressionTree;
import com.atherys.rpg.api.effect.ApplyableCarrier;
import com.atherys.rpg.api.resource.ResourceUser;
import com.atherys.rpg.api.skill.CastableCarrier;

import java.util.Set;

public interface RPGCharacter extends ApplyableCarrier, AttributeCarrier, ResourceUser, CastableCarrier {

    Set<ProgressionTree> getTrees();

    void addTree(ProgressionTree tree);
}
