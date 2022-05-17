package com.atherys.rpg.facade;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.stat.AttributeType;
import com.atherys.rpg.character.PlayerCharacter;
import com.atherys.rpg.command.exception.RPGCommandException;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.atherys.rpg.config.stat.AttributesConfig;
import com.atherys.rpg.data.AttributeMapData;
import com.atherys.rpg.service.AttributeService;
import com.atherys.rpg.service.ExpressionService;
import com.atherys.rpg.service.RPGCharacterService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.udojava.evalex.Expression;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.Equipable;
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

    private Map<AttributeType, Double> defaultAttributes;

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private AttributesConfig attributesConfig;

    @Inject
    private AttributeService attributeService;

    @Inject
    private RPGCharacterService characterService;

    @Inject
    private RPGMessagingFacade rpgMsg;

    @Inject
    private ExpressionService expressionService;

    public void init() {}

    /**
     * Adds a CharacterAttribute to a players character
     * @param player The Player to add the attribute for
     * @param attributeType The attribute to add
     * @param amount The amount to add
     * @throws RPGCommandException
     */
    public void addPlayerAttribute(Player player, AttributeType attributeType, double amount) throws RPGCommandException {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (validateCharacterAttribute(attributeType, pc.getCharacterAttributes().getOrDefault(attributeType, config.ATTRIBUTE_MIN) + amount)) {
            characterService.addAttribute(pc, attributeType, amount);
        }
    }

    /**
     * Removes a CharacterAttribute from a players character
     * @param player The player to remove the attribute for
     * @param attributeType The Attribute to remove
     * @param amount The amount to remove
     * @throws RPGCommandException
     */
    public void removePlayerAttribute(Player player, AttributeType attributeType, double amount) throws RPGCommandException {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (validateCharacterAttribute(attributeType, pc.getCharacterAttributes().getOrDefault(attributeType, config.ATTRIBUTE_MIN) - amount)) {
            characterService.removeAttribute(pc, attributeType, amount);
        }
    }

    /**
     * Validate setting the value of an Attribute against a Character
     * @param type The Attribute to be set
     * @param amount The amount to the attribute will be set to
     * @return true if this is a valid action
     * @throws RPGCommandException When the Attribute is invalid
     */
    private boolean validateCharacterAttribute(AttributeType type, Double amount) throws RPGCommandException {
        if (!type.isUpgradable()) {
            throw new RPGCommandException("Cannot set a Non-Upgradable attribute against a Character");
        }
        if (amount < config.ATTRIBUTE_MIN) {
            throw new RPGCommandException("A player cannot have attributes less than ", config.ATTRIBUTE_MIN);
        }

        if (amount > config.ATTRIBUTE_MAX) {
            throw new RPGCommandException("A player cannot have attributes bigger than ", config.ATTRIBUTE_MAX);
        }

        return true;
    }

    public void mergeBuffAttributes(RPGCharacter<?> pc, Map<AttributeType, Double> attributes) {
        characterService.mergeBuffAttributes(pc, attributes);
    }

    /**
     * Called when the player purchases an attribute for experience
     * @param player The Player purchasing the attribute
     * @param type The type of attribute being purchased
     * @param amount The amount being purchased
     * @throws RPGCommandException
     */
    public void purchaseAttribute(Player player, AttributeType type, double amount) throws RPGCommandException {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        if (!type.isUpgradable()) {
            throw new RPGCommandException("You may not purchase a Non-Upgradable attribute.");
        }

        double expCost = getCostForAttributes(player, amount);

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

            double afterPurchase = pc.getCharacterAttributes().getOrDefault(type, config.ATTRIBUTE_MIN) + amount;

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

    private double getCostForAttributes(Player player, double amount) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        double totalPurchased = attributeService.getUpgradeableAttributeCount(pc);

        double expCost = 0;
        Expression cost = expressionService.getExpression(config.ATTRIBUTE_UPGRADE_COST);
        for (int i = 0; i < amount; i++) {
            cost.setVariable("ATTRIBUTES", BigDecimal.valueOf(totalPurchased));
            cost.setVariable("SKILLS", BigDecimal.valueOf(pc.getSkills().size()));
            expCost += cost.eval().doubleValue();
            totalPurchased++;
        }
        System.out.println("Total cost for " + amount + " starting with " + (totalPurchased - amount) + ": " + expCost);

        return expCost;
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

    public void setItemAttributeValue(ItemStack item, AttributeType attributeType, Double amount) {
        AttributeMapData data = item.get(AttributeMapData.class).orElseGet(AttributeMapData::new);
        data.setAttribute(attributeType, amount);
        item.offer(data);
    }

    public Map<AttributeType, Double> getEquipmentAttributes(Entity entity) {
        return attributeService.getEquipmentAttributes(entity);
    }

    public Map<AttributeType, Double> getAllAttributes(Entity entity) {
        return attributeService.getAllAttributes(entity);
    }

    public void showPlayerAttributes(Player player) {
        PlayerCharacter pc = characterService.getOrCreateCharacter(player);

        List<Text> attributeTexts = new ArrayList<>();
        attributeTexts.add(Text.of(
                DARK_GRAY, "[]===[ ", GOLD, "Your Attributes", DARK_GRAY, " ]===[]"
        ));

        Map<AttributeType, Double> defaultAttributes = attributeService.getDefaultAttributes();
        Map<AttributeType, Double> characterAttributes = pc.getCharacterAttributes();
        Map<AttributeType, Double> buffAttributes = pc.getBuffAttributes();
        Map<AttributeType, Double> equipmentAttributes = getEquipmentAttributes(player);

        double costFor1 = getCostForAttributes(player, 1.0);
        double costFor5 = getCostForAttributes(player, 5.0);

        Sponge.getRegistry().getAllOf(AttributeType.class).forEach( type -> {

            // if the attribute is hidden, there's no need to proceed.
            if (type.isHidden()) {
                return;
            }

            double base = defaultAttributes.get(type) + characterAttributes.getOrDefault(type, config.ATTRIBUTE_MIN);
            double buff = buffAttributes.getOrDefault(type, 0.0d);
            double item = equipmentAttributes.getOrDefault(type, 0.0d);

            int total = Math.max(0, (int) Math.round(base + buff + item));

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
                upgradeButton1 = getAddAttributeButton(pc, type, 1.0, costFor1);
                upgradeButton5 = getAddAttributeButton(pc, type, 5.0, costFor5);
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

    private Text getAddAttributeButton(PlayerCharacter pc, AttributeType type, double amountToAdd, double cost) {

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
                .append(Text.of(DARK_GREEN, "Click to add ", (int) amountToAdd, type.getColor(), type.getName(), DARK_GREEN, " for ", GOLD, cost, DARK_GREEN, " experience"))
                .build();

        return Text.builder()
                .append(Text.of(DARK_GRAY, "[", GOLD, "+", (int) amountToAdd, DARK_GRAY, "] "))
                .onHover(TextActions.showText(hoverText))
                .onClick(TextActions.executeCallback(onClick))
                .build();
    }

    private Text getAttributeDescription(AttributeType type) {
        return Text.of(type.getColor(), type.getName(), NEW_LINE, DARK_GREEN, type.getDescription());
    }

    /**
     * Updates the lore of an item to reflect the attributes contained within it
     *
     * @param stack The itemstack whose lore is to be updated
     */
    public void updateItemLore(ItemStack stack) {
        Optional<AttributeMapData> attributeData = stack.get(AttributeMapData.class);

        attributeData.ifPresent((data) -> {
            List<Text> lore = stack.get(Keys.ITEM_LORE).orElse(new ArrayList<>());

            data.getAttributes().forEach((type, value) -> {
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

    private Text getAttributeLoreLine(AttributeType type, Double amount) {
        return Text.of(type.getColor(), type.getName(), ": ", TextColors.WHITE, amount, TextColors.RESET);
    }
}
