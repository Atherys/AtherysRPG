package com.atherys.rpg.api.event;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.facade.ItemFacade;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.EventContext;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;

public class RegisterItemEvent extends AbstractEvent {
    private ItemFacade itemFacade;

    public RegisterItemEvent(ItemFacade itemFacade) {
        this.itemFacade = itemFacade;
    }

    @Override
    public Cause getCause() {
        return Cause.of(EventContext.empty(), AtherysRPG.getInstance());
    }

    public void registerItem(String id, ItemStackSnapshot item) {
       itemFacade.registerItem(id, item);
    }
}
