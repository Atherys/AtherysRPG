package com.atherys.rpg.skill;

import com.atherys.rpg.api.skill.CastableProperties;
import com.atherys.rpg.api.skill.MouseButtonCombo;
import com.atherys.rpg.utils.StringUtils;
import com.google.gson.annotations.Expose;

import java.util.HashMap;
import java.util.Map;

public final class SkillProperties implements CastableProperties {

    @Expose private Map<String, Object> properties = new HashMap<>();

    public SkillProperties() {
    }

    public static SkillProperties of(com.atherys.rpg.api.skill.annotation.SkillProperties annotation) {
        SkillProperties properties = new SkillProperties();
        properties
                .setPermission(annotation.permission())
                .setCombo(new MouseButtonCombo(annotation.combo()))
                .setCooldown((long) (annotation.cooldown() * 1000L))
                .setCost(annotation.cost());

        return properties;
    }

    @Override
    public String getPermission() {
        return (String) getOrDefault(PERMISSION, "");
    }

    @Override
    public double getResourceCost() {
        return (double) getOrDefault(COST, -1);
    }

    @Override
    public long getCooldown() {
        return (long) getOrDefault(COOLDOWN, -1);
    }

    @Override
    public MouseButtonCombo getCombo() {
        return (MouseButtonCombo) getOrDefault(MOUSE_COMBO, MouseButtonCombo.EMPTY);
    }

    @Override
    public String getDescription() {
        return StringUtils.format((String) properties.getOrDefault(DESCRIPTION, "No description available."), properties);
    }

    @Override
    public Map<String, Object> getMeta() {
        return properties;
    }

    @Override
    public SkillProperties copy() {
        SkillProperties properties = new SkillProperties();
        properties.properties = new HashMap<>(this.properties);
        return properties;
    }

    @Override
    public SkillProperties inheritFrom(CastableProperties parent) {
        SkillProperties copy = this.copy();
        parent.getMeta().forEach((k,v) -> { if ( !copy.properties.containsKey(k) ) copy.properties.put(k, v); });
        return copy;
    }

    protected SkillProperties setPermission(String permission) {
        return set(PERMISSION, permission);
    }

    protected SkillProperties setCost(double cost) {
        return set(COST, cost);
    }

    protected SkillProperties setCooldown(long cooldown) {
        return set(COOLDOWN, cooldown);
    }

    protected SkillProperties setCombo(MouseButtonCombo combo) {
        return set(MOUSE_COMBO, combo);
    }

    protected SkillProperties set(String key, Object value) {
        properties.put(key, value);
        return this;
    }
}
