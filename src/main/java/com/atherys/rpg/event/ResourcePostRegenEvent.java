package com.atherys.rpg.event;

import com.atherys.rpg.api.character.RPGCharacter;

public class ResourcePostRegenEvent extends ResourceRegenEvent {
    public ResourcePostRegenEvent(RPGCharacter character, double amount) {
        super(character, amount);
    }
}
