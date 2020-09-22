package com.atherys.rpg.facade;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.command.exception.RPGCommandException;
import com.atherys.rpg.config.LootableConfig;
import com.atherys.rpg.config.LootablesConfig;
import com.atherys.rpg.config.ItemLootConfig;
import com.atherys.rpg.data.LootableData;
import com.atherys.rpg.data.RPGKeys;
import com.flowpowered.math.vector.Vector3i;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.tileentity.TileEntity;
import org.spongepowered.api.data.Property;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.block.InteractBlockEvent;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.InventoryProperty;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.World;

import java.util.*;

@Singleton
public class LootableFacade {

    @Inject
    private LootablesConfig lootablesConfig;

    @Inject
    private ItemFacade itemFacade;

    @Inject
    private RPGMessagingFacade messagingFacade;

    private Map<String, LootableConfig> lootableGroups = new HashMap<>();

    public void init() {
        lootableGroups.clear();

        lootablesConfig.LOOTABLES.forEach(lootableConfig -> {
            lootableGroups.put(lootableConfig.ID, lootableConfig);
        });
    }

    public void assignLootableIdToBlockPlayerIsLookingAt(Player source, String lootableId) throws RPGCommandException {

        if (!lootableGroups.containsKey(lootableId)) {
            throw new RPGCommandException("No such lootable id has been configured!");
        }

        BlockRay<World> blockRay = BlockRay.from(source)
                .distanceLimit(5)
                .skipFilter(worldBlockRayHit -> !source.getWorld().getTileEntity(worldBlockRayHit.getBlockPosition()).isPresent())
                .build();

        // If there is any block ray hits, fetch the first and ignore the rest
        if (blockRay.hasNext()) {
            BlockRayHit<World> next = blockRay.next();

            TileEntity tileEntity = source.getWorld().getTileEntity(next.getBlockPosition()).get();
            tileEntity.offer(new LootableData(lootableId));
            messagingFacade.info(source, "Tile Entity at coordinates ", next.getBlockPosition(), " has been assigned the lootable id of \"", lootableId, "\"");
        }
    }

    public void onBlockClick(Player player, BlockSnapshot targetBlock, InteractBlockEvent.Secondary event) {
        Optional<TileEntity> tileEntity = player.getWorld().getTileEntity(targetBlock.getPosition());

        // if this is not a tile entity, return
        if (!tileEntity.isPresent()) {
            return;
        }

        Optional<String> lootableId = tileEntity.get().get(RPGKeys.LOOTABLE_ID);

        // if it does not have lootable data, return
        if (!lootableId.isPresent()) {
            return;
        }

        LootableConfig lootableConfig = lootableGroups.get(lootableId.get());

        // if the lootable id is invalid, return
        if (lootableConfig == null) {
            return;
        }

        // if the block meets all criteria, cancel the event and open custom gui containing randomly selected items
        event.setCancelled(true);

        List<ItemStack> items = fetchRandomizedListOfItems(lootableConfig);

        // TODO: Limit how many times a player can loot a chest ( loot-limit-per-player ), lootable can be looted only so many times by any one player
        // TODO: Introduce time interval limit between looting ( loot-time-limit-seconds ), lootable can be looted once per duration
        // TODO: Introduce global loot limit ( global-loot-limit ), lootable can only be looted so many times total, regardless of player loot limits

        // TODO: Create Inventory GUI containing items and show to player
    }

    private List<ItemStack> fetchRandomizedListOfItems(LootableConfig lootableConfig) {
        List<ItemStack> items = new ArrayList<>();
        int numberOfItems = RandomUtils.nextInt(lootableConfig.MINIMUM_NUMBER_OF_ITEMS, lootableConfig.MAXIMUM_NUMBER_OF_ITEMS + 1);

        // concrete number of items takes precedence over randomly generated amount
        if (lootableConfig.NUMBER_OF_ITEMS >= 1) {
            numberOfItems = lootableConfig.NUMBER_OF_ITEMS;
        }

        for (int i = 0; i < numberOfItems; i++) {
            ItemStack item;

            // item group settings take precedence over individually configured item ids
            if (!StringUtils.isEmpty(lootableConfig.LOOT.ITEM_GROUP)) {

                // quantity settings take precedence over randomly selected quantity
                if (lootableConfig.LOOT.QUANTITY >= 1) {
                    item = itemFacade.fetchRandomItemStackFromGroup(
                            lootableConfig.LOOT.ITEM_GROUP,
                            lootableConfig.LOOT.MINIMUM_QUANTITY,
                            lootableConfig.LOOT.MINIMUM_QUANTITY
                    ).orElse(null);
                } else {
                    item = itemFacade.fetchRandomItemStackFromGroup(
                            lootableConfig.LOOT.ITEM_GROUP,
                            lootableConfig.LOOT.QUANTITY
                    ).orElse(null);
                }
            } else {
                String itemId = lootableConfig.LOOT.ITEM_IDS.get(RandomUtils.nextInt(0, lootableConfig.LOOT.ITEM_IDS.size()));

                if (lootableConfig.LOOT.QUANTITY >= 1) {
                    item = itemFacade.createItemStack(
                            itemId,
                            lootableConfig.LOOT.MINIMUM_QUANTITY,
                            lootableConfig.LOOT.MINIMUM_QUANTITY
                    ).orElse(null);
                } else {
                    item = itemFacade.createItemStack(
                            itemId,
                            lootableConfig.LOOT.QUANTITY
                    ).orElse(null);
                }
            }

            if (item != null) {
                items.add(item);
            }
        }

        return items;
    }
}
