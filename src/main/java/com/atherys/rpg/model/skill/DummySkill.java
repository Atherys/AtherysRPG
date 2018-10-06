package com.atherys.rpg.model.skill;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.api.skill.CastResult;
import com.atherys.rpg.api.skill.CastableCarrier;
import com.atherys.rpg.api.skill.annotation.Skill;
import com.atherys.rpg.api.skill.annotation.SkillProperties;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

import java.util.concurrent.TimeUnit;

import static com.atherys.rpg.api.skill.MouseButtonCombo.MouseButton.LEFT;
import static com.atherys.rpg.api.skill.MouseButtonCombo.MouseButton.RIGHT;

@Skill(
        id = "atherys:dummy",
        name = "Dummy Skill",
        defaults = @SkillProperties(
                permission = "atherysrpg.skill.dummy",
                cost = 20.0d,
                cooldown = 5.0d,
                description = "Sends a message to the player ${msg_amount} times.",
                combo = {LEFT, RIGHT, RIGHT, LEFT}
        )
)
public class DummySkill extends AbstractSkill {
    @Override
    public CastResult cast(CastableCarrier user, long timestamp, String... args) {
        double msgAmount = user.getProperty(this, "msg_amount", Double.class);

        Task.builder()
                .name("dummy-skill-task-" + user.getUniqueId())
                .interval(1, TimeUnit.SECONDS)
                .execute(() -> {
                    user.asLiving().ifPresent(living -> {
                        if (living instanceof Player) {
                            ((Player) living).sendMessage(Text.of("Dummy Skill Message."));
                        }
                    });
                })
                .submit(AtherysRPG.getInstance());

        return null;
    }
}
