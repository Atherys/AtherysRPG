package com.atherys.rpg.service;

import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.character.ArmorEquipableCharacter;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.character.SimpleCharacter;
import com.atherys.rpg.repository.PlayerCharacterRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public class RPGCharacterService {

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private PlayerCharacterRepository repository;

    @Inject
    private AttributeService attributeService;

    @Inject
    private ExpressionService expressionService;

    private HashMap<UUID, RPGCharacter<? extends Living>> nonPlayerCharacters = new HashMap<>();

    public PlayerCharacter getOrCreateCharacter(Player player) {
        return repository.findById(player.getUniqueId()).orElseGet(() -> {
            PlayerCharacter pc = new PlayerCharacter(player.getUniqueId());
            pc.setEntity(player);
            pc.setBaseAttributes(attributeService.getDefaultAttributes());
            pc.setExperienceSpendingLimit(config.DEFAULT_EXPERIENCE_SPENDING_LIMIT);
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

    public void addExperience(PlayerCharacter pc, double amount) {
        pc.setExperience(pc.getExperience() + amount);
        repository.saveOne(pc);
    }

    public void removeExperience(PlayerCharacter pc, double amount) {
        pc.setExperience(pc.getExperience() - amount);
        repository.saveOne(pc);
    }

    public void addAttribute(PlayerCharacter pc, AttributeType attributeType, double amount) {
        pc.getBaseAttributes().merge(attributeType, amount, (v1, v2) -> v1 + v2);
        repository.saveOne(pc);
    }

    public void removeAttribute(PlayerCharacter pc, AttributeType attributeType, double amount) {
        pc.getBaseAttributes().merge(attributeType, amount, (v1, v2) -> Math.abs(v1 - v2));
        repository.saveOne(pc);
    }

    public void setCharacterExperienceSpendingLimit(PlayerCharacter pc, Double amount) {
        pc.setExperienceSpendingLimit(amount);
        repository.saveOne(pc);
    }

    public void addSpentExperience(PlayerCharacter pc, double amount) {
        pc.setSpentExperience(pc.getSpentExperience() + amount);
        repository.saveOne(pc);
    }

    public double calcResourceRegen(Map<AttributeType, Double> attributes) {
        Expression expression = expressionService.getExpression(config.RESOURCE_REGEN_CALCULATION);

        expressionService.populateAttributes(expression, attributes, "source");

        return expression.eval().doubleValue();
    }
<<<<<<< Updated upstream
=======

    /**
     * Resets the characters attributes & skills, and gives back used experience.
     */
    public void resetCharacter(PlayerCharacter pc) {
        pc.setBaseAttributes(attributeService.getDefaultAttributes());

        // Remove old permissions
        pc.getSkills().forEach(s -> {
            setSkillPermission(pc, s, false);
        });
        pc.setSkills(new ArrayList<>());

        double spent = pc.getSpentExperience();
        pc.setSpentExperience(0);
        pc.setExperience(pc.getExperience() + spent);

        repository.saveOne(pc);
    }
>>>>>>> Stashed changes
}
