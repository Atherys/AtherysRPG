package com.atherys.rpg.api.skill;

import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import java.util.Objects;

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

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        AbstractSkill that = (AbstractSkill) object;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getId(), this.getName());
    }
}
