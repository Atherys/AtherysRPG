package com.atherys.rpg.effect;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.effect.Applyable;
import com.atherys.rpg.api.effect.ApplyableCarrier;
import org.spongepowered.api.scheduler.Task;

public class EffectService {

    private static EffectService instance = new EffectService();

    private Task task;

    private EffectService() {
        task = Task.builder()
                .name("effect-service-task")
                .intervalTicks(1)
                .execute(this::tick)
                .submit(AtherysRPG.getInstance());
    }

    private void tick() {
        long timestamp = System.currentTimeMillis();

        AtherysRPG.getCharacterManager().getAll().forEach(character -> {
            character.getEffects().forEach(effect -> tickEffect(timestamp, effect, character));
        });
    }

    private void tickEffect(long timestamp, Applyable applyable, ApplyableCarrier carrier) {
        carrier.applyEffect(applyable, timestamp);
        carrier.removeEffect(applyable, timestamp);
    }

    public static EffectService getInstance() {
        return instance;
    }

}
