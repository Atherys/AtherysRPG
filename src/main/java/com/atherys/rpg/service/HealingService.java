package com.atherys.rpg.service;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.config.AtherysRPGConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.scheduler.Task;

@Singleton
public class HealingService {

    @Inject
    private AtherysRPGConfig config;

    @Inject
    private ExpressionService expressionService;

    @Inject
    private RPGCharacterService characterService;

    private Task healthRegenTask;

    public void init() {
        healthRegenTask = Task.builder()
                .intervalTicks(config.HEALTH_REGEN_DURATION_TICKS)
                .execute(this::tickHealthRegen)
                .submit(AtherysRPG.getInstance());
    }

    public void heal(Living entity, double amount) {
        entity.transform(Keys.HEALTH, val -> {
            return Math.min(val + amount, entity.maxHealth().get());
        });
    }

    private void tickHealthRegen() {
        Sponge.getServer().getOnlinePlayers().stream().forEach(player -> {
            if (player.isRemoved() || player.get(Keys.HEALTH).get() <= 0) {
                return;
            }

            double healthRegenAmount = expressionService.evalExpression(player, config.HEALTH_REGEN_CALCULATION).doubleValue();
            heal(player, healthRegenAmount);
        });
    }

}
