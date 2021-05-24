package com.atherys.rpg.skill.description;

import com.atherys.rpg.api.skill.RPGSkill;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextRepresentable;

public class SpeedPercentPropertyDescription extends PropertyDescriptionArgument {

    public SpeedPercentPropertyDescription(RPGSkill skill, String propertyName, String defaultValue) {
        super(skill, propertyName, defaultValue);
    }

    @Override
    public TextRepresentable apply(Living living) {
        double value = evalExpression(living).doubleValue();
        // 0 == Speed 1, for each level of speed, movement speed is increased by 20%
        value = (value + 1) * 20;
        return Text.of(String.format("%.2f", value), "%");
    }
}
