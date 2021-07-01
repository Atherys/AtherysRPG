package com.atherys.rpg.service;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.event.ChangeAttributeEvent;
import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.character.ArmorEquipableCharacter;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.character.SimpleCharacter;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.repository.PlayerCharacterRepository;
import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.resource.ResourceUser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
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
    private SkillGraphService skillGraphService;

    private HashMap<UUID, RPGCharacter<? extends Living>> nonPlayerCharacters = new HashMap<>();

    public PlayerCharacter getOrCreateCharacter(Player player) {
        return repository.findById(player.getUniqueId()).orElseGet(() -> {
            PlayerCharacter pc = new PlayerCharacter(player.getUniqueId());
            pc.setEntity(player);
            pc.addSkill(skillGraphService.getSkillGraphRoot().getId());
            pc.setExperience(config.EXPERIENCE_START);
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
            user.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, skillPermission, value ? Tristate.TRUE : Tristate.FALSE);
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

    /**
     * Increase the value of an attribute directly on a character
     * @param pc The Character to remove the attribute from
     * @param attributeType The attribute to increase
     * @param amount The amount to increase
     */
    public void addAttribute(PlayerCharacter pc, AttributeType attributeType, double amount) {
        pc.addCharacterAttribute(attributeType, amount);
        repository.saveOne(pc);
        Sponge.getEventManager().post(new ChangeAttributeEvent(pc));
    }

    /**
     * Set the value of an attribute directly on a Character
     * @param pc The Character to remove the attribute from
     * @param attributeType The attribute to set
     * @param amount The amount to set
     */
    public void setAttribute(PlayerCharacter pc, AttributeType attributeType, double amount) {
        pc.setCharacterAttribute(attributeType, amount);
        repository.saveOne(pc);
        Sponge.getEventManager().post(new ChangeAttributeEvent(pc));
    }

    /**
     * Remove an Attribute from a Character
     * @param pc The Character to remove the attribute from
     * @param attributeType The attribute to decrease
     * @param amount The amount to decrease by
     */
    public void removeAttribute(PlayerCharacter pc, AttributeType attributeType, double amount) {
        Double cur = pc.getCharacterAttributes().getOrDefault(attributeType, 0.0d);
        pc.setCharacterAttribute(attributeType, Math.max(0.0d, cur - amount));
        repository.saveOne(pc);
        Sponge.getEventManager().post(new ChangeAttributeEvent(pc));
    }

    public void mergeBuffAttributes(RPGCharacter<?> pc, Map<AttributeType, Double> attributes) {
        pc.mergeBuffAttributes(attributes);
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

        Sponge.getEventManager().post(new ChangeAttributeEvent(pc));
    }

    public double calcResourceRegen(Map<AttributeType, Double> attributes) {
        Expression expression = expressionService.getExpression(config.RESOURCE_REGEN_CALCULATION);

        expressionService.populateSourceAttributes(expression, attributes);

        return expression.eval().doubleValue();
    }

    public void resetCharacterSkills(PlayerCharacter pc) {
        double spentOnSkills = pc.getSpentSkillExperience();

        AtherysSkills.getInstance().getSkillService().getAllSkills().values().forEach(castable -> {
            setSkillPermission(pc, castable.getPermission(), false);
        });

        pc.getSkills().clear();
        pc.addSkill(skillGraphService.getSkillGraphRoot().getId());
        setSkillPermission(pc, skillGraphService.getSkillGraphRoot().getPermission(), true);

        pc.setSpentSkillExperience(0);
        pc.setSpentExperience(pc.getSpentExperience() - spentOnSkills);
        pc.setExperience(pc.getExperience() + spentOnSkills);

        repository.saveOne(pc);
    }

    public void resetCharacterAttributes(PlayerCharacter pc) {
        double spentOnAttributes = pc.getSpentAttributeExperience();

        pc.setCharacterAttributes(new HashMap<>());
        pc.setSpentAttributeExperience(0);
        pc.setSpentExperience(pc.getSpentExperience() - spentOnAttributes);
        pc.setExperience(pc.getExperience() + spentOnAttributes);

        repository.saveOne(pc);

        Sponge.getEventManager().post(new ChangeAttributeEvent(pc));
    }

    /**
     * Resets the characters attributes and skills, and gives back used experience.
     */
    public void resetCharacter(PlayerCharacter pc) {
        resetCharacterSkills(pc);
        resetCharacterAttributes(pc);
    }

    public void assignEntityResourceLimit(Living player, boolean fill) {
        double max = expressionService.evalExpression(player, config.RESOURCE_LIMIT_CALCULATION).doubleValue();
        ResourceUser user = AtherysSkills.getInstance().getResourceService().getOrCreateUser(player);

        user.setMax(max);
        if (fill) {
            user.fill();
        } else if (user.getCurrent() > user.getMax()) {
            user.fill();
        }
    }

    public void assignEntityHealthLimit(Living living, boolean fill) {
        double maxHP = expressionService.evalExpression(living, config.HEALTH_LIMIT_CALCULATION).doubleValue();

        DataTransactionResult maxHPResult = living.offer(Keys.MAX_HEALTH, maxHP);
        if (fill) {
            living.offer(Keys.HEALTH, maxHP);
        }

        if (!maxHPResult.isSuccessful()) {
            AtherysRPG.getInstance().getLogger().warn(
                    "Failed to set max health for entity {}, Max HP Result: {}",
                    living,
                    maxHPResult
            );
        }

        if (living.supports(Keys.HEALTH_SCALE)) {
            living.offer(Keys.HEALTH_SCALE, config.HEALTH_SCALING);
        }
    }

    public void assignEntityMovementSpeed(Living living) {
        if (!living.supports(Keys.WALKING_SPEED)) return;

        double newMovementSpeed = expressionService.evalExpression(living, config.MOVEMENT_SPEED_CALCULATION).doubleValue();
        double oldMovementSpeed = living.get(Keys.WALKING_SPEED).get();

        if (Math.abs(newMovementSpeed - oldMovementSpeed) >= 0.0001) {
            living.offer(Keys.WALKING_SPEED, newMovementSpeed);
        }
    }
}
