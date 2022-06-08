package com.atherys.rpg.command.role;

import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Children;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.rpg.AtherysRPG;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("class")
@Permission("atherysrpg.class.base")
@Description("Base class command. Displays list of classes.")
@Children({SetRoleCommand.class})
public class RoleCommand implements PlayerCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext commandContext) throws CommandException {
        AtherysRPG.getInstance().getRPGCharacterFacade().showClasses(source);
        return CommandResult.success();
    }
}