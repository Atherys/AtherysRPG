package com.atherys.rpg.skill;

import com.atherys.rpg.api.skill.CastResult;
import com.atherys.rpg.api.skill.CastableCarrier;
import com.atherys.rpg.api.skill.annotation.MetaProperty;
import com.atherys.rpg.api.skill.annotation.MetaSetter;
import com.atherys.rpg.api.skill.annotation.Skill;
import com.atherys.rpg.api.skill.annotation.SkillProperties;

import static com.atherys.rpg.api.skill.MouseButtonCombo.MouseButton.LEFT;
import static com.atherys.rpg.api.skill.MouseButtonCombo.MouseButton.RIGHT;

@Skill(
        id = "atherys:fireball",
        name = "Fireball",
        defaults = @SkillProperties(
                permission = "atherysrpg.skill.fireball",
                cost = 20.0d,
                cooldown = 5.0d,
                description = "Fires a flaming ball of energy which deals ${damage} Fire Damage and sets the target on fire for ${fireTicks} ticks. Costs ${cost} to use, and has a cooldown of ${cooldown}s",
                combo = {LEFT, RIGHT, RIGHT, LEFT}
        )
)
public class FireballSkill extends AbstractSkill {

    @MetaProperty("damage")
    double damage = 30.0d;

    @MetaProperty("fireTicks")
    int fireTicks = 40;

    @Override
    public CastResult cast(CastableCarrier user, long timestamp, String... args) {
        return CastResult.notImplemented();
    }

    @MetaSetter("damage")
    public void setDamage(double damage) {
        this.damage = damage;
    }

    @MetaSetter("fireTicks")
    public void setFireTicks(int fireTicks) {
        this.fireTicks = fireTicks;
    }
}
