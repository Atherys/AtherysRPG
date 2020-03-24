package com.atherys.rpg.skill.description;

import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.util.TextUtils;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.TextRepresentable;

public class TimePropertyDescriptionArgument extends PropertyDescriptionArgument {
    public TimePropertyDescriptionArgument(RPGSkill skill, String propertyName, String defaultValue) {
        super(skill, propertyName, defaultValue);
    }

    @Override
    public TextRepresentable apply(Living living) {
        return TextUtils.formatDuration(asLong(living));
    }
}
