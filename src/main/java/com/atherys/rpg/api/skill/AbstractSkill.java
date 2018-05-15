package com.atherys.rpg.api.skill;

import com.atherys.rpg.skill.SkillProperties;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

public abstract class AbstractSkill implements Castable {

    @Expose SkillProperties properties;

    @Override
    public CastableProperties getProperties() {
        return properties;
    }

    @Override
    public Text toText() {
        return Text.builder()
                .append(Text.of(this.getName()))
                .onHover(TextActions.showText(this.properties.getDescription()))
                .build();
    }
}
