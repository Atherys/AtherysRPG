package com.atherys.rpg.facade;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.command.exception.RPGCommandException;
import com.atherys.rpg.config.ItemConfig;
import com.atherys.rpg.config.ItemsConfig;
import com.google.common.collect.ImmutableMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.RandomUtils;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.enchantment.Enchantment;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.item.inventory.transaction.InventoryTransactionResult;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.serializer.TextSerializers;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class ItemFacade {

    @Inject
    private ItemsConfig config;

    @Inject
    private AttributeFacade attributeFacade;

    private Map<String, List<String>> groups = new HashMap<>();

    private Map<String, ItemStackSnapshot> items = new HashMap<>();

    public void init() {
        if (items == null || !items.isEmpty()) {
            items = new HashMap<>();
        }

        config.ITEMS.forEach(itemConfig -> {
            Optional<ItemStackSnapshot> snapshot = createItemStackSnapshotFromItemConfig(itemConfig);

            if (!snapshot.isPresent()) {
                AtherysRPG.getInstance().getLogger().error("Could not create item snapshot for item configuration with id \"" + itemConfig.ID + "\"");
                return;
            }

            items.put(itemConfig.ID, snapshot.get());

            List<String> itemGroup = groups.get(itemConfig.GROUP);
            if (itemGroup == null) {
                itemGroup = new ArrayList<>();
                itemGroup.add(itemConfig.ID);
                groups.put(itemConfig.GROUP, itemGroup);
            } else {
                itemGroup.add(itemConfig.ID);
            }
        });
    }

    public Map<String, ItemStackSnapshot> getCachedItems() {
        return ImmutableMap.copyOf(items);
    }

    public Optional<ItemStack> fetchRandomItemStackFromGroup(String groupId, int quantityMin, int quantityMax) {
        return fetchRandomItemStackFromGroup(groupId, RandomUtils.nextInt(quantityMin, quantityMax + 1));
    }

    public Optional<ItemStack> fetchRandomItemStackFromGroup(String groupId, int quantity) {
        List<String> itemGroup = groups.get(groupId);

        if (itemGroup == null || itemGroup.isEmpty()) {
            return Optional.empty();
        } else {
            return createItemStack(itemGroup.get(RandomUtils.nextInt(0, itemGroup.size())), quantity);
        }
    }

    public Optional<ItemStack> createItemStack(String itemId, int quantityMin, int quantityMax) {
        return createItemStack(itemId, RandomUtils.nextInt(quantityMin, quantityMax + 1));
    }

    public Optional<ItemStack> createItemStack(String itemId, int quantity) {
        ItemStackSnapshot snapshot = items.get(itemId);

        if (snapshot == null) {
            return Optional.empty();
        }

        ItemStack itemStack = snapshot.createStack();
        itemStack.setQuantity(quantity);

        return Optional.of(itemStack);
    }

    public void createAndGiveItemToPlayer(ItemStackSnapshot snapshot, int quantity, Player player) throws RPGCommandException {

        if (player == null) {
            throw new RPGCommandException("Must have a player as an arg if run by console.");
        }

        if (quantity < 1) {
            throw new RPGCommandException("Cannot give 0 or negative quantity of an item.");
        }

        ItemStack itemStack = snapshot.createStack();
        itemStack.setQuantity(quantity);

        InventoryTransactionResult inventoryTransactionResult = player.getInventory().offer(itemStack);

        if (inventoryTransactionResult.getType() != InventoryTransactionResult.Type.SUCCESS) {
            Entity item = player.getWorld().createEntity(EntityTypes.ITEM, player.getPosition());
            item.offer(Keys.REPRESENTED_ITEM, itemStack.createSnapshot());
            player.getWorld().spawnEntity(item);
        }
    }

    private Optional<ItemStackSnapshot> createItemStackSnapshotFromItemConfig(ItemConfig item) {
        ItemStack itemStack = ItemStack.builder()
                .itemType(item.ITEM_TYPE)
                .quantity(1)
                .build();

        // Convert and apply item display name
        Text displayName = TextSerializers.formattingCode('&').deserialize(item.ITEM_NAME);
        itemStack.offer(Keys.DISPLAY_NAME, displayName);

        // Hide item attributes
        itemStack.offer(Keys.HIDE_ATTRIBUTES, item.HIDE_FLAGS);
        itemStack.offer(Keys.HIDE_ENCHANTMENTS, item.HIDE_FLAGS);

        // Convert and apply enchantments
        List<Enchantment> enchantments = new ArrayList<>();
        item.ENCHANTMENTS.forEach((e, a) -> enchantments.add(Enchantment.of(e, a)));
        itemStack.offer(Keys.ITEM_ENCHANTMENTS, enchantments);

        // If durability is >= 1, set item durability
        if (item.DURABILITY >= 1) {
            itemStack.offer(Keys.ITEM_DURABILITY, item.DURABILITY);
        }

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

}
