package com.atherys.rpg.skill.service;

import com.atherys.rpg.api.skill.CastResult;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.CastableCarrier;
import com.atherys.rpg.api.skill.annotation.MetaProperty;
import com.atherys.rpg.api.skill.annotation.Skill;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class SkillService {

    private static SkillService instance = new SkillService();

    private Set<Castable> skills = new HashSet<>();

    private SkillService() {
    }

    public boolean register(Castable castable) {

        return skills.add(castable);
    }

    public boolean unregister(Castable castable) {
        return skills.remove(castable);
    }

    public CastResult cast(Castable castable, CastableCarrier castableCarrier, String... args) {
        fillMetaProperties(castable, castableCarrier);
        return castable.cast(castableCarrier, args);
    }

    private void fillProperties(Castable castable) {
        Skill skill;
        skill.defaults().combo();
    }

    private void fillMetaProperties(Castable castable, CastableCarrier castableCarrier) {
        for ( Field field : castable.getClass().getDeclaredFields() ) {
            if ( field.isAnnotationPresent(MetaProperty.class) ) {

                MetaProperty propertyAnnotation = field.getAnnotation(MetaProperty.class);
                Object property = castableCarrier.getProperty(castable, propertyAnnotation.value());

                if ( !field.getDeclaringClass().isAssignableFrom(property.getClass()) ) {
                    throw new PropertyClassMismatchException(castable, propertyAnnotation);
                } else {
                    try {
                        boolean accessibility = field.isAccessible();
                        field.setAccessible(true);
                        field.set(castable, property);
                        field.setAccessible(accessibility);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static SkillService getInstance() {
        return instance;
    }

    public static class PropertyClassMismatchException extends RuntimeException {
        PropertyClassMismatchException(Castable castable, MetaProperty property) {
            super("\"" + property.value() + "\" MetaProperty does not match field declared class in skill " + castable.getClass().getName());
        }
    }

}
