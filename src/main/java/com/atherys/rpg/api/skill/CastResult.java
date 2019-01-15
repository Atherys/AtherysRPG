package com.atherys.rpg.api.skill;

import com.atherys.rpg.api.resource.Resource;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CastResult {

    private Text message;

    private CastResult(Text message) {
        this.message = message;
    }

    public static CastResult empty() {
        return new CastResult(Text.EMPTY);
    }

    public static CastResult custom(Text text) {
        return new CastResult(text);
    }

    public static CastResult success() {
        return new CastResult(Text.EMPTY);
    }

    void feedback(CastableCarrier user) {
        user.asLiving().ifPresent(living -> {
            if (living instanceof Player && !message.isEmpty()) {
                // TODO
                //CombatMessage.prefix((Player) living, message);
            }
        });
    }
}
