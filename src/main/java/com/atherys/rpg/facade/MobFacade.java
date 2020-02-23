package com.atherys.rpg.facade;

import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.command.exception.RPGCommandException;
import com.atherys.rpg.config.*;
import com.atherys.rpg.data.DamageExpressionData;
import com.atherys.rpg.data.RPGKeys;
import com.atherys.rpg.service.AttributeService;
import com.atherys.rpg.service.RPGCharacterService;
import com.flowpowered.math.vector.Vector3d;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.RandomUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.Item;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.event.entity.living.humanoid.player.RespawnPlayerEvent;
import org.spongepowered.api.event.filter.Getter;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializer;
import org.spongepowered.api.text.serializer.TextSerializers;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class MobFacade {
    @Inject
    private MobsConfig mobsConfig;

    @Inject
    private AttributeFacade attributeFacade;

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

        // For all npcs, set their damage expression and max health
        npcs.forEach(living -> {
            getMobConfigFromLiving(living).ifPresent(mobConfig -> {
                assignEntityDamageExpression(living, new HashMap<>(mobConfig.DEFAULT_ATTRIBUTES), mobConfig.DAMAGE_EXPRESSION);
                characterFacade.assignEntityHealthLimit(living, mobConfig.HEALTH_LIMIT_EXPRESSION);
            });
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
                awardPlayerCurrencyLoot(killer, loot.CURRENCY);
            }

            // If there is item loot, create the item and drop it at the location of the mob
            if (loot.ITEM != null) {
                dropItemLoot(mob.getLocation(), loot.ITEM);
            }

            // If there is experience loot, calculate it and award to player
            if (loot.EXPERIENCE != null) {
                awardPlayerExperienceLoot(killer, loot.EXPERIENCE);
            }
        }));
    }

    private void awardPlayerCurrencyLoot(Player player, CurrencyLootConfig config) {
        // TODO
    }

    private void dropItemLoot(Location<World> dropLocation, ItemLootConfig config) {
        Optional<ItemStackSnapshot> itemStack = createItemStackFromItemConfig(config);

        if (!itemStack.isPresent()) {
            throw new RuntimeException("Could not create an item stack from the item configuration \"" + config + "\"");
        } else {
            Entity groundItem = dropLocation.getExtent().createEntity(EntityTypes.ITEM, dropLocation.getPosition());
            groundItem.offer(Keys.REPRESENTED_ITEM, itemStack.get());
            dropLocation.getExtent().spawnEntity(groundItem);
        }
    }

    private void awardPlayerExperienceLoot(Player player, ExperienceLootConfig config) {
        characterFacade.addPlayerExperience(player, RandomUtils.nextDouble(config.MINIMUM, config.MAXIMUM));
    }

    private Optional<ItemStackSnapshot> createItemStackFromItemConfig(ItemLootConfig item) {
        ItemStack itemStack = ItemStack.builder()
                .itemType(item.ITEM_TYPE)
                .quantity(RandomUtils.nextInt(item.MINIMUM_QUANTITY, item.MAXIMUM_QUANTITY + 1))
                .build();

        // Convert and apply item display name
        Text displayName = TextSerializers.FORMATTING_CODE.deserialize(item.ITEM_NAME);
        itemStack.offer(Keys.DISPLAY_NAME, displayName);

        // Hide item attributes
        itemStack.offer(Keys.HIDE_ATTRIBUTES, item.HIDE_FLAGS);

        // Convert and apply enchantments
        List<Enchantment> enchantments = new ArrayList<>();
        item.ENCHANTMENTS.forEach((e, a) -> enchantments.add(Enchantment.of(e, a)));
        itemStack.offer(Keys.ITEM_ENCHANTMENTS, enchantments);

        // Apply RPG attributes
        item.ATTRIBUTES.forEach((t, a) -> {
            try {
                attributeFacade.setItemAttributeValue(itemStack, t, a);
            } catch (RPGCommandException e) {
                e.printStackTrace();
            }
        });

        // Convert and apply item lore
        List<Text> convertedLore = item.LORE.stream()
                .map(TextSerializers.FORMATTING_CODE::deserialize)
                .collect(Collectors.toList());
        itemStack.offer(Keys.ITEM_LORE, convertedLore);

        return Optional.of(itemStack.createSnapshot());
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
