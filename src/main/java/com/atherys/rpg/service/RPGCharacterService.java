package com.atherys.rpg.service;

import com.atherys.rpg.AtherysRPGConfig;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.character.ArmorEquipableCharacter;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.character.SimpleCharacter;
import com.atherys.rpg.repository.PlayerCharacterRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Equipable;
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
        if (living instanceof Player) {
            return getOrCreateCharacter((Player) living);
        }

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

    public <T extends Entity> RPGCharacter<?> getOrCreateCharacter(T entity) {
        if (entity instanceof Living) {
            return getOrCreateCharacter((Living) entity);
        } else {
            throw new IllegalArgumentException("Entity must be some sort of Living.");
        }
    }

    public void updateAttributes(RPGCharacter<?> character, Entity source) {
        if (source instanceof Equipable) {
            // Get the attributes of the held items
            Map<AttributeType, Double> result = attributeService.getHeldItemAttributes((Equipable) source);

            if (source instanceof ArmorEquipable) {
                // Merge the held item and armor attributes
                result = attributeService.mergeAttributes(result, attributeService.getArmorAttributes((ArmorEquipable) source));
            }

            // Merge all into the character's attributes
            attributeService.mergeAttributes(character.getAttributes(), result);
        }
    }

    public void addExperience(PlayerCharacter pc, double amount) {
        pc.setExperience(pc.getExperience() + amount);
        repository.saveOne(pc);
    }

    public void removeExperience(PlayerCharacter pc, double amount) {
        pc.setExperience(pc.getExperience() - amount);
        repository.saveOne(pc);
    }

    public void addAttribute(PlayerCharacter pc, AttributeType attributeType, double amount) {
        pc.getAttributes().merge(attributeType, amount, (v1, v2) -> v1 + v2);
        repository.saveOne(pc);
    }

    public void removeAttribute(PlayerCharacter pc, AttributeType attributeType, double amount) {
        pc.getAttributes().merge(attributeType, amount, (v1, v2) -> Math.abs(v1 - v2));
        repository.saveOne(pc);
    }

    public void setCharacterExperienceSpendingLimit(PlayerCharacter pc, Double amount) {
        pc.setExperienceSpendingLimit(amount);
        repository.saveOne(pc);
    }
}
