package com.atherys.rpg.api.skill;

import com.atherys.rpg.skill.description.*;

public final class DescriptionArguments {

    public static DescriptionArgument ofSource(String expression) {
        return new ExpressionDescriptionArgument(expression);
    }

    public static DescriptionArgument ofProperty(RPGSkill skill, String propertyName, String defaultValue) {
        return new PropertyDescriptionArgument(skill, propertyName, defaultValue);
    }

    public static DescriptionArgument ofTimeProperty(RPGSkill skill, String propertyName, String defaultValue) {
        return new TimePropertyDescriptionArgument(skill, propertyName, defaultValue);
    }

    public static SpeedPercentPropertyDescription ofSpeedPercentProperty(RPGSkill skill, String propertyName, String defaultValue) {
        return new SpeedPercentPropertyDescription(skill, propertyName, defaultValue);
    }

    public static SlowPercentPropertyDescription ofSlowPercentProperty(RPGSkill skill, String propertyName, String defaultValue) {
        return new SlowPercentPropertyDescription(skill, propertyName, defaultValue);
    }
}
