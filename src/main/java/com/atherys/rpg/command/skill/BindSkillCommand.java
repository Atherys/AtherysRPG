package com.atherys.rpg.command.skill;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.rpg.AtherysRPG;
import com.atherys.skills.api.skill.Castable;
import com.atherys.skills.command.SkillCommandElement;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases("bind")
@Description("Binds a skill to an item.")
@Permission("atherysrpg.skills.bind")
public class BindSkillCommand implements PlayerCommand, ParameterizedCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                new SkillCommandElement(Text.of("skill"))
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        AtherysRPG.getInstance().getRPGCharacterFacade().bindItemToSkill(source, args.<Castable>getOne("skill").get());
        return CommandResult.success();
    }
}
