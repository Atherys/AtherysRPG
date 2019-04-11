package com.atherys.rpg.api.skill;

import com.atherys.skills.api.skill.TargetedCastable;
import org.spongepowered.api.entity.living.Living;

public abstract class TargetedRPGSkill extends RPGSkill implements TargetedCastable {

    public static final String MAX_RANGE_PROPERTY = "max-range";

    protected TargetedRPGSkill(SkillSpec skillSpec) {
        super(skillSpec);
    }

    @Override
    public double getRange(Living user) {
        return asDouble(user, getProperty(MAX_RANGE_PROPERTY, String.class, "100.0"));
    }
}
