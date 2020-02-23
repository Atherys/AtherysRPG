package com.atherys.rpg.facade;

import com.atherys.core.utils.AbstractMessagingFacade;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

@Singleton
public class RPGMessagingFacade extends AbstractMessagingFacade {
    public RPGMessagingFacade() {
        super("RPG");
    }
}
