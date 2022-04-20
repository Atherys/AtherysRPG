package com.atherys.rpg.service;

import com.atherys.rpg.api.event.RegisterMobEvent;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.config.mob.MobConfig;
import com.atherys.rpg.config.mob.MobsConfig;
import com.atherys.rpg.data.DamageExpressionData;
import com.flowpowered.math.vector.Vector3d;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.living.Living;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Singleton
public class MobService {
    @Inject
    private RPGCharacterService characterService;

    @Inject
    private MobsConfig mobsConfig;

    @Inject
    private AttributeService attributeService;

    private Map<String, EntityArchetype> mobsCache = new HashMap<>();

    public void init() {
        loadMobs();
    }

    private void loadMobs() {
        mobsConfig.MOBS.forEach((id, mobConfig) -> {
            Entity entity = Sponge.getServer().getWorld("world").get().createEntity(mobConfig.ENTITY_TYPE, Vector3d.ZERO);
            if (entity instanceof Living) {
                registerMob(id, (Living) entity);
            }
        });

        Sponge.getEventManager().post(new RegisterMobEvent(this));
    }

    public void registerMob(String id, Living living) {
        MobConfig mobConfig = mobsConfig.MOBS.get(id);
        Map<AttributeType, Double> attributes = new HashMap<>(mobConfig.DEFAULT_ATTRIBUTES);

        assignEntityDamageExpression(living, attributes, mobConfig.DAMAGE_EXPRESSION);
        characterService.assignEntityHealthLimit(living, true);
        characterService.assignEntityMovementSpeed(living);
        living.remove();

        mobsCache.put(id, living.createArchetype());
    }

    public Optional<EntityArchetype> getMob(String id) {
        return Optional.ofNullable(mobsCache.get(id));
    }

    public void assignEntityDamageExpression(Living entity, Map<AttributeType, Double> mobAttributes, String damageExpression) {
        Map<AttributeType, Double> attributes = attributeService.fillInAttributes(mobAttributes);

        characterService.getOrCreateCharacter(entity, attributes);
        entity.offer(new DamageExpressionData(damageExpression));
    }
}
