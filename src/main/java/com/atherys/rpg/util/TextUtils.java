package com.atherys.rpg.util;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextTemplate;

public final class TextUtils {

    /**
     * Create a new {@link TextTemplate} from the provided string,
     * where the template arguments start with "${" and end with "}"
     * @param template
     * @return
     */
    public static TextTemplate templateOf(String template) {
        // TODO
        return TextTemplate.EMPTY;
    }

    public static Text formatDuration(long duration) {
        String format = "H'h' m'm' s.S's'";

        if (duration < 60000) {
            format = "s.SS's'";
        }

        if (duration >= 60000 && duration < 3600000) {
            format = "m'm' s.S's'";
        }

        if (duration >= 3600000) {
            format = "H'h' m'm' s.S's'";
        }

        return Text.of(DurationFormatUtils.formatDuration(duration, format, false));
    }

}
