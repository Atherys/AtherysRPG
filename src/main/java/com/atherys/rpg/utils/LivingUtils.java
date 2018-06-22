package com.atherys.rpg.utils;

import org.spongepowered.api.data.DataTransactionResult;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.entity.living.Living;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class LivingUtils {

    public static DataTransactionResult applyEffect(Living living, PotionEffect effect) {
        Optional<List<PotionEffect>> potionEffects = living.get(Keys.POTION_EFFECTS);

        if (potionEffects.isPresent()) {
            List<PotionEffect> effects = potionEffects.get();
            effects.add(effect);
            return living.offer(Keys.POTION_EFFECTS, effects);
        } else {
            return living.offer(Keys.POTION_EFFECTS, Arrays.asList(effect));
        }
    }

    public static DataTransactionResult removeEffect(Living living, PotionEffect effect) {
        Optional<List<PotionEffect>> potionEffects = living.get(Keys.POTION_EFFECTS);

        if (potionEffects.isPresent()) {
            List<PotionEffect> effects = potionEffects.get();
            effects.remove(effect);
            return living.offer(Keys.POTION_EFFECTS, effects);
        } else {
            return DataTransactionResult.failNoData();
        }
    }

}
