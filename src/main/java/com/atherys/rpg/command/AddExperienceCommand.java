package com.atherys.rpg.command;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.rpg.AtherysRPG;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

@Aliases({"give", "add"})
@Permission("atherysrpg.experience.add")
@Description("Gives the player (RPG) experience.")
public class AddExperienceCommand implements ParameterizedCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[]{
                GenericArguments.player(Text.of("player")),
                GenericArguments.doubleNum(Text.of("amount"))
        };
    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        AtherysRPG.getInstance().getRPGCharacterFacade().addPlayerExperience(
                args.<Player>getOne("player").orElse(null),
                args.<Double>getOne("amount").orElse(0.0d)
        );
        return CommandResult.success();
    }
}
