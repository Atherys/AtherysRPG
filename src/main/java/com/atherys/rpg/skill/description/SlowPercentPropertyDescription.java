package com.atherys.rpg.skill.description;

import com.atherys.rpg.api.skill.RPGSkill;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;

public class SlowPercentPropertyDescription extends PropertyDescriptionArgument {

    public SlowPercentPropertyDescription(RPGSkill skill, String propertyName, String defaultValue) {
        super(skill, propertyName, defaultValue);
    }

    @Override
    public TextRepresentable apply(Living living) {
        double value = evalExpression(living).doubleValue();
        // 0 == Slowness 1, for each level of slowness, movement speed is decreased by 15%
        value = (value + 1) * 15;
        return Text.of(String.format("%.2f", value), "%");
    }
}
