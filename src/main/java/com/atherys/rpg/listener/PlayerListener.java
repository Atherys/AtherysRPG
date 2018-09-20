package com.atherys.rpg.listener;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.skill.FireballSkill;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerListener {

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @Root Player player) {
        PlayerCharacter pc = AtherysRPG.getPlayerCharacterManager().getOrCreate(player);
        pc.addCastable(new FireballSkill());
    }

}
