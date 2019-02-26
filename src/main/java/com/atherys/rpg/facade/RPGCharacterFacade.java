package com.atherys.rpg.facade;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.service.DamageService;
import com.atherys.rpg.service.RPGCharacterService;
import com.atherys.rpg.sources.AtherysEntityDamageSource;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Equipable;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.IndirectEntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;

@Singleton
public class RPGCharacterFacade {

    @Inject
    private DamageService damageService;

    @Inject
    private RPGCharacterService characterService;

    public void onDamage(DamageEntityEvent event, EntityDamageSource rootSource) {
        // If the event cause contains an AtherysEntityDamageSource object,
        // this means the event comes from a damage tick caused by this plugin.
        // In that case, stop, to prevent recursive event triggers
        if (event.getCause().containsType(AtherysEntityDamageSource.class)) {
            return;
        }

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

            event.setCancelled(true);

            damageService.damageMelee(attackerCharacter, targetCharacter, weaponType);
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

        event.setCancelled(true);

        damageService.damageRanged(attackerCharacter, targetCharacter, rootSource.getSource().getType());
    }

}
