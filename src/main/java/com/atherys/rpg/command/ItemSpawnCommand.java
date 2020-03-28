package com.atherys.rpg.command;

import com.atherys.core.command.ParameterizedCommand;
import com.atherys.core.command.PlayerCommand;
import com.atherys.core.command.annotation.Aliases;
import com.atherys.core.command.annotation.Permission;
import com.atherys.rpg.AtherysRPG;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.text.Text;

import javax.annotation.Nonnull;

@Aliases("spawnitem")
@Permission("atherysrpg.spawnitem")
public class ItemSpawnCommand implements PlayerCommand, ParameterizedCommand {
    @Nonnull
    @Override
    public CommandResult execute(@Nonnull Player source, @Nonnull CommandContext args) throws CommandException {
        AtherysRPG.getInstance().getItemFacade().createAndGiveItemToPlayer(
                args.<ItemStackSnapshot>getOne("item").orElse(null),
                args.<Integer>getOne("quantity").orElse(0),
                source
        );

        return CommandResult.success();
    }

    @Override
    public CommandElement[] getArguments() {
        return new CommandElement[] {
                GenericArguments.choices(Text.of("item"), AtherysRPG.getInstance().getItemFacade().getCachedItems()),
                GenericArguments.integer(Text.of("quantity"))
        };
    }
}
