package com.atherys.rpg.facade;

import com.atherys.core.utils.AbstractMessagingFacade;
import com.google.inject.Singleton;

@Singleton
public class RPGMessagingFacade extends AbstractMessagingFacade {
    public RPGMessagingFacade() {
        super("RPG");
    }
}
