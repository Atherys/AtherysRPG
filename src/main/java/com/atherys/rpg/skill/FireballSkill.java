package com.atherys.rpg.skill;

import com.atherys.rpg.api.skill.CastResult;
import com.atherys.rpg.api.skill.CastableCarrier;
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

    @Override
    public CastResult cast(CastableCarrier user, long timestamp, String... args) {
        final Double damage = user.getProperty(this, "damage", Double.class);
        final Integer fireTicks = user.getProperty(this, "fireTicks", Integer.class);

        return CastResult.notImplemented();
    }
}
