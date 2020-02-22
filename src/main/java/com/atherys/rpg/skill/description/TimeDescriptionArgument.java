package com.atherys.rpg.skill.description;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.util.TextUtils;
import org.apache.commons.lang3.time.DurationFormatUtils;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;

public class TimeDescriptionArgument extends ExpressionDescriptionArgument {
    public TimeDescriptionArgument(String expression) {
        super(expression);
    }

    @Override
    public TextRepresentable apply(Living living) {
        long duration = AtherysRPG.getInstance().getExpressionService().evalExpression(living, super.expression).longValue();

        return TextUtils.formatDuration(duration);
    }
}
