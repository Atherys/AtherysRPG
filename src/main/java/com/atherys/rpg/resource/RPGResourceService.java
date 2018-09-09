package com.atherys.rpg.resource;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.ResourceService;
import com.atherys.rpg.event.ResourcePostRegenEvent;
import com.atherys.rpg.event.ResourcePreRegenEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;

public final class RPGResourceService implements ResourceService {

    private static RPGResourceService instance = new RPGResourceService();

    private Task task;

    private RPGResourceService() {
        task = Task.builder()
                .name("resource-service-task")
                .intervalTicks(AtherysRPG.getConfig().RESOURCE_REGEN_TICKS)
                .execute(this::tick)
                .submit(AtherysRPG.getInstance());
    }

    private void tick() {
        AtherysRPG.getPlayerCharacterManager().getOnline().forEach(character -> {
            ResourcePreRegenEvent preRegenEvent = new ResourcePreRegenEvent(character, character.getResource().getRegen());
            Sponge.getEventManager().post(preRegenEvent);

            if ( preRegenEvent.isCancelled() ) {
                ResourcePostRegenEvent postRegenEvent = new ResourcePostRegenEvent(character, character.getResource().getRegen());
                Sponge.getEventManager().post(postRegenEvent);
            }
        });
    }

    public static RPGResourceService getInstance() {
        return instance;
    }

}
