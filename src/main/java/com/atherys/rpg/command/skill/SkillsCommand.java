package com.atherys.rpg.command.skill;

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

@Aliases("skills")
@Description("Base skills command.")
@Children({
        ListAvailableSkillsCommand.class,
        PickSkillCommand.class,
        RemoveSkillCommand.class,
        ResetSkillsCommand.class
})
@Permission("atherysrpg.skills")
public class SkillsCommand implements PlayerCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        AtherysRPG.getInstance().getRPGCharacterFacade().displaySkills(source);
        return CommandResult.success();
    }
}
