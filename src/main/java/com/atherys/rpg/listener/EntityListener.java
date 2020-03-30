package com.atherys.rpg.listener;

import com.atherys.core.utils.EntityUtils;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.facade.MobFacade;
import com.atherys.rpg.facade.RPGCharacterFacade;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.DamageEntityEvent;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;

@Singleton
public class EntityListener {

    @Inject
    private RPGCharacterFacade characterFacade;

    @Inject
    private MobFacade mobFacade;

    @Inject
    private AtherysRPGConfig config;

    @Listener
    public void onJoin(ClientConnectionEvent.Join event) {
        characterFacade.checkTreeOnLogin(event.getTargetEntity());
        characterFacade.setPlayerHealth(event.getTargetEntity());
        characterFacade.setPlayerResourceLimit(event.getTargetEntity(), true);
    }

    @Listener
    public void onDamage(DamageEntityEvent event, @Root EntityDamageSource source) {
        characterFacade.onDamage(event, source);
    }

    @Listener
    public void onEnvironmentalDamage(DamageEntityEvent event, @Root DamageSource source, @Getter("getTargetEntity") Living target) {
        if (config.ENVIRONMENTAL_CALCULATIONS.containsKey(source.getType())) {
            characterFacade.onEnvironmentalDamage(event, source.getType(), target);
        }
    }

    @Listener
    public void onEntityDeath(DestructEntityEvent.Death event, @Getter("getTargetEntity") Living target, @Root EntityDamageSource source) {
        EntityUtils.playerAttackedEntity(source).ifPresent(player -> mobFacade.dropMobLoot(target, player));
    }

    @Listener(order = Order.LAST)
    public void onEntitySpawn(SpawnEntityEvent event) {
        mobFacade.onMobSpawn(event);
    }

    @Listener(order = Order.LAST)
    public void onPlayerRespawn(RespawnPlayerEvent event) {
        characterFacade.setPlayerHealth(event.getTargetEntity());
        characterFacade.setPlayerResourceLimit(event.getTargetEntity(), true);
    }
}
