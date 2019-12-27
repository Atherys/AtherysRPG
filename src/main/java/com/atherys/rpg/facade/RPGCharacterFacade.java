package com.atherys.rpg.facade;

import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.command.exception.RPGCommandException;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.data.DamageExpressionData;
import com.atherys.rpg.service.DamageService;
import com.atherys.rpg.service.ExpressionService;
import com.atherys.rpg.service.HealingService;
import com.atherys.rpg.service.RPGCharacterService;
import com.atherys.skills.api.event.ResourceRegenEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.entity.damage.DamageFunction;
import org.spongepowered.api.event.cause.entity.damage.DamageModifier;
import org.spongepowered.api.event.cause.entity.damage.DamageModifierTypes;
import org.spongepowered.api.event.cause.entity.damage.DamageTypes;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSources;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.IndirectEntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.service.permission.SubjectData;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.util.Tristate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.spongepowered.api.text.Text.NEW_LINE;
import static org.spongepowered.api.text.format.TextColors.DARK_GREEN;
import static org.spongepowered.api.text.format.TextColors.GOLD;

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
    private RPGSkillFacade skillFacade;

    @Inject
    private SkillGraphFacade skillGraphFacade;

    @Inject
    private RPGMessagingFacade rpgMsg;

    public void showPlayerExperience(Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);
        rpgMsg.info(player, Text.of(DARK_GREEN, "Your current experience: ", GOLD, pc.getExperience()));
    }

    public void addPlayerExperience(Player player, double amount) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (validateExperience(pc.getExperience() + amount)) {
            characterService.addExperience(pc, amount);
            rpgMsg.info(player, "Gained ", GOLD, amount, DARK_GREEN, " experience.");
        }
    }

    public void removePlayerExperience(Player player, double amount) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (validateExperience(pc.getExperience() - amount)) {
            characterService.removeExperience(pc, amount);
        }
    }

    public void displaySkills(Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        Text.Builder skills = Text.builder().append(Text.of("Skills", NEW_LINE));
        pc.getSkills().forEach(s -> {
            RPGSkill skill = skillFacade.getSkillById(s).get();
            skills.append(skillFacade.renderSkill(skill, player), NEW_LINE, NEW_LINE);
        });

        player.sendMessage(skills.build());
    }

    public void getAvailableSkills(Player player) throws RPGCommandException {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        Text.Builder skills = Text.builder().append(Text.of("Available Skills", NEW_LINE));
        skillGraphFacade.getLinkedSkills(pc.getSkills()).forEach(s -> {

            Text skillText = Text.builder()
                    .append(skillFacade.renderSkill(s, player))
                    .onClick(TextActions.executeCallback(source -> {
                        chooseSkillWithoutThrowing(player, s.getId());
                    }))
                    .onHover(TextActions.showText(Text.of(
                            "Click to pick up skill."
                    )))
                    .build();

            skills.append(skillText, NEW_LINE, NEW_LINE);
        });

        player.sendMessage(skills.build());
    }

    public void checkTreeOnLogin(Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (!skillGraphFacade.isPathValid(pc.getSkills())) {
            characterService.resetCharacter(pc);
            characterService.addSkill(pc, skillGraphFacade.getSkillGraphRoot().getId());
            rpgMsg.info(player, "The server's skill tree has changed. Your attributes and skill tree have been reset.");
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
            characterService.addSkill(pc, skillId);
            characterService.removeExperience(pc, cost);
            player.getSubjectData().setPermission(SubjectData.GLOBAL_CONTEXT, skillId, Tristate.TRUE);
            rpgMsg.info(player, "You have unlocked the skill ", GOLD, skill.getName(), ".");
        } else {
            throw new RPGCommandException("You do not have enough experience to unlock that skill.");
        }
    }

    public void removeSkill(Player player, String skillId) throws RPGCommandException {
        RPGSkill skill = getSkillById(skillId);
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (!pc.getSkills().contains(skillId)) {
            throw new RPGCommandException("You do not have the skill ", skillId);
        }

        List<String> skillsCopy = new ArrayList<>(pc.getSkills());
        skillsCopy.remove(skillId);

        if (skillGraphFacade.isPathValid(skillsCopy)) {
            characterService.removeSkill(pc, skillId);
            skillGraphFacade.getCostForSkill(skill, skillsCopy).ifPresent(cost -> {
                characterService.addExperience(pc, cost);
            });
            rpgMsg.info(player, "You have removed the skill ", GOLD, skill.getName(), DARK_GREEN, ".");
        } else {
            throw new RPGCommandException("You cannot remove that skill.");
        }
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

    public void setPlayerExperienceSpendingLimit(Player player, Double amount) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);
        characterService.setCharacterExperienceSpendingLimit(pc, amount);
    }

    public void resetPlayerCharacter(Player player) {
        characterService.resetCharacter(characterService.getOrCreateCharacter(player));
        rpgMsg.info(player, "Skills and attributes reset. Your experience has been returned to you.");
    }

    public void onResourceRegen(ResourceRegenEvent event, Player player) {
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
        event.getModifiers().forEach(damageFunction -> {
            event.setDamage(damageFunction.getModifier(), (base) -> 0);
        });

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
        event.getModifiers().forEach(damageFunction -> {
            event.setDamage(damageFunction.getModifier(), (base) -> 0);
        });

        Optional<DamageExpressionData> damageExpressionData = rootSource.getSource().get(DamageExpressionData.class);
        if (damageExpressionData.isPresent()) {
            Map<AttributeType, Double> sourceAttributes = attributeFacade.getAllAttributes(source);
            Map<AttributeType, Double> targetAttributes = attributeFacade.getAllAttributes(target);

            event.setBaseDamage(Math.max(damageService.getDamageFromExpression(sourceAttributes, targetAttributes, damageExpressionData.get().getDamageExpression()), 0.0d));
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

        event.setBaseDamage(damageService.getRangedDamage(attackerAttributes, targetAttributes, projectileType));
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
