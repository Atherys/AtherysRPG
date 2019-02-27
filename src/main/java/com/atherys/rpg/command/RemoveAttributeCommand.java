package com.atherys.rpg.command;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Permission;
import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.stat.AttributeType;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

@Aliases("remove")
@Permission("atherysrpg.attributes.remove")
public class RemoveAttributeCommand implements ParameterizedCommand {

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                GenericArguments.player(Text.of("player")),
                GenericArguments.catalogedElement(Text.of("attributeType"), AttributeType.class),
                GenericArguments.doubleNum(Text.of("amount"))
        };
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        AtherysRPG.getInstance().getRPGCharacterFacade().removePlayerAttribute(
                args.<Player>getOne("player").orElse(null),
                args.<AttributeType>getOne("attributeType").orElse(null),
                args.<Double>getOne("amount").orElse(0.0d)
        );
        return CommandResult.success();
    }
}
