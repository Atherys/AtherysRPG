package com.atherys.rpg.facade;

import com.atherys.core.AtherysCore;
import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.command.exception.RPGCommandException;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.config.loot.CurrencyLootConfig;
import com.atherys.rpg.config.loot.ExperienceLootConfig;
import com.atherys.rpg.config.loot.ItemLootConfig;
import com.atherys.rpg.config.loot.LootConfig;
import com.atherys.rpg.config.mob.MobConfig;
import com.atherys.rpg.config.mob.MobsConfig;
import com.atherys.rpg.config.mob.SpawnersConfig;
import com.atherys.rpg.data.DamageExpressionData;
import com.atherys.rpg.integration.AtherysPartiesIntegration;
import com.atherys.rpg.service.AttributeService;
import com.atherys.rpg.service.MobService;
import com.atherys.rpg.service.RPGCharacterService;
import com.flowpowered.math.vector.Vector3d;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.RandomUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.EntitySnapshot;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.CauseStackManager;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;

import static org.spongepowered.api.text.format.TextColors.DARK_GREEN;
import static org.spongepowered.api.text.format.TextColors.GOLD;

@Singleton
public class MobFacade {

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private MobsConfig mobsConfig;

    @Inject
    private AttributeService attributeService;

    @Inject
    private RPGCharacterService characterService;

    @Inject
    private MobService mobService;

    @Inject
    private RPGCharacterFacade characterFacade;

    @Inject
    private ItemFacade itemFacade;

    @Inject
    private RPGMessagingFacade msg;

    private DecimalFormat decimalFormat = new DecimalFormat();

    private static Random random = new Random();

    public void spawnMob(String mobId, Player player) throws CommandException {
        EntityArchetype archetype = mobService.getMob(mobId).get();

        try (CauseStackManager.StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
            frame.pushCause(player);
            frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.PLACEMENT);
            Entity entity = archetype.apply(player.getLocation()).orElseThrow(() -> new RPGCommandException("Error."));
            entity.offer(Keys.PERSISTS, false);

            player.getWorld().spawnEntity(entity);
        }
    }

    // To HeadHunter: DO NOT WRITE BUGS THEN
    public void dropMobLoot(Living mob, Player killer) {
        Optional<MobConfig> lootOptional = getMobConfigFromLiving(mob);

        if (lootOptional.isPresent()) {
            int itemLimit = lootOptional.get().ITEM_DROP_LIMIT;
            Set<LootConfig> lootConfigs = lootOptional.get().LOOT;

            if (lootConfigs.isEmpty()) return;

            Set<Player> playersToReceiveLoot = new HashSet<>();
            playersToReceiveLoot.add(killer);

            if (Sponge.getPluginManager().isLoaded("atherysparties")) {
                playersToReceiveLoot = AtherysPartiesIntegration.fetchPlayerPartyMembersWithRadius(killer, config.MAX_REWARD_DISTANCE);
            }

            int playersToReceiveLootSize = playersToReceiveLoot.size();

            for (LootConfig lootConfig : lootConfigs) {
                double drop = random.nextDouble();
                // Roll for drop chance is unsuccessful and this loot item will not drop
                if (drop >= lootConfig.DROP_RATE) {
                    continue;
                }

                if (lootConfig.CURRENCY != null) {
                    playersToReceiveLoot.forEach(player -> awardPlayerCurrencyLoot(player, lootConfig.CURRENCY, playersToReceiveLootSize));
                }

                // If there is experience loot, calculate it and award to player
                if (lootConfig.EXPERIENCE != null) {
                    playersToReceiveLoot.forEach(player -> awardPlayerExperienceLoot(player, lootConfig.EXPERIENCE, playersToReceiveLootSize));
                }

                // If there is item loot, create the item and drop it at the location of the mob
                if (lootConfig.ITEM != null && itemLimit != 0) {
                    dropItemLoot(mob.getLocation(), lootConfig.ITEM);
                    itemLimit--;
                }
            }
        }
    }

    private void awardPlayerCurrencyLoot(Player player, CurrencyLootConfig config, int split) {
        AtherysCore.getEconomyService().flatMap(economyService -> economyService.getOrCreateAccount(player.getUniqueId())).ifPresent(playerAccount -> {
            Optional<Currency> currencyAward = Sponge.getRegistry().getType(Currency.class, config.CURRENCY);
            if (!currencyAward.isPresent()) {
                throw new RuntimeException("Could not find currency corresponding to id \"" + config.CURRENCY + "\"");
            } else {
                double amount = Math.floor((RandomUtils.nextDouble(config.MINIMUM, config.MAXIMUM) / split) * 100) / 100;
                playerAccount.deposit(
                        currencyAward.get(),
                        BigDecimal.valueOf(amount),
                        Cause.builder().append(AtherysRPG.getInstance()).build(Sponge.getCauseStackManager().getCurrentContext())
                );

                msg.info(player, "Gained ", GOLD, amount, DARK_GREEN, " ", currencyAward.get().getPluralDisplayName());
            }
        });
    }

    private void dropItemLoot(Location<World> dropLocation, ItemLootConfig config) {
        Optional<ItemStack> itemStack;

        // If an item group was configured, prefer that
        if (config.ITEM_GROUP != null) {
            itemStack = itemFacade.fetchRandomItemStackFromGroup(
                    config.ITEM_GROUP,
                    config.QUANTITY >= 1 ? config.QUANTITY : RandomUtils.nextInt(config.MINIMUM_QUANTITY, config.MAXIMUM_QUANTITY + 1)
            );
        // If no item group was configured, fetch a random item from the list of item ids
        } else {
            itemStack = itemFacade.createItemStack(
                    config.ITEM_IDS.get(RandomUtils.nextInt(0, config.ITEM_IDS.size())),
                    config.QUANTITY >= 1 ? config.QUANTITY : RandomUtils.nextInt(config.MINIMUM_QUANTITY, config.MAXIMUM_QUANTITY + 1)
            );
        }

        if (!itemStack.isPresent()) {
            throw new RuntimeException("Could not create an item stack from the item configuration \"" + config + "\"");
        } else {
            Entity groundItem = dropLocation.getExtent().createEntity(EntityTypes.ITEM, dropLocation.getPosition());
            groundItem.offer(Keys.REPRESENTED_ITEM, itemStack.get().createSnapshot());
            dropLocation.getExtent().spawnEntity(groundItem);
        }
    }

    private void awardPlayerExperienceLoot(Player player, ExperienceLootConfig config, int split) {
        characterFacade.addPlayerExperience(
                player,
                (Math.floor(RandomUtils.nextDouble(config.MINIMUM, config.MAXIMUM) * 100 ) / 100) / split
        );
    }

    public Optional<MobConfig> getMobConfigFromLiving(Living entity) {
        String name = entity.get(Keys.DISPLAY_NAME).orElse(Text.of(entity.getType().getId())).toPlain();
        return Optional.ofNullable(mobsConfig.MOBS.get(name));
    }
}
