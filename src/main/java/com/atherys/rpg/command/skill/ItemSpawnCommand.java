package com.atherys.rpg.command.skill;

import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.rpg.AtherysRPG;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.entity.living.player.Player;

import javax.annotation.Nonnull;

@Aliases("spawnitem")
public class ItemSpawnCommand implements PlayerCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        AtherysRPG.getInstance().getItemFacade().createAndGiveItemToPlayer(
                args.<String>getOne("item-id").orElse(null),
                args.<Integer>getOne("quantity").orElse(0),
                source
        );

        return CommandResult.success();
    }
}
