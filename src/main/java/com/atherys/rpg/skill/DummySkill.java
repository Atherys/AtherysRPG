package com.atherys.rpg.skill;

import com.atherys.rpg.api.skill.RPGSkill;
import com.atherys.rpg.api.skill.SkillSpec;
import com.atherys.skills.api.exception.CastException;
import com.atherys.skills.api.skill.CastErrors;
import com.atherys.skills.api.skill.CastResult;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.TextTemplate;

public class DummySkill extends RPGSkill {

    public DummySkill(String id, String name, String description) {
        super(
                SkillSpec.create()
                .id(id)
                .name(name)
                .descriptionTemplate(TextTemplate.of(description))
                .cooldown("0")
                .resourceCost("0.0")
                .permission("atherysrpg.skill.cast.root")
        );
    }

    @Override
    public CastResult cast(Living user, long timestamp, String... args) throws CastException {
        throw CastErrors.noSuchSkill();
    }
}
