package com.atherys.rpg.api.skill;

import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.text.TextRepresentable;

import java.util.function.Function;

@FunctionalInterface
public interface DescriptionArgument extends Function<Living, TextRepresentable> {
}
