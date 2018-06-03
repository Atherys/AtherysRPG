package com.atherys.rpg.skill;

import com.atherys.rpg.api.skill.AbstractSkill;
import com.atherys.rpg.api.skill.CastResult;
import com.atherys.rpg.api.skill.CastableCarrier;

public class FireballSkill extends AbstractSkill {

    @Override
    public CastResult cast(CastableCarrier user, String... args) {
        getProperty("damage", Double.class);
        return null;
    }

    @Override
    public String getId() {
        return "atherys:fireball";
    }

    @Override
    public String getName() {
        return "Fireball";
    }
}
