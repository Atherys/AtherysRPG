package com.atherys.rpg.api;

import com.atherys.rpg.api.character.RPGCharacter;

import java.util.UUID;

public interface CooldownService {

    long getGlobalCooldown();

    void setOnGlobalCooldown(UUID uuid, long timestamp);

    boolean isOnGlobalCooldown(UUID uuid);

    boolean isOnGlobalCooldown(RPGCharacter character);

}
