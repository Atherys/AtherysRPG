package com.atherys.rpg.listener;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.model.character.PlayerCharacter;
import com.atherys.rpg.model.skill.FireballSkill;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerListener {

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @Root Player player) {

        AtherysRPG.getSkillService().addPrototype(new FireballSkill());

        PlayerCharacter playerCharacter = AtherysRPG.getPlayerCharacterManager().getOrCreate(player);
        playerCharacter.addCastable(new FireballSkill());
    }

}
