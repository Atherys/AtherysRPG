package com.atherys.rpg.listener;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.model.character.PlayerCharacter;
import com.atherys.rpg.model.skill.DummySkill;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerListener {

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @Root Player player) {
        DummySkill dummySkill = new DummySkill();
        AtherysRPG.getSkillService().addPrototype(dummySkill);

        PlayerCharacter playerCharacter = AtherysRPG.getPlayerCharacterManager().getOrCreate(player);
        playerCharacter.addCastable(dummySkill);
    }

}
