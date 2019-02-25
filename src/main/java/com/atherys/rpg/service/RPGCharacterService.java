package com.atherys.rpg.service;

import com.atherys.rpg.AtherysRPGConfig;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.character.ArmorEquipableCharacter;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.character.SimpleCharacter;
import com.atherys.rpg.repository.PlayerCharacterRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;

import java.util.*;

@Singleton
public class RPGCharacterService {

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private PlayerCharacterRepository repository;

    @Inject
    private AttributeService attributeService;

    private HashMap<UUID, RPGCharacter<? extends Living>> nonPlayerCharacters = new HashMap<>();

    public PlayerCharacter getOrCreateCharacter(Player player) {
        return repository.findById(player.getUniqueId()).orElseGet(() -> {
            PlayerCharacter pc = new PlayerCharacter(player, attributeService.getDefaultAttributes());
            repository.saveOne(pc);
            return pc;
        });
    }

    public RPGCharacter<? extends Living> getOrCreateCharacter(Living living) {
        RPGCharacter<? extends Living> npc = nonPlayerCharacters.get(living.getUniqueId());

        if (npc == null) {
            if (living instanceof ArmorEquipable) {
                npc = new ArmorEquipableCharacter(living, attributeService.getDefaultAttributes());
            } else {
                npc = new SimpleCharacter(living, attributeService.getDefaultAttributes());
            }

            nonPlayerCharacters.put(living.getUniqueId(), npc);
        }

        return npc;
    }

}
