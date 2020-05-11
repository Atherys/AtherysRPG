package com.atherys.rpg.listener;

import com.atherys.core.utils.EntityUtils;
import com.atherys.rpg.api.event.ChangeAttributeEvent;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.data.AttributeData;
import com.atherys.rpg.facade.MobFacade;
import com.atherys.rpg.facade.RPGCharacterFacade;
import com.atherys.rpg.service.RPGCharacterService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
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
import org.spongepowered.api.event.item.inventory.InteractItemEvent;
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
    }

    @Listener(order = Order.LAST)
    public void onEntitySpawn(SpawnEntityEvent event) {
        mobFacade.onMobSpawn(event);
    }

    @Listener(order = Order.LAST)
    public void onPlayerRespawn(RespawnPlayerEvent event) {
        characterFacade.setPlayerHealth(event.getTargetEntity(), true);
        characterFacade.setPlayerResourceLimit(event.getTargetEntity(), true);
    }

    @Listener
    public void onPlayerRightClick(InteractItemEvent.Secondary event, @Root Player player) {
        characterFacade.onRightClick(player);
    }

    @Listener
    public void onPlayerEquip(ChangeEntityEquipmentEvent.TargetPlayer event) {
        if (event.getTransaction().getFinal().equals(event.getTransaction().getOriginal())) return;

        boolean newHasAttributes = event.getTransaction().getFinal().get(AttributeData.Immutable.class).isPresent();
        boolean oldHadAttributes = event.getTransaction().getOriginal().get(AttributeData.Immutable.class).isPresent();

        if (newHasAttributes || oldHadAttributes) {
            characterFacade.setPlayerHealth(event.getTargetEntity(), false);
            characterFacade.setPlayerResourceLimit(event.getTargetEntity(), false);
        }
    }

    @Listener
    public void onChangeAttribute(ChangeAttributeEvent event, @Root PlayerCharacter pc) {
        Player player = pc.getEntity().orElse(Sponge.getServer().getPlayer(pc.getUniqueId()).orElse(null));

        if (player != null) {
            characterFacade.setPlayerHealth(player, false);
            characterFacade.setPlayerResourceLimit(player, false);
        }
    }
}
