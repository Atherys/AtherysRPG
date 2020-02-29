package com.atherys.rpg.service;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.character.ArmorEquipableCharacter;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.character.SimpleCharacter;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.facade.SkillGraphFacade;
import com.atherys.rpg.repository.PlayerCharacterRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.entity.living.player.User;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.service.user.UserStorageService;
import org.spongepowered.api.util.Tristate;

import java.util.*;

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

    @Inject
    private SkillGraphFacade skillGraphFacade;

    private HashMap<UUID, RPGCharacter<? extends Living>> nonPlayerCharacters = new HashMap<>();

    public PlayerCharacter getOrCreateCharacter(Player player) {
        return repository.findById(player.getUniqueId()).orElseGet(() -> {
            PlayerCharacter pc = new PlayerCharacter(player.getUniqueId());
            pc.setEntity(player);
            pc.setBaseAttributes(attributeService.getDefaultAttributes());
            pc.setExperienceSpendingLimit(config.DEFAULT_EXPERIENCE_SPENDING_LIMIT);
            pc.addSkill(skillGraphFacade.getSkillGraphRoot().getId());
            repository.saveOne(pc);

            return pc;
        });
    }

    public RPGCharacter<? extends Living> getOrCreateCharacter(Living living, Map<AttributeType, Double> attributes) {
        if (living instanceof Player) {
            return getOrCreateCharacter((Player) living);
        }

        RPGCharacter<? extends Living> npc = nonPlayerCharacters.get(living.getUniqueId());

        if (npc == null) {
            if (living instanceof ArmorEquipable) {
                npc = new ArmorEquipableCharacter(living, attributes);
            } else {
                npc = new SimpleCharacter(living, attributes);
            }

            nonPlayerCharacters.put(living.getUniqueId(), npc);
        }

        return npc;
    }

    public <T extends Entity> RPGCharacter<?> getOrCreateCharacter(T entity) {
        if (entity instanceof Living) {
            return getOrCreateCharacter((Living) entity, attributeService.getDefaultAttributes());
        } else {
            throw new IllegalArgumentException("Entity must be some sort of Living.");
        }
    }

    public void setSkills(PlayerCharacter pc, List<String> skills) {
        pc.setSkills(skills);
        skills.forEach(s -> setSkillPermission(pc, s, true));
        repository.saveOne(pc);
    }

    public void addSkill(PlayerCharacter pc, RPGSkill skill) {
        pc.addSkill(skill.getId());
        setSkillPermission(pc, skill.getPermission(), true);
        repository.saveOne(pc);
    }

    public void removeSkill(PlayerCharacter pc, RPGSkill skill) {
        pc.removeSkill(skill.getId());
        setSkillPermission(pc, skill.getPermission(), false);
        repository.saveOne(pc);
    }

    private void setSkillPermission(PlayerCharacter pc, String skillPermission, boolean value) {
        getUser(pc).ifPresent(user -> {
            user.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, skillPermission, value ? Tristate.TRUE : Tristate.UNDEFINED);
        });
    }

    private Optional<User> getUser(PlayerCharacter pc) {
        return Sponge.getServiceManager().provideUnchecked(UserStorageService.class).get(pc.getUniqueId());
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

    public void addSpentSkillExperience(PlayerCharacter pc, double amount) {
        pc.setSpentExperience(pc.getSpentExperience() + amount);
        pc.setSpentSkillExperience(pc.getSpentSkillExperience() + amount);
        repository.saveOne(pc);
    }

    public void addSpentAttributeExperience(PlayerCharacter pc, double amount) {
        pc.setSpentExperience(pc.getSpentExperience() + amount);
        pc.setSpentAttributeExperience(pc.getSpentAttributeExperience() + amount);
        repository.saveOne(pc);
    }

    public double calcResourceRegen(Map<AttributeType, Double> attributes) {
        Expression expression = expressionService.getExpression(config.RESOURCE_REGEN_CALCULATION);

        expressionService.populateAttributes(expression, attributes, "source");

        return expression.eval().doubleValue();
    }

    public void resetCharacterSkills(PlayerCharacter pc) {
        double spentOnSkills = pc.getSpentSkillExperience();

        pc.getSkills().forEach(s -> {
            setSkillPermission(pc, s, false);
        });
        pc.setSkills(new ArrayList<>());

        pc.setSpentSkillExperience(0);
        pc.setSpentExperience(pc.getSpentExperience() - spentOnSkills);
        pc.setExperience(pc.getExperience() + spentOnSkills);

        repository.saveOne(pc);
    }

    public void resetCharacterAttributes(PlayerCharacter pc) {
        double spentOnAttributes = pc.getSpentAttributeExperience();

        pc.setBaseAttributes(attributeService.getDefaultAttributes());
        pc.setSpentAttributeExperience(0);
        pc.setSpentExperience(pc.getSpentExperience() - spentOnAttributes);
        pc.setExperience(pc.getExperience() + spentOnAttributes);

        repository.saveOne(pc);
    }

    /**
     * Resets the characters attributes and skills, and gives back used experience.
     */
    public void resetCharacter(PlayerCharacter pc) {
        resetCharacterSkills(pc);
        resetCharacterAttributes(pc);
    }
}
