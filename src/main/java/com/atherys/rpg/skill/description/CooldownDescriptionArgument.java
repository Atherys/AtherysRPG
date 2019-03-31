package com.atherys.rpg.skill.description;

import com.atherys.rpg.AtherysRPG;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;

public class CooldownDescriptionArgument extends ExpressionDescriptionArgument {
    public CooldownDescriptionArgument(String expression) {
        super(expression);
    }

    @Override
    public TextRepresentable apply(Living living) {
        long duration = AtherysRPG.getInstance().getExpressionService().evalExpression(living, super.expression).longValue();

        String format = "H'h' m'm' s.S's'";

        if (duration < 60000) {
            format = "s.S's'";
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
