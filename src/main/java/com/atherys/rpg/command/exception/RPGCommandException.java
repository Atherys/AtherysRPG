package com.atherys.rpg.command.exception;

import com.atherys.rpg.AtherysRPG;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class RPGCommandException extends CommandException {

    public RPGCommandException(Object... message) {
        super(AtherysRPG.getInstance().getRPGMessagingFacade().format(TextColors.RED, Text.of(message)));
    }
}
