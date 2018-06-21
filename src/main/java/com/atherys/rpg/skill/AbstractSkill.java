package com.atherys.rpg.skill;

import com.atherys.rpg.api.character.RPGCharacter;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.CastableProperties;
import org.spongepowered.api.text.Text;

import java.util.Objects;

public abstract class AbstractSkill implements Castable {

    private String id = "atherys:" + this.getClass().getName().toLowerCase();
    private String name = this.getClass().getSimpleName();

    private SkillProperties defaults = new SkillProperties();

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public CastableProperties getDefaultProperties() {
        return defaults;
    }

    @Override
    public Text asText(RPGCharacter character) {
        return Text.of(character.searchProperty(this, CastableProperties.DESCRIPTION, String.class).orElse("No description available."));
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

    protected void setId(String id) {
        this.id = id;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected void setDefaultProperties(SkillProperties defaults) {
        this.defaults = defaults;
    }
}
