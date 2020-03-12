package com.atherys.rpg.command.skill;

import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.*;
import com.atherys.rpg.AtherysRPG;
import com.atherys.skills.command.skill.CastSkillCommand;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("skills")
@Children({
        ListAvailableSkillsCommand.class,
        PickSkillCommand.class,
        RemoveSkillCommand.class,
        ResetSkillsCommand.class,
        CastSkillCommand.class
})
@HelpCommand(title = "Skills Help", command = "help")
@Permission("atherysrpg.skills.base")
@Description("Displays your unlocked skills.")
public class SkillsCommand implements PlayerCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        AtherysRPG.getInstance().getRPGCharacterFacade().displaySkills(source);
        return CommandResult.success();
    }
}
