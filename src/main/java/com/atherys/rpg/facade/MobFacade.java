package com.atherys.rpg.facade;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.config.MobConfig;
import com.atherys.rpg.config.MobsConfig;
import com.atherys.rpg.data.DamageExpressionData;
import com.atherys.rpg.service.AttributeService;
import com.atherys.rpg.service.RPGCharacterService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

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

    public void onMobSpawn(SpawnEntityEvent event) {
        event.getEntities().stream()
                .filter(entity -> entity instanceof Living && !(entity instanceof Player))
                .map(entity -> (Living) entity)
                .forEach(living -> {
                    getMobConfigFromLiving(living).ifPresent(mobConfig -> {
                        Map<AttributeType, Double> attributes = attributeService.fillInAttributes(new HashMap<>(mobConfig.DEFAULT_ATTRIBUTES));

                        characterService.getOrCreateCharacter(living, attributes);
                        living.offer(new DamageExpressionData(mobConfig.DAMAGE_EXPRESSION));
                    });
                });
    }

    public void onMobDeath(Player killer, Living target) {
        getMobConfigFromLiving(target).ifPresent(mobConfig -> {
            characterFacade.addPlayerExperience(killer, mobConfig.EXPERIENCE);
        });
    }

    public Optional<MobConfig> getMobConfigFromLiving(Living entity) {
        String name = entity.get(Keys.DISPLAY_NAME).orElse(Text.of(entity.getType().getId())).toPlain();
        return Optional.ofNullable(mobsConfig.MOBS.get(name));
    }
}
