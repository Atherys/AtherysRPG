package com.atherys.rpg.facade;

import com.atherys.rpg.AtherysRPGConfig;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.command.exception.RPGCommandException;
import com.atherys.rpg.service.DamageService;
import com.atherys.rpg.service.RPGCharacterService;
import com.atherys.skills.api.event.ResourceRegenEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.IndirectEntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;

@Singleton
public class RPGCharacterFacade {

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private DamageService damageService;

    @Inject
    private RPGCharacterService characterService;

    @Inject
    private RPGMessagingFacade rpgMsg;

    public void showPlayerAttributes(Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        Text.Builder attributeText = Text.builder();

        pc.getAttributes().forEach((type, value) -> {
            Text attribute = Text.builder()
                    .append(Text.of(type.getColor(), type.getName(), ": ", TextColors.RESET))
                    .append(
                            Text.of(
                                    value, " ",
                                    getAddAttributeButton(type),
                                    Text.NEW_LINE
                            )
                    )
                    .build();

            attributeText.append(attribute);
        });

        player.sendMessage(attributeText.build());
    }

    public void showPlayerExperience(Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);
        rpgMsg.info(player, Text.of(TextColors.DARK_GREEN, "Your current experience: ", TextColors.GOLD, pc.getExperience()));
    }

    public void addPlayerExperience(Player player, double amount) throws RPGCommandException {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (validateExperience(pc.getExperience() + amount)) {
            characterService.addExperience(pc, amount);
        }
    }

    public void removePlayerExperience(Player player, double amount) throws RPGCommandException {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (validateExperience(pc.getExperience() - amount)) {
            characterService.removeExperience(pc, amount);
        }
    }

    private boolean validateExperience(double experience) throws RPGCommandException {
        if (experience < config.EXPERIENCE_MIN) {
            throw new RPGCommandException("A player cannot have experience less than ", config.EXPERIENCE_MIN);
        }

        if (experience > config.EXPERIENCE_MAX) {
            throw new RPGCommandException("A player cannot have experience bigger than ", config.EXPERIENCE_MAX);
        }

        return true;
    }

    public void addPlayerAttribute(Player player, AttributeType attributeType, double amount) throws RPGCommandException {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (validateAttribute(pc.getAttributes().getOrDefault(attributeType, config.ATTRIBUTE_MIN) + amount)) {
            characterService.addAttribute(pc, attributeType, amount);
        }
    }

    public void removePlayerAttribute(Player player, AttributeType attributeType, double amount) throws RPGCommandException {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (validateAttribute(pc.getAttributes().getOrDefault(attributeType, config.ATTRIBUTE_MIN) - amount)) {
            characterService.removeAttribute(pc, attributeType, amount);
        }
    }

    private boolean validateAttribute(double amount) throws RPGCommandException {
        if (amount < config.ATTRIBUTE_MIN) {
            throw new RPGCommandException("A player cannot have attributes less than ", config.ATTRIBUTE_MIN);
        }

        if (amount > config.ATTRIBUTE_MAX) {
            throw new RPGCommandException("A player cannot have attributes bigger than ", config.ATTRIBUTE_MAX);
        }

        return true;
    }

    public void setPlayerExperienceSpendingLimit(Player player, Double amount) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);
        characterService.setCharacterExperienceSpendingLimit(pc, amount);
    }

    public void purchaseAttribute(Player player, AttributeType type, double amount) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        double expAmount = amount * (pc.getExperience() + config.ATTRIBUTE_UPGRADE_COST);

        // If the player has already reached their experience spending limit, cancel
        if (pc.getExperienceSpendingLimit() < expAmount) {
            rpgMsg.error(player, "You cannot go over your experience spending limit of ", pc.getExperienceSpendingLimit());
        } else {
            characterService.addAttribute(pc, type, amount);
            characterService.removeExperience(pc, expAmount);

            rpgMsg.info(player,
                    "You have added ", 1.0, " ",
                    type.getColor(), type.getName(), TextColors.RESET,
                    " for ", TextColors.GOLD, config.ATTRIBUTE_UPGRADE_COST, TextColors.RESET,
                    " experience."
            );
        }
    }

    public void onResourceRegen(ResourceRegenEvent event, Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);
        double amount = characterService.getResourceRegenAmount(pc);
        event.setRegenAmount(amount);
    }

    public void onDamage(DamageEntityEvent event, EntityDamageSource rootSource) {
        // The average time taken for these, once the JVM has had time to do some runtime optimizations
        // is 0.1 - 0.2 milliseconds
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

        if ( source instanceof Equipable ) {
            weaponType = ((Equipable) source).getEquipped(EquipmentTypes.MAIN_HAND)
                    .map(ItemStack::getType)
                    .orElse(ItemTypes.NONE);
        }

        RPGCharacter<?> attackerCharacter = characterService.getOrCreateCharacter(source);
        RPGCharacter<?> targetCharacter = characterService.getOrCreateCharacter(target);

        characterService.updateAttributes(attackerCharacter, source);
        characterService.updateAttributes(targetCharacter, target);

        event.setBaseDamage(damageService.getMeleeDamage(attackerCharacter, targetCharacter, weaponType));
    }

    private void onIndirectDamage(DamageEntityEvent event, IndirectEntityDamageSource rootSource) {
        Entity source = rootSource.getIndirectSource();
        Entity target = event.getTargetEntity();

        RPGCharacter<?> attackerCharacter = characterService.getOrCreateCharacter(source);
        RPGCharacter<?> targetCharacter = characterService.getOrCreateCharacter(target);

        characterService.updateAttributes(attackerCharacter, source);
        characterService.updateAttributes(targetCharacter, target);

        EntityType projectileType = rootSource.getSource().getType();

        event.setBaseDamage(damageService.getRangedDamage(attackerCharacter, targetCharacter, projectileType));
    }

    private Text getAddAttributeButton(AttributeType type) {
        return Text.builder()
                .append(Text.of(TextColors.RESET, "[ ", TextColors.DARK_GREEN, "+", TextColors.RESET, " ]"))
                .onHover(TextActions.showText(Text.of("Click to add 1 ", type.getColor(), type.getName(), TextColors.RESET, " for ", config.ATTRIBUTE_UPGRADE_COST, " experience.")))
                .onClick(TextActions.executeCallback(source -> {
                    if (source instanceof Player) {
                        purchaseAttribute((Player) source, type, 1.0);
                    }
                }))
                .build();
    }
}
