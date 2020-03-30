package com.atherys.rpg.command.attribute;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.stat.AttributeType;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases("enchant")
@Permission("atherysrpg.attributes.enchant")
@Description("Enchants your held item with the given attribute and amount.")
public class AddAttributeToItemCommand implements PlayerCommand, ParameterizedCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.catalogedElement(Text.of("attributeType"), AttributeType.class),
                GenericArguments.doubleNum(Text.of("amount"))
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        AtherysRPG.getInstance().getAttributeFacade().enchantPlayerHeldItem(
                source,
                args.<AttributeType>getOne("attributeType").orElse(null),
                args.<Double>getOne("amount").orElse(0.0d)
        );
        return CommandResult.success();
    }
}
