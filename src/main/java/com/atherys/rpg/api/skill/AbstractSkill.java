package com.atherys.rpg.api.skill;

import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.action.TextActions;

import java.util.Optional;

public abstract class AbstractSkill implements Castable {

    @Expose private CastableProperties properties;

    @Override
    public CastableProperties getProperties() {
        return properties;
    }

    protected Optional<?> getProperty(String id) {
        return Optional.ofNullable(properties.get(id));
    }

    protected <T> Optional<T> getProperty(String id, Class<T> clazz) {
        return properties.get(id, clazz);
    }

    public <T extends CastableProperties> void setProperties(T properties) {
        this.properties = properties;
    }

    @Override
    public Text toText() {
        return Text.builder()
                .append(Text.of(this.getName()))
                .onHover(TextActions.showText(this.properties.getDescription()))
                .build();
    }
}
