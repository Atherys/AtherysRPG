package com.atherys.rpg.facade;

import com.atherys.core.AtherysCore;
import com.atherys.core.utils.UserUtils;
import com.atherys.party.AtherysParties;
import com.atherys.party.entity.Party;
import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.config.*;
import com.atherys.rpg.data.DamageExpressionData;
import com.atherys.rpg.service.AttributeService;
import com.atherys.rpg.service.RPGCharacterService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.entity.SpawnEntityEvent;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.service.economy.Currency;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.CollectionUtils;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.math.BigDecimal;
import java.security.cert.CollectionCertStoreParameters;
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
    private AttributeFacade attributeFacade;

    @Inject
    private RPGCharacterService characterService;

    @Inject
    private AttributeService attributeService;

    @Inject
    private RPGCharacterFacade characterFacade;

    @Inject
    private ItemFacade itemFacade;

    @Inject
    private RPGMessagingFacade msg;

    private DecimalFormat decimalFormat = new DecimalFormat();

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
                characterFacade.assignEntityHealthLimit(living, mobConfig.HEALTH_LIMIT_EXPRESSION, true);
            });
        });
    }

    // To HeadHunter: DO NOT WRITE BUGS THEN
    public void dropMobLoot(Living mob, Player killer) {
        Optional<MobConfig> lootOptional = getMobConfigFromLiving(mob);

        if (lootOptional.isPresent()) {
            int itemLimit = lootOptional.get().ITEM_DROP_LIMIT;
            Set<LootConfig> lootConfigs = lootOptional.get().LOOT;

            if (lootConfigs.isEmpty()) return;

            Optional<Party> partyOptional = AtherysParties.getInstance().getPartyFacade().getPlayerParty(killer);
            Set<Player> playersToReceiveLoot = partyOptional.map(party -> {
                return party.getMembers().stream()
                        .map(uuid -> Sponge.getServer().getPlayer(uuid))
                        .filter(player -> {
                            boolean inRange = player.isPresent() && killer.getPosition().distanceSquared(player.get().getPosition()) < Math.pow(config.MAX_REWARD_DISTANCE, 2);
                            return inRange || player.isPresent() && player.get().getUniqueId() == killer.getUniqueId();
                        })
                        .map(Optional::get)
                        .collect(Collectors.toSet());
            })
            .orElse(Collections.singleton(killer));

            for (LootConfig lootConfig : lootConfigs) {
                double drop = random.nextDouble();
                // Roll for drop chance is unsuccessful and this loot item will not drop
                if (drop >= lootConfig.DROP_RATE) {
                    continue;
                }

                if (lootConfig.CURRENCY != null) {
                    playersToReceiveLoot.forEach(player -> awardPlayerCurrencyLoot(player, lootConfig.CURRENCY, playersToReceiveLoot.size()));
                }

                // If there is experience loot, calculate it and award to player
                // TODO: REFACTOR THIS! Either document the dependency, or refactor so it's optional.
                if (lootConfig.EXPERIENCE != null) {
                    playersToReceiveLoot.forEach(player -> awardPlayerExperienceLoot(player, lootConfig.EXPERIENCE, playersToReceiveLoot.size()));
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

    private void assignEntityDamageExpression(Living entity, HashMap<AttributeType, Double> mobAttributes, String damageExpression) {
        Map<AttributeType, Double> attributes = attributeService.fillInAttributes(mobAttributes);

        characterService.getOrCreateCharacter(entity, attributes);
        entity.offer(new DamageExpressionData(damageExpression));
    }

}
