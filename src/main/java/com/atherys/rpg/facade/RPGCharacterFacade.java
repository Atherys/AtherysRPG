package com.atherys.rpg.facade;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.service.DamageService;
import com.atherys.rpg.service.RPGCharacterService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.damage.DamageModifier;
import org.spongepowered.api.event.cause.entity.damage.DamageModifierTypes;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.IndirectEntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

@Singleton
public class RPGCharacterFacade {

    @Inject
    private DamageService damageService;

    @Inject
    private RPGCharacterService characterService;

    public void showPlayerAttributes(Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        Text.Builder attributeText = Text.builder();

        pc.getAttributes().forEach((type, value) -> {
            Text attribute = Text.builder()
                    .append(Text.of(type.getColor(), type.getName(), ": ", TextColors.RESET))
                    .append(Text.of(value, Text.NEW_LINE))
                    .build();

            attributeText.append(attribute);
        });

        player.sendMessage(attributeText.build());
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

        // source must be an instance of Equipable
        if (source instanceof Equipable) {
            ItemType weaponType = ((Equipable) source).getEquipped(EquipmentTypes.MAIN_HAND)
                    .map(ItemStack::getType)
                    .orElse(ItemTypes.NONE);

            RPGCharacter<?> attackerCharacter = characterService.getOrCreateCharacter(source);
            RPGCharacter<?> targetCharacter = characterService.getOrCreateCharacter(target);

            characterService.updateAttributes(attackerCharacter, source);
            characterService.updateAttributes(targetCharacter, target);

            event.setBaseDamage(damageService.getMeleeDamage(attackerCharacter, targetCharacter, weaponType));
        }
    }

    private void onIndirectDamage(DamageEntityEvent event, IndirectEntityDamageSource rootSource) {
        Entity source = rootSource.getIndirectSource();
        Entity target = event.getTargetEntity();

        // source must be an instance of Equipable
        if (!(source instanceof Equipable)) {
            return;
        }

        RPGCharacter<?> attackerCharacter = characterService.getOrCreateCharacter(source);
        RPGCharacter<?> targetCharacter = characterService.getOrCreateCharacter(target);

        characterService.updateAttributes(attackerCharacter, source);
        characterService.updateAttributes(targetCharacter, target);

        EntityType projectileType = rootSource.getSource().getType();

        event.setBaseDamage(damageService.getRangedDamage(attackerCharacter, targetCharacter, projectileType));
    }

}
