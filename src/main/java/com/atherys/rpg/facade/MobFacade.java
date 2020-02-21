package com.atherys.rpg.facade;

import com.atherys.core.AtherysCore;
import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.config.MobConfig;
import com.atherys.rpg.config.MobsConfig;
import com.atherys.rpg.data.DamageExpressionData;
import com.atherys.rpg.service.AttributeService;
import com.atherys.rpg.service.RPGCharacterService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class MobFacade {
    @Inject
    private MobsConfig mobsConfig;

    @Inject
    private RPGCharacterService characterService;

    @Inject
    private AttributeService attributeService;

    @Inject
    private RPGCharacterFacade characterFacade;

    private Random random = new Random();

    public void onMobSpawn(SpawnEntityEvent event) {
        Set<Living> npcs = event.getEntities().stream()
                .filter(entity -> entity instanceof Living && !(entity instanceof Player))
                .map(entity -> (Living) entity)
                .collect(Collectors.toSet());

        Set<Player> players = event.getEntities().stream()
                .filter(entity -> entity instanceof Player)
                .map(entity -> (Player) entity)
                .collect(Collectors.toSet());

        // For all npcs, set their damage expression and max health
        npcs.forEach(living -> {
            getMobConfigFromLiving(living).ifPresent(mobConfig -> {
                assignEntityDamageExpression(living, new HashMap<>(mobConfig.DEFAULT_ATTRIBUTES), mobConfig.DAMAGE_EXPRESSION);
                characterFacade.assignEntityHealthLimit(living, mobConfig.HEALTH_LIMIT_EXPRESSION);
            });
        });

        // For all players, redirect to RPGCharacterFacade
        players.forEach(player -> {
            characterFacade.onPlayerSpawn(player);
        });
    }

    public void dropMobLoot(Living mob, Player killer) {
        // TODO: Add logic for if and when the player ( killer ) is in a party
        getMobConfigFromLiving(mob).map(config -> config.LOOT).ifPresent(lootConfigs -> lootConfigs.forEach((loot) -> {
            // Roll for drop chance is unsuccessful and this loot item will not drop
            if (random.nextDouble() > loot.DROP_RATE) {
                return;
            }

            // If there is currency loot, calculate it and award to player
            if (loot.CURRENCY != null) {
                // TODO
            }

            // If there is item loot, create the item and drop it at the location of the mob
            if (loot.ITEMS != null) {
                // TODO
            }

            // If there is experience loot, calculate it and award to player
            if (loot.EXPERIENCE != null) {
                // TODO
            }
        }));
    }

    public Optional<MobConfig> getMobConfigFromLiving(Living entity) {
        String name = entity.get(Keys.DISPLAY_NAME).orElse(Text.of(entity.getType().getId())).toPlain();
        return Optional.ofNullable(mobsConfig.MOBS.get(name));
    }

    private void assignEntityDamageExpression(Living entity, HashMap<AttributeType, Double> mobAttributes, String damageExpression) {
        Map<AttributeType, Double> attributes = attributeService.fillInAttributes(mobAttributes);

        characterService.getOrCreateCharacter(entity, attributes);
        entity.offer(new DamageExpressionData(damageExpression));
    }

}
