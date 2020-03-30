package com.atherys.rpg.api.skill;

import com.atherys.rpg.skill.description.ExpressionDescriptionArgument;
import com.atherys.rpg.skill.description.PropertyDescriptionArgument;
import com.atherys.rpg.skill.description.TimeDescriptionArgument;
import com.atherys.rpg.skill.description.TimePropertyDescriptionArgument;

public final class DescriptionArguments {

    public static DescriptionArgument ofSource(String expression) {
        return new ExpressionDescriptionArgument(expression);
    }

    public static DescriptionArgument time(String expression) {
        return new TimeDescriptionArgument(expression);
    }

    public static DescriptionArgument timeProperty(RPGSkill skill, String propertyName, String defaultValue) {
        return new TimePropertyDescriptionArgument(skill, propertyName, defaultValue);
    }

    public static DescriptionArgument ofProperty(RPGSkill skill, String propertyName, String defaultValue) {
        return new PropertyDescriptionArgument(skill, propertyName, defaultValue);
    }

    public static DescriptionArgument of(DescriptionArgument argument) {
        return argument;
    }
}
