package com.atherys.rpg.listener;

import com.atherys.rpg.facade.RPGCharacterFacade;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.filter.cause.Root;
import org.spongepowered.api.event.network.ClientConnectionEvent;

/**
 * This is ugly, because something on the event handling level exists solely for the reason of data management
 * on the database level. Even though this is technically correct with the architecture in mind, it still breaks
 * layer separation rules. Too bad!
 */
@Singleton
public class PlayerListener {

    @Inject
    private RPGCharacterFacade characterFacade;

    @Listener
    public void onPlayerJoin(ClientConnectionEvent.Join event, @Root Player player) {
        characterFacade.fetchPlayerData(player);
    }

    @Listener
    public void onPlayerLeave(ClientConnectionEvent.Disconnect event, @Root Player player) {
        characterFacade.storePlayerData(player);
    }

}
