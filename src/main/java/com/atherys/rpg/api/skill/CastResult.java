package com.atherys.rpg.api.skill;

import com.atherys.rpg.api.resource.Resource;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class CastResult {

    private Text message;
    private boolean successful;

    private CastResult(Text message, boolean successful) {
        this.message = message;
        this.successful = successful;
    }

    public static CastResult empty() {
        return new CastResult(Text.EMPTY, false);
    }

    public static CastResult custom(Text text, boolean successful) {
        return new CastResult(text, successful);
    }

    public static CastResult success() {
        return new CastResult(Text.EMPTY, true);
    }

    public static CastResult failure(Castable castable) {
        return new CastResult(Text.of(TextColors.RED, "Failed to use ", TextColors.DARK_RED, castable.getName()), false);
    }

    public static CastResult cancelled(Castable castable) {
        return new CastResult(Text.of(TextColors.RED, "Cancelled ", TextColors.DARK_RED, castable.getName()), false);
    }

    public static CastResult onCooldown(long timestamp, Castable castable, long cooldownEnd) {
        String format = "H'h' m'm' s.S's'";
        long duration = cooldownEnd - timestamp;
        if (duration < 60000) format = "s.S's'";
        if (duration >= 60000 && duration < 3600000) format = "m'm' s.S's'";
        if (duration >= 3600000) format = "H'h' m'm' s.S's'";
        return new CastResult(Text.of(TextColors.DARK_RED, castable.getName(), TextColors.RED, " is on cooldown for another ", TextColors.WHITE, DurationFormatUtils.formatDuration(duration, format, false)), false);
    }

    public static CastResult insufficientResources(Castable castable, Resource resource) {
        return new CastResult(Text.of("You do not have enough ", resource.getColor(), resource.getName(), TextColors.RESET, " to cast ", castable.getName()), false);
    }

    public static CastResult blocked(Castable castable) {
        return new CastResult(Text.of(TextColors.DARK_RED, castable.getName(), TextColors.RED, " has been blocked."), false);
    }

    public static CastResult noTarget() {
        return new CastResult(Text.of(TextColors.RED, "No target could be found."), false);
    }

    public static CastResult invalidTarget() {
        return new CastResult(Text.of(TextColors.RED, "Target is not valid."), false);
    }

    public static CastResult obscuredTarget() {
        return new CastResult(Text.of(TextColors.RED, "Target is obscured."), false);
    }

    public static CastResult notImplemented() {
        return new CastResult(Text.of(TextColors.RED, "This skill is not functional yet."), false);
    }

    public static CastResult noSuchSkill() {
        return new CastResult(Text.of(TextColors.RED, "No such skill"), false);
    }

    public static CastResult internalError() {
        return new CastResult(Text.of(TextColors.DARK_RED, "An internal error occurred while casting this skill. Please report this."), false);
    }

    void feedback(CastableCarrier user) {
        user.asLiving().ifPresent(living -> {
            if (living instanceof Player && !message.isEmpty()) {
                // TODO
                //CombatMessage.prefix((Player) living, message);
            }
        });
    }

    public boolean isSuccessful() {
        return successful;
    }
}
