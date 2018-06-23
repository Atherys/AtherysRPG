package com.atherys.rpg.skill;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.character.RPGCharacter;
import org.spongepowered.api.scheduler.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class CooldownService {

    private static final CooldownService instance = new CooldownService();

    private Task task;

    /**
     * Contains a map relating a UUID to the last time that UUID in milliseconds had used a skill
     */
    private Map<UUID,Long> lastTimeUsedSkill = new HashMap<>();

    private long globalCooldown = AtherysRPG.getConfig().GLOBAL_COOLDOWN;

    private CooldownService() {
        task = Task.builder()
                .name("cooldown-service-task")
                .delayTicks(1)
                .execute(this::tick)
                .async()
                .submit(AtherysRPG.getInstance());
    }

    private void tick() {
        long currentMillis = System.currentTimeMillis();
        for ( Map.Entry<UUID,Long> entry : lastTimeUsedSkill.entrySet() ) {
            if ( currentMillis - entry.getValue() >= globalCooldown ) lastTimeUsedSkill.remove(entry.getKey());
        }
    }

    public long getGlobalCooldown() {
        return globalCooldown;
    }

    public boolean isOnGlobalCooldown(RPGCharacter character) {
        return isOnGlobalCooldown(character.getUniqueId());
    }

    public boolean isOnGlobalCooldown(UUID uuid) {
        return lastTimeUsedSkill.containsKey(uuid);
    }

    public void setOnGlobalCooldown(UUID uuid, long timestamp) {
        lastTimeUsedSkill.put(uuid, timestamp);
    }

    public static CooldownService getInstance() {
        return instance;
    }

}
