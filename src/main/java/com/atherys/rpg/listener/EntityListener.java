package com.atherys.rpg.listener;

import com.atherys.core.utils.EntityUtils;
import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.event.ChangeAttributeEvent;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.data.AttributeMapData;
import com.atherys.rpg.facade.MobFacade;
import com.atherys.rpg.facade.RPGCharacterFacade;
import com.atherys.rpg.service.RPGCharacterService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.cause.entity.damage.source.DamageSource;
import org.spongepowered.api.event.cause.entity.damage.source.EntityDamageSource;
import org.spongepowered.api.event.entity.ChangeEntityEquipmentEvent;
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
    private RPGCharacterService characterService;

    @Inject
    private AtherysRPGConfig config;

    @Listener
    public void onJoin(ClientConnectionEvent.Join event) {
        characterFacade.onPlayerJoin(event.getTargetEntity());
    }

    @Listener
    public void onDamage(DamageEntityEvent event, @Root DamageSource source, @Getter("getTargetEntity") Living target) {
        characterFacade.onDamage(event, source, target);
    }

    @Listener
    public void onEntityDeath(DestructEntityEvent.Death event, @Getter("getTargetEntity") Living target, @Root EntityDamageSource source) {
        EntityUtils.playerAttackedEntity(source).ifPresent(player -> mobFacade.dropMobLoot(target, player));

        if (EntityUtils.getRootEntity(source).getType().equals(EntityTypes.PLAYER) && target.getType().equals(EntityTypes.PLAYER)) {
            characterFacade.setKeepInventoryOnPVP(event);
        }
    }

    @Listener(order = Order.LAST)
    public void onEntitySpawn(SpawnEntityEvent event) {
        mobFacade.onMobSpawn(event);
    }

    @Listener(order = Order.LAST)
    public void onPlayerRespawn(RespawnPlayerEvent event) {
        characterFacade.onPlayerRespawn(event.getTargetEntity());
    }

    @Listener
    public void onPlayerEquip(ChangeEntityEquipmentEvent.TargetPlayer event) {
        if (event.getTransaction().getFinal().equals(event.getTransaction().getOriginal())) return;

        boolean newHasAttributes = event.getTransaction().getFinal().get(AttributeMapData.Immutable.class).isPresent();
        boolean oldHadAttributes = event.getTransaction().getOriginal().get(AttributeMapData.Immutable.class).isPresent();

        if (newHasAttributes || oldHadAttributes) {
            characterFacade.assignEntityHealthLimit(event.getTargetEntity(), false);
            characterFacade.assignEntityResourceLimit(event.getTargetEntity(), false);
            characterFacade.assignEntityMovementSpeed(event.getTargetEntity());
        }
    }

    @Listener
    public void onChangeAttribute(ChangeAttributeEvent event, @Root RPGCharacter<?> character) {
        Living living;

        if (character instanceof PlayerCharacter) {
            PlayerCharacter pc = (PlayerCharacter) character;
            living = pc.getEntity().orElse(Sponge.getServer().getPlayer(pc.getUniqueId()).orElse(null));
        } else {
            living = character.getEntity().orElse(null);
        }

        if (living != null) {
            characterFacade.assignEntityHealthLimit(living, false);
            characterFacade.assignEntityResourceLimit(living, false);
            characterFacade.assignEntityMovementSpeed(living);
        }
    }
}
