package com.atherys.rpg.facade;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.event.GainSkillEvent;
import com.atherys.rpg.api.event.LoseSkillEvent;
import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.command.exception.RPGCommandException;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.data.DamageExpressionData;
import com.atherys.rpg.service.*;
import com.atherys.skills.AtherysSkills;
import com.atherys.skills.api.event.ResourceEvent;
import com.atherys.skills.api.resource.ResourceUser;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.entity.damage.DamageModifierTypes;
import org.spongepowered.api.event.cause.entity.damage.DamageType;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.IndirectEntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import java.math.BigDecimal;
import java.util.*;

import static org.spongepowered.api.text.format.TextColors.*;

@Singleton
public class RPGCharacterFacade {

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private DamageService damageService;

    @Inject
    private HealingService healingService;

    @Inject
    private ExpressionService expressionService;

    @Inject
    private RPGCharacterService characterService;

    @Inject
    private AttributeFacade attributeFacade;

    @Inject
    private AttributeService attributeService;

    @Inject
    private RPGSkillFacade skillFacade;

    @Inject
    private SkillGraphFacade skillGraphFacade;

    @Inject
    private RPGMessagingFacade rpgMsg;

    public void showPlayerExperience(Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);
        rpgMsg.info(player, Text.of(DARK_GREEN, "Your current experience: ", GOLD, (int) pc.getExperience()));
    }

    public void addPlayerExperience(Player player, double amount) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (validateExperience(pc.getExperience() + amount)) {
            characterService.addExperience(pc, amount);
            rpgMsg.info(player, "Gained ", GOLD, (int) amount, DARK_GREEN, " experience.");
        }
    }

    public void removePlayerExperience(Player player, double amount) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (validateExperience(pc.getExperience() - amount)) {
            characterService.removeExperience(pc, amount);
        }
    }

    public void displayAllSkills(Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        List<String> ownedSkills = config.DISPLAY_ROOT_SKILL ? pc.getSkills() : pc.getSkills().subList(1, pc.getSkills().size());

        List<Text> messages = new ArrayList<>();
        messages.add(Text.of(DARK_GRAY, "[]====[ ", GOLD, "Your Skills", DARK_GRAY, " ]====[]"));

        for (String skillId : ownedSkills) {
            RPGSkill skill = skillFacade.getSkillById(skillId).get();
            messages.add(Text.of(DARK_GREEN, "- ", skillFacade.renderSkill(skill, player), " "));
        }

        messages.add(Text.of(DARK_GRAY, "[]==[ ", GOLD, "Available Skills", DARK_GRAY, " ]==[]"));

        for (RPGSkill skill : skillGraphFacade.getLinkedSkills(pc.getSkills())) {
            Text skillText = Text.builder()
                    .append(Text.of(DARK_GREEN, "- ", skillFacade.renderAvailableSkill(skill, player), " "))
                    .onClick(TextActions.executeCallback(source -> chooseSkillWithoutThrowing(player, skill.getId())))
                    .build();
            messages.add(skillText);
        }

        messages.forEach(player::sendMessage);
    }

    public void checkTreeOnLogin(Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (!skillGraphFacade.isPathValid(pc.getSkills())) {
            characterService.resetCharacter(pc);
            characterService.addSkill(pc, skillGraphFacade.getSkillGraphRoot());
            rpgMsg.info(player, "The server's skill tree has changed. Your attributes and skill tree have been reset.");
        }
    }

    public void checkAttributesDefaults(PlayerCharacter pc) {
        Map<AttributeType, Double> attributes = attributeService.getBaseAttributes(pc);

        for (AttributeType type : Sponge.getRegistry().getAllOf(AttributeType.class)) {
            if (!attributes.containsKey(type) || type.isResetOnLogin() || attributes.get(type) < type.getDefaultValue()) {
                characterService.setAttribute(pc, type, type.getDefaultValue());
            }
        }
    }

    public void chooseSkillWithoutThrowing(Player player, String skillId) {
        try {
            chooseSkill(player, skillId);
        } catch (RPGCommandException e) {
            player.sendMessage(e.getText());
        }
    }

    public void chooseSkill(Player player, String skillId) throws RPGCommandException {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);
        RPGSkill skill = getSkillById(skillId);

        if (pc.getSkills().contains(skillId)) {
            throw new RPGCommandException("You already have the skill ", skill.getName(), ".");
        }

        double cost = skillGraphFacade.getCostForSkill(skill, pc.getSkills()).orElseThrow(() -> {
            return new RPGCommandException("You do not have access to the skill ", skill.getName(), ".");
        });

        if (pc.getExperience() >= cost)  {
            if (pc.getSpentExperience() + cost > config.EXPERIENCE_SPENDING_LIMIT) {
                throw new RPGCommandException("You cannot go over the experience spending limit of ", config.EXPERIENCE_SPENDING_LIMIT, ".");
            }

            if (pc.getSpentSkillExperience() + cost > config.SKILL_SPENDING_LIMIT) {
                throw new RPGCommandException("You cannot go over the skill spending limit of ", config.SKILL_SPENDING_LIMIT, ".");
            }

            characterService.addSkill(pc, skill);
            characterService.removeExperience(pc, cost);
            characterService.addSpentSkillExperience(pc, cost);

            Sponge.getEventManager().post(new GainSkillEvent(player, skill));

            rpgMsg.info(player, "You have unlocked the skill ", GOLD, skill.getName(), ".");
        } else {
            throw new RPGCommandException("You do not have enough experience to unlock that skill.");
        }
    }

    public void removeSkill(Player player, String skillId) throws RPGCommandException {
        RPGSkill skill = getSkillById(skillId);
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (!pc.getSkills().contains(skillId)) {
            throw new RPGCommandException("You do not have the skill ", skillId, ".");
        }

        List<String> skillsCopy = new ArrayList<>(pc.getSkills());
        skillsCopy.remove(skillId);

        if (skillGraphFacade.isPathValid(skillsCopy)) {
            characterService.removeSkill(pc, skill);
            skillGraphFacade.getCostForSkill(skill, skillsCopy).ifPresent(cost -> {
                characterService.addExperience(pc, cost);
                characterService.addSpentSkillExperience(pc, -cost);
            });

            Sponge.getEventManager().post(new LoseSkillEvent(player, skill));

            rpgMsg.info(player, "You have removed the skill ", GOLD, skill.getName(), DARK_GREEN, ".");
        } else {
            throw new RPGCommandException("You cannot remove that skill.");
        }
    }

    public void resetSkills(Player player) {
        characterService.resetCharacterSkills(characterService.getOrCreateCharacter(player));
        rpgMsg.info(player, "Your skills have been reset.");
    }

    private RPGSkill getSkillById(String skillId) throws RPGCommandException {
        return skillFacade.getSkillById(skillId).orElseThrow(() -> {
            return new RPGCommandException("No skill with ID ", skillId, " found.");
        });
    }

    private boolean validateExperience(double experience) {
        if (experience < config.EXPERIENCE_MIN) {
            return false;
        }

        if (experience > config.EXPERIENCE_MAX) {
            return false;
        }

        return true;
    }

    public void resetPlayerCharacter(Player player) {
        characterService.resetCharacter(characterService.getOrCreateCharacter(player));
        rpgMsg.info(player, "Skills and attributes reset. Your experience has been returned to you.");
    }

    public void onResourceRegen(ResourceEvent.Regen event, Player player) {
        double amount = characterService.calcResourceRegen(attributeFacade.getAllAttributes(player));


        event.setRegenAmount(amount);
    }

    public void onDamage(DamageEntityEvent event, EntityDamageSource rootSource) {
        // The average time taken for these, once the JVM has had time to do some runtime optimizations
        // is 0.2 - 0.3 milliseconds
        if (rootSource instanceof IndirectEntityDamageSource) {
            onIndirectDamage(event, (IndirectEntityDamageSource) rootSource);
        } else {
            onDirectDamage(event, rootSource);
        }
    }

    public void onEnvironmentalDamage(DamageEntityEvent event, DamageType type, Living target) {
        //Remove damage modifiers
        resetDamageEvent(event);

        double damage;

        if (type == DamageTypes.FALL) {
            Expression expression = expressionService.getExpression(config.ENVIRONMENTAL_CALCULATIONS.get(type));
            float blocksFallen = event.getTargetEntity().get(Keys.FALL_DISTANCE).get();

            expression.setVariable("DISTANCE", BigDecimal.valueOf(blocksFallen));
            damage = expressionService.evalExpression(target, expression).doubleValue();
        } else {
            damage = expressionService.evalExpression(target, config.ENVIRONMENTAL_CALCULATIONS.get(type)).doubleValue();
        }

        event.setBaseDamage(damage);
    }

    private void onDirectDamage(DamageEntityEvent event, EntityDamageSource rootSource) {
        Entity source = rootSource.getSource();
        Entity target = event.getTargetEntity();

        ItemType weaponType = ItemTypes.NONE;

        if (source instanceof Equipable) {
            weaponType = ((Equipable) source).getEquipped(EquipmentTypes.MAIN_HAND)
                    .map(ItemStack::getType)
                    .orElse(ItemTypes.NONE);
        }

        // Remove all damage modifiers
        resetDamageEvent(event);

        Optional<DamageExpressionData> damageExpressionData = source.get(DamageExpressionData.class);
        if (damageExpressionData.isPresent()) {
            Map<AttributeType, Double> sourceAttributes = attributeFacade.getAllAttributes(source);
            Map<AttributeType, Double> targetAttributes = attributeFacade.getAllAttributes(target);

            event.setBaseDamage(Math.max(damageService.getDamageFromExpression(sourceAttributes, targetAttributes, damageExpressionData.get().getDamageExpression()), 0.0d));
            return;
        }

        // #15 - If damage source type is VOID, skip additional damage calculations // "Pure" Damage
        if (rootSource.getType() == DamageTypes.VOID) {
            return;
        }

        // If the damage source type is MAGIC, we do magic mitigation
        if (rootSource.getType() == DamageTypes.MAGIC) {
            Map<AttributeType, Double> targetAttributes = attributeFacade.getAllAttributes(target);

            event.setBaseDamage(Math.max(damageService.getMagicalDamageMitigation(targetAttributes, event.getBaseDamage()), 0.0f));
            return;
        }

        // If the damage source type is CUSTOM, we do physical mitigation
        if (rootSource.getType() == DamageTypes.CUSTOM) {
            Map<AttributeType, Double> targetAttributes = attributeFacade.getAllAttributes(target);

            event.setBaseDamage(Math.max(damageService.getPhysicalDamageMitigation(targetAttributes, event.getBaseDamage()), 0.0f));
            return;
        }

        // Otherwise, treat as basic attack
        Map<AttributeType, Double> attackerAttributes = attributeFacade.getAllAttributes(source);
        Map<AttributeType, Double> targetAttributes = attributeFacade.getAllAttributes(target);

        event.setBaseDamage(Math.max(damageService.getMeleeDamage(attackerAttributes, targetAttributes, weaponType), 0.0d));
    }

    private void onIndirectDamage(DamageEntityEvent event, IndirectEntityDamageSource rootSource) {
        Entity source = rootSource.getIndirectSource();
        Entity target = event.getTargetEntity();

        EntityType projectileType = rootSource.getSource().getType();

        // Remove all damage modifiers
        resetDamageEvent(event);

        double speed = rootSource.getSource().getVelocity().lengthSquared();

        Optional<DamageExpressionData> damageExpressionData = rootSource.getSource().get(DamageExpressionData.class);
        if (damageExpressionData.isPresent()) {
            Map<AttributeType, Double> sourceAttributes = attributeFacade.getAllAttributes(source);
            Map<AttributeType, Double> targetAttributes = attributeFacade.getAllAttributes(target);

            Map<String, BigDecimal> extra = new HashMap<String, BigDecimal>() {{
                put("SPEED", BigDecimal.valueOf(speed));
            }};

            event.setBaseDamage(Math.max(damageService.getDamageFromExpression(sourceAttributes, targetAttributes,
                                damageExpressionData.get().getDamageExpression(), extra), 0.0d));
            return;
        }

        // #15 - If damage source type is CUSTOM, skip additional damage calculations // "Pure" Damage
        if (rootSource.getType() == DamageTypes.VOID) {
            return;
        }

        if (rootSource.getType() == DamageTypes.MAGIC) {
            Map<AttributeType, Double> targetAttributes = attributeFacade.getAllAttributes(target);

            event.setBaseDamage(Math.max(damageService.getMagicalDamageMitigation(targetAttributes, event.getBaseDamage()), 0.0f));
            return;
        }

        if (rootSource.getType() == DamageTypes.CUSTOM) {
            Map<AttributeType, Double> targetAttributes = attributeFacade.getAllAttributes(target);

            event.setBaseDamage(Math.max(damageService.getPhysicalDamageMitigation(targetAttributes, event.getBaseDamage()), 0.0f));
            return;
        }

        Map<AttributeType, Double> attackerAttributes = attributeFacade.getAllAttributes(source);
        Map<AttributeType, Double> targetAttributes = attributeFacade.getAllAttributes(target);

        event.setBaseDamage(damageService.getRangedDamage(attackerAttributes, targetAttributes, projectileType, speed));
    }

    private void resetDamageEvent(DamageEntityEvent event) {
        event.getModifiers().forEach(damageFunction -> {
            if (damageFunction.getModifier().getType() != DamageModifierTypes.ABSORPTION) {
                event.setDamage(damageFunction.getModifier(), (base) -> 0);
            }
        });
    }

    public void onPlayerJoin(Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);
        boolean fill = false;

        if (!pc.hasJoined()) {
            pc.setHasJoined(true);
            fill = true;
        }

        setPlayerHealth(player, fill);
        setPlayerResourceLimit(player, fill);
        checkTreeOnLogin(player);
        checkAttributesDefaults(pc);
    }

    public void onPlayerRespawn(Player player) {
        setPlayerHealth(player, true);
        setPlayerResourceLimit(player, true);
    }

    public void setPlayerHealth(Player player, boolean fill) {
        assignEntityHealthLimit(player, config.HEALTH_LIMIT_CALCULATION, fill);
    }

    public void setPlayerResourceLimit(Player player, boolean fill) {
        double max = expressionService.evalExpression(player, config.RESOURCE_LIMIT_CALCULATION).doubleValue();
        ResourceUser user = AtherysSkills.getInstance().getResourceService().getOrCreateUser(player);

        user.setMax(max);
        if (fill) {
            user.fill();
        } else if (user.getCurrent() > user.getMax()) {
            user.fill();
        }
    }

    public void assignEntityHealthLimit(Living living, String healthLimitExpression, boolean fill) {
        double maxHP = expressionService.evalExpression(living, healthLimitExpression).doubleValue();

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

    public void setKeepInventoryOnPVP(DestructEntityEvent.Death event) {
        event.setKeepInventory(config.PLAYERS_KEEP_INVENTORY_ON_PVP);
    }

// Healing is currently not implementable as such due to Sponge
//    public void onHeal(ChangeDataHolderEvent.ValueChange event) {
//        if (event.getTargetHolder() instanceof Living) {
//            Living living = (Living) event.getTargetHolder();
//            RPGCharacter<?> character = characterService.getOrCreateCharacter(living);
//
//            double healthRegenAmount = healingService.getHealthRegenAmount(character);
//            System.out.println("New health regen amount: " + healthRegenAmount);
//
//            HealthData healthData = living.getHealthData();
//            healthData.transform(Keys.HEALTH, (value) -> value + healthRegenAmount);
//
//            event.proposeChanges(DataTransactionResult.successResult(healthData.health().asImmutable()));
//        }
//    }

}
