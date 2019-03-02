package com.atherys.rpg.facade;

import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

@Singleton
public class RPGMessagingFacade {

    public static final Text PREFIX = Text.of(TextColors.DARK_GREEN, "[", TextColors.DARK_RED, "RPG", TextColors.DARK_GREEN, "] ", TextColors.RESET);

    public RPGMessagingFacade() {}

    public Text format(Object... msg) {
        return Text.of(PREFIX, Text.of(msg));
    }

    public void info(Player player, Object... msg) {
        player.sendMessage(format(msg));
    }

    public void error(Player player, Object... msg) {
        player.sendMessage(Text.of(TextColors.RED, format(msg)));
    }
}
