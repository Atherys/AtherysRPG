package com.atherys.rpg.api.skill;

import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.skill.CastResult;
import org.spongepowered.api.entity.living.Living;

public final class BlankSkill extends RPGSkill {
    public static final BlankSkill blank = new BlankSkill();

    private BlankSkill() {
        super(SkillSpec.create()
                .cooldown("0")
                .id("blank")
                .name("Blank")
                .resourceCost("0")
        );
    }

    @Override
    public CastResult cast(Living living, long l, String... strings) throws CastException {
        return CastResult.empty();
    }
}