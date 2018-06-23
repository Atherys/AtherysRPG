package com.atherys.rpg.resource;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.event.ResourcePostRegenEvent;
import com.atherys.rpg.event.ResourcePreRegenEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

public final class ResourceService {

    private static ResourceService instance = new ResourceService();

    private Task task;

    private ResourceService() {
        task = Task.builder()
                .name("resource-service-task")
                .intervalTicks(AtherysRPG.getConfig().RESOURCE_REGEN_TICKS)
                .execute(this::tick)
                .submit(AtherysRPG.getInstance());
    }

    private void tick() {
        AtherysRPG.getCharacterManager().getAll().forEach(character -> {
            ResourcePreRegenEvent preRegenEvent = new ResourcePreRegenEvent(character, character.getResource().getRegen());
            Sponge.getEventManager().post(preRegenEvent);

            if ( preRegenEvent.isCancelled() ) {
                ResourcePostRegenEvent postRegenEvent = new ResourcePostRegenEvent(character, character.getResource().getRegen());
                Sponge.getEventManager().post(postRegenEvent);
            }
        });
    }

    public static ResourceService getInstance() {
        return instance;
    }

}
