package com.atherys.rpg.command;

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

@Aliases({"experience", "xp"})
@Children({
        AddExperienceCommand.class,
        RemoveExperienceCommand.class,
})
@Permission("atherysrpg.experience.base")
@Description("Displays your RPG experience.")
public class ExperienceCommand implements PlayerCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        AtherysRPG.getInstance().getRPGCharacterFacade().showPlayerExperience(source);
        return CommandResult.success();
    }
}
