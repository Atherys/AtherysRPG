package com.atherys.rpg.effect;

import com.atherys.rpg.api.effect.ApplyableCarrier;
import com.atherys.rpg.utils.LivingUtils;
import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;

/**
 * Wrapper around a {@link PotionEffect}
 */
public class TemporaryPotionEffect extends TemporaryEffect {

    private PotionEffect effect;

    public TemporaryPotionEffect(String id, String name, PotionEffect effect) {
        super(id, name, effect.getDuration() * 50);
        this.effect = effect;
    }

    public static TemporaryPotionEffect of(PotionEffectType effectType, int duration, int amplifier) {
        return new TemporaryPotionEffect(
                "atherys:" + effectType.getId() + "_effect",
                effectType.getName(),
                PotionEffect.of(effectType, duration, amplifier)
        );
    }

    @Override
    protected <T extends ApplyableCarrier> boolean apply(T character) {
        return character.asLiving()
                .map(living -> LivingUtils.applyEffect(living, effect))
                .map(DataTransactionResult::isSuccessful)
                .orElse(false);
    }

    @Override
    protected <T extends ApplyableCarrier> boolean remove(T character) {
        return character.asLiving()
                .map(living -> LivingUtils.removeEffect(living, effect))
                .map(DataTransactionResult::isSuccessful)
                .orElse(false);
    }
}
