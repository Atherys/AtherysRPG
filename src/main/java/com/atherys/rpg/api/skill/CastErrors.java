package com.atherys.rpg.api.skill;

import com.atherys.rpg.api.exception.CastException;
import com.atherys.rpg.api.resource.Resource;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public final class CastErrors {

    public static CastException exception(Text message) {
        return new CastException(message);
    }

    public static CastException failure(Castable castable) {
        return exception(Text.of(TextColors.RED, "Failed to use ", TextColors.DARK_RED, castable.getName()));
    }

    public static CastException cancelled(Castable castable) {
        return exception(Text.of(TextColors.RED, "Cancelled ", TextColors.DARK_RED, castable.getName()));
    }

    public static CastException onCooldown(long timestamp, Castable castable, long cooldownEnd) {
        String format = "H'h' m'm' s.S's'";

        long duration = cooldownEnd - timestamp;

        if (duration < 60000) {
            format = "s.S's'";
        }

        if (duration >= 60000 && duration < 3600000) {
            format = "m'm' s.S's'";
        }

        if (duration >= 3600000) {
            format = "H'h' m'm' s.S's'";
        }

        return exception(Text.of(TextColors.DARK_RED, castable.getName(), TextColors.RED, " is on cooldown for another ", TextColors.WHITE, DurationFormatUtils.formatDuration(duration, format, false)));
    }

    public static CastException insufficientResources(Castable castable, Resource resource) {
        return exception(Text.of("You do not have enough ", resource.getColor(), resource.getName(), TextColors.RESET, " to cast ", castable.getName()));
    }

    public static CastException blocked(Castable castable) {
        return exception(Text.of(TextColors.DARK_RED, castable.getName(), TextColors.RED, " has been blocked."));
    }

    public static CastException noTarget() {
        return exception(Text.of(TextColors.RED, "No target could be found."));
    }

    public static CastException invalidTarget() {
        return exception(Text.of(TextColors.RED, "Target is not valid."));
    }

    public static CastException obscuredTarget() {
        return exception(Text.of(TextColors.RED, "Target is obscured."));
    }

    public static CastException notImplemented() {
        return exception(Text.of(TextColors.RED, "This skill is not implemented yet."));
    }

    public static CastException noSuchSkill() {
        return exception(Text.of(TextColors.RED, "No such skill"));
    }

    public static CastException internalError() {
        return exception(Text.of(TextColors.DARK_RED, "An internal error occurred while casting this skill. Please report this."));
    }

    public static CastException noPermission(Castable castable) {
        return exception(Text.of(TextColors.RED, "You lack the permission required to use the skill ", castable.getName()));
    }

}
