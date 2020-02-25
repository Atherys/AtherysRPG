package com.atherys.rpg.facade;

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
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
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

    public void onMobSpawn(SpawnEntityEvent event) {
        Set<Living> npcs = event.getEntities().stream()
                .filter(entity -> entity instanceof Living && !(entity instanceof Player))
                .map(entity -> (Living) entity)
                .collect(Collectors.toSet());

        // For all npcs, set their damage expression and max health
        npcs.forEach(living -> {
            getMobConfigFromLiving(living).ifPresent(mobConfig -> {
                assignEntityDamageExpression(living, new HashMap<>(mobConfig.DEFAULT_ATTRIBUTES), mobConfig.DAMAGE_EXPRESSION);
                characterFacade.assignEntityHealthLimit(living, mobConfig.HEALTH_LIMIT_EXPRESSION);
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

    private void assignEntityDamageExpression(Living entity, HashMap<AttributeType, Double> mobAttributes, String damageExpression) {
        Map<AttributeType, Double> attributes = attributeService.fillInAttributes(mobAttributes);

        characterService.getOrCreateCharacter(entity, attributes);
        entity.offer(new DamageExpressionData(damageExpression));
    }
}
