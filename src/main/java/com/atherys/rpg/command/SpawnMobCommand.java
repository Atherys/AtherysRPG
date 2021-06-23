package com.atherys.rpg.command;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Description;
import com.atherys.core.command.annotation.Permission;
import com.atherys.rpg.AtherysRPG;
import com.google.common.collect.Maps;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases("spawnmob")
@Permission("atherysrpg.mob.spawn")
@Description("Spawns a mob with a given id from the mobs config.")
public class SpawnMobCommand implements PlayerCommand, ParameterizedCommand {
    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                GenericArguments.choices(
                        Text.of("mob"),
                        Maps.asMap(AtherysRPG.getInstance().getMobsConfig().MOBS.keySet(), s -> s)
                )
        };
    }

    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        AtherysRPG.getInstance().getMobFacade().spawnMob(args.<String>getOne("mob").get(), source);
        return CommandResult.success();
    }
}
