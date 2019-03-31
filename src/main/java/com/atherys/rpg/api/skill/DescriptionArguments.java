package com.atherys.rpg.api.skill;

import com.atherys.rpg.skill.description.CooldownDescriptionArgument;
import com.atherys.rpg.skill.description.ExpressionDescriptionArgument;

public final class DescriptionArguments {

    public static DescriptionArgument ofSource(String expression) {
        return new ExpressionDescriptionArgument(expression);
    }

    public static DescriptionArgument cooldown(String expression) {
        return new CooldownDescriptionArgument(expression);
    }

    public static DescriptionArgument of(DescriptionArgument argument) {
        return argument;
    }
}
