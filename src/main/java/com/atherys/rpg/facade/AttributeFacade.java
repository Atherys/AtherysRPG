package com.atherys.rpg.facade;

import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.command.exception.RPGCommandException;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.data.AttributeData;
import com.atherys.rpg.service.AttributeService;
import com.atherys.rpg.service.ExpressionService;
import com.atherys.rpg.service.RPGCharacterService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.item.inventory.equipment.EquipmentTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Consumer;

import static org.spongepowered.api.text.Text.NEW_LINE;
import static org.spongepowered.api.text.format.TextColors.*;
import static org.spongepowered.api.text.format.TextStyles.BOLD;

@Singleton
public class AttributeFacade {

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private RPGCharacterService characterService;

    @Inject
    private RPGMessagingFacade rpgMsg;

    @Inject
    private AttributeService attributeService;

    @Inject
    private ExpressionService expressionService;

    private List<Double> attributeCosts;

    public void init() {
        this.attributeCosts = new ArrayList<>();
        int attribute = 0;
        Expression expression = expressionService.getExpression(config.ATTRIBUTE_UPGRADE_COST);

        while (attribute <= config.ATTRIBUTE_MAX) {
            expression.setVariable("ATTRIBUTE", BigDecimal.valueOf(attribute));
            this.attributeCosts.add(expression.eval().doubleValue());
            attribute++;
        }
    }

    public void addPlayerAttribute(Player player, AttributeType attributeType, double amount) throws RPGCommandException {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (validateAttribute(pc.getBaseAttributes().getOrDefault(attributeType, config.ATTRIBUTE_MIN) + amount)) {
            characterService.addAttribute(pc, attributeType, amount);
        }
    }

    public void removePlayerAttribute(Player player, AttributeType attributeType, double amount) throws RPGCommandException {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (validateAttribute(pc.getBaseAttributes().getOrDefault(attributeType, config.ATTRIBUTE_MIN) - amount)) {
            characterService.removeAttribute(pc, attributeType, amount);
        }
    }

    private boolean validateAttribute(double amount) throws RPGCommandException {
        if (amount < config.ATTRIBUTE_MIN) {
            throw new RPGCommandException("A player cannot have attributes less than ", config.ATTRIBUTE_MIN);
        }

        if (amount > config.ATTRIBUTE_MAX) {
            throw new RPGCommandException("A player cannot have attributes bigger than ", config.ATTRIBUTE_MAX);
        }

        return true;
    }

    private double getCostForAttribute(Player player, AttributeType type, double amount) {
        double min = characterService.getOrCreateCharacter(player).getBaseAttributes().get(type);

        double cost = 0;
        for (int i = 0; i < amount; i++) {
             cost += attributeCosts.get((int) Math.round(min + i));
        }

        return cost;
    }

    public void purchaseAttribute(Player player, AttributeType type, double amount) throws RPGCommandException{
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        double expCost = getCostForAttribute(player, type, amount);

        // If the player has already reached their experience spending limit, cancel
        if (pc.getSpentExperience() + expCost > config.EXPERIENCE_SPENDING_LIMIT) {
            throw new RPGCommandException("You cannot go over the experience spending limit of ", config.EXPERIENCE_SPENDING_LIMIT, ".");
        } else {

            if (pc.getExperience() - expCost < config.EXPERIENCE_MIN) {
                throw new RPGCommandException("You do not have enough experience to increase this attribute.");
            }

            if (pc.getSpentAttributeExperience() + expCost > config.ATTRIBUTE_SPENDING_LIMIT) {
                throw new RPGCommandException(
                        "You cannot go over the attribute spending limit of ", config.ATTRIBUTE_SPENDING_LIMIT, "."
                );
            }

            double afterPurchase = pc.getBaseAttributes().getOrDefault(type, config.ATTRIBUTE_MIN) + amount;

            if (afterPurchase > config.ATTRIBUTE_MAX) {
                throw new RPGCommandException("You cannot have more than a base of ", config.ATTRIBUTE_MAX, " in this attribute.");
            }

            characterService.addAttribute(pc, type, amount);
            characterService.removeExperience(pc, expCost);
            characterService.addSpentAttributeExperience(pc, expCost);

            rpgMsg.info(player,
                    "You have added ", type.getColor(), (int) amount, " ",
                    type.getName(), DARK_GREEN,
                    " for ", GOLD, config.ATTRIBUTE_UPGRADE_COST, DARK_GREEN,
                    " experience."
            );
        }
    }

    public void resetPlayerAttributes(Player source) {
        characterService.resetCharacterAttributes(characterService.getOrCreateCharacter(source));
        rpgMsg.info(source, "Your attributes have been reset.");
    }

    public void enchantPlayerHeldItem(Player source, AttributeType attributeType, Double amount) throws RPGCommandException {
        ItemStack item = source.getEquipped(EquipmentTypes.MAIN_HAND).orElse(null);

        if (item == null) {
            throw new RPGCommandException("You must be holding an item in order to enchant it with an attribute.");
        }

        setItemAttributeValue(item, attributeType, amount);
        updateItemLore(item);
    }

    public void setItemAttributeValue(ItemStack item, AttributeType attributeType, Double amount) throws RPGCommandException {
        AttributeData data = item.get(AttributeData.class).orElseGet(AttributeData::new);
        data.setAttribute(attributeType, amount);
        if (!item.offer(data).isSuccessful()) {
            throw new RPGCommandException("Failed to set attribute " + attributeType.getId() + " on item.");
        }
    }

    public Map<AttributeType, Double> getAllAttributes(Entity entity) {
        return attributeService.getAllAttributes(entity);
    }

    public Map<AttributeType, Double> getBaseAttributes(Entity entity) {
        return attributeService.getBaseAttributes(characterService.getOrCreateCharacter(entity));
    }

    public Map<AttributeType, Double> getEquipmentAttributes(Entity entity) {
        return attributeService.getEquipmentAttributes(entity);
    }

    public void showPlayerAttributes(Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        List<Text> attributeTexts = new ArrayList<>();
        attributeTexts.add(Text.of(
                DARK_GRAY, "[]===[ ", GOLD, "Your Attributes", DARK_GRAY, " ]===[]"
        ));

        Map<AttributeType, Double> baseAttributes = pc.getBaseAttributes();
        Map<AttributeType, Double> buffAttributes = pc.getBuffAttributes();
        Map<AttributeType, Double> itemAttributes = attributeService.getEquipmentAttributes(player);

        config.ATTRIBUTE_ORDER.forEach(type -> {
            double base = baseAttributes.get(type);
            double buff = buffAttributes.getOrDefault(type, 0.0d);
            double item = itemAttributes.getOrDefault(type, 0.0d);

            int total = (int) Math.round(base + buff + item);

            Text hoverText = Text.of(
                    RED, BOLD, base, TextStyles.RESET, " base", Text.NEW_LINE,
                    BLUE, BOLD, item, TextStyles.RESET, " from equipment", Text.NEW_LINE,
                    GREEN, BOLD, buff, TextStyles.RESET, " from effects"
            );

            Text textTotal = Text.builder()
                    .append(Text.of(type.getColor(), BOLD, total))
                    .onHover(TextActions.showText(hoverText))
                    .build();

            Text upgradeButton1 = Text.EMPTY;
            Text upgradeButton5 = Text.EMPTY;

            if (type.isUpgradable()) {
                upgradeButton1 = getAddAttributeButton(pc, type, 1.0);
                upgradeButton5 = getAddAttributeButton(pc, type, 5.0);
            }

            Text attribute = Text.builder()
                    .append(upgradeButton1)
                    .append(Text.of(" "))
                    .append(upgradeButton5)
                    .append(Text.of(TextActions.showText(getAttributeDescription(type)), type.getColor(), type.getName(), ": ", TextColors.RESET, textTotal))
                    .build();

            attributeTexts.add(attribute);
        });

        attributeTexts.forEach(player::sendMessage);
    }

    private Text getAddAttributeButton(PlayerCharacter pc, AttributeType type, double amountToAdd) {

        Consumer<CommandSource> onClick = source -> {
            if (source instanceof Player) {
                try {
                    purchaseAttribute((Player) source, type, amountToAdd);
                } catch (RPGCommandException e) {
                    source.sendMessage(e.getText());
                }
            }
        };

        Text hoverText = Text.builder()
                .append(Text.of(DARK_GREEN, "Click to add ", (int) amountToAdd, type.getColor(), type.getName(), DARK_GREEN, " for ", GOLD, config.ATTRIBUTE_UPGRADE_COST, DARK_GREEN, " experience"))
                .build();

        return Text.builder()
                .append(Text.of(DARK_GRAY, "[", GOLD, "+", (int) amountToAdd, DARK_GRAY, "] "))
                .onHover(TextActions.showText(hoverText))
                .onClick(TextActions.executeCallback(onClick))
                .build();
    }

    private Text getAttributeDescription(AttributeType type) {
        return Text.of(type.getColor(), type.getName(), NEW_LINE, DARK_GREEN, config.ATTRIBUTE_DESCRIPTIONS.getOrDefault(type, Text.EMPTY));
    }

    /**
     * Updates the lore of an item to reflect the attributes contained within it
     *
     * @param stack The itemstack whose lore is to be updated
     */
    public void updateItemLore(ItemStack stack) {
        Optional<AttributeData> attributeData = stack.get(AttributeData.class);

        attributeData.ifPresent((data) -> {
            List<Text> lore = stack.get(Keys.ITEM_LORE).orElse(new ArrayList<>());

            data.asMap().forEach((type, value) -> {
                if (value == 0.0d) {
                    return;
                }

                for (int i = 0; i < lore.size(); i++) {
                    // TODO: Remove lore line if attribute is no longer present
                    // TODO: Update amount of attribute if already present
                    if (lore.get(i).toPlain().contains(type.getName())) {
                        lore.set(i, getAttributeLoreLine(type, value));
                        return;
                    }
                }

                lore.add(getAttributeLoreLine(type, value));
            });

            stack.offer(Keys.ITEM_LORE, lore);
        });
    }

    private double getFinalAttributeValue(PlayerCharacter pc, Player player, AttributeType type) {
        double finalValue = pc.getBaseAttributes().get(type);

        finalValue += attributeService.getHeldItemAttributes(player).getOrDefault(type, 0.0);
        finalValue += attributeService.getArmorAttributes(player).getOrDefault(type, 0.0);
        finalValue += attributeService.getBuffAttributes(pc).getOrDefault(type, 0.0);

        return finalValue;
    }

    private Text getAttributeLoreLine(AttributeType type, Double amount) {
        return Text.of(type.getColor(), type.getName(), ": ", TextColors.WHITE, amount, TextColors.RESET);
    }
}
