package com.atherys.rpg.skill;

import com.atherys.rpg.api.skill.CastableProperties;
import com.atherys.rpg.api.skill.MouseButtonCombo;
import com.google.gson.annotations.Expose;
import org.spongepowered.api.text.Text;

import java.util.HashMap;
import java.util.Map;

public class SkillProperties implements CastableProperties {

    @Expose private String permission = "";

    @Expose private double cost = -1;
    @Expose private long cooldown = -1;
    @Expose private Text description = Text.EMPTY;
    @Expose private MouseButtonCombo combo = MouseButtonCombo.EMPTY;

    @Expose private Map<String, Object> properties = new HashMap<>();

    public SkillProperties() {
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public double getResourceCost() {
        return cost;
    }

    @Override
    public long getCooldown() {
        return cooldown;
    }

    @Override
    public MouseButtonCombo getCombo() {
        return combo;
    }

    @Override
    public Text getDescription() {
        return description;
    }

    @Override
    public Map<String, Object> getMeta() {
        return properties;
    }

    @Override
    public SkillProperties copy() {
        SkillProperties properties = new SkillProperties();
        properties.permission = this.permission;
        properties.description = this.description;
        properties.combo = this.combo;
        properties.cooldown = this.cooldown;
        properties.cost = this.cost;
        properties.properties = new HashMap<>(this.properties);
        return properties;
    }

    @Override
    public SkillProperties mutate(CastableProperties parent) {
        SkillProperties copy = this.copy();
        if ( copy.permission.isEmpty() ) copy.permission = parent.getPermission();
        if ( copy.cost == -1 ) copy.cost = parent.getResourceCost();
        if ( copy.cooldown == -1 ) copy.cooldown = parent.getCooldown();
        if ( copy.combo.isEmpty() ) copy.combo = parent.getCombo();
        if ( copy.description.isEmpty() ) copy.description = parent.getDescription();

        parent.getMeta().forEach((k,v) -> { if ( !copy.properties.containsKey(k) ) copy.properties.put(k, v); });
        return copy;
    }


}
