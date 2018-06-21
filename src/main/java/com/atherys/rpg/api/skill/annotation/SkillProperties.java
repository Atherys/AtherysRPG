package com.atherys.rpg.api.skill.annotation;

import com.atherys.rpg.api.skill.MouseButtonCombo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface SkillProperties {

    String permission();

    double cost();

    double cooldown();

    String description();

    MouseButtonCombo.MouseButton[] combo();

}
