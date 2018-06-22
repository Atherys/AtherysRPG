package com.atherys.rpg.skill;

import com.atherys.rpg.api.skill.CastResult;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.CastableCarrier;
import com.atherys.rpg.api.skill.CastableProperties;
import com.atherys.rpg.api.skill.annotation.MetaProperty;
import com.atherys.rpg.api.skill.annotation.Skill;
import com.atherys.rpg.event.PostCastEvent;
import com.atherys.rpg.event.PreCastEvent;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.entity.living.player.Player;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class SkillService {

    private static SkillService instance = new SkillService();

    private Set<Castable> skills = new HashSet<>();

    private SkillService() {
    }

    public boolean register(Castable castable) {
        fillProperties(castable);
        return skills.add(castable);
    }

    public boolean unregister(Castable castable) {
        return skills.remove(castable);
    }

    public Optional<Castable> getById(String id) {
        return skills.stream().filter(castable -> castable.getId().equals(id)).findFirst();
    }

    public CastResult cast(Castable castable, CastableCarrier castableCarrier, long timestamp, String... args) {
        String permission = castable.getDefaultProperties().getPermission();

        Optional<? extends Living> living = castableCarrier.asLiving();

        if ( !living.isPresent() ) return CastResult.cancelled(castable);
        else {
            Living user = living.get();
            if ( user instanceof Player && !((Player) user).hasPermission(permission) ) return CastResult.noPermission(castable);
        }

        PreCastEvent preCastEvent = new PreCastEvent(castableCarrier, castable, timestamp, args);
        Sponge.getEventManager().post(preCastEvent);

        if ( preCastEvent.isCancelled() ) return CastResult.cancelled(castable);

        fillMetaProperties(castable, castableCarrier);
        CastResult result = castable.cast(castableCarrier, timestamp, args);

        PostCastEvent postCastEvent = new PostCastEvent(castableCarrier, castable, result, timestamp, args);
        Sponge.getEventManager().post(postCastEvent);

        return result;
    }

    private void fillProperties(Castable castable) {
        if (castable.getClass().isAnnotationPresent(Skill.class) && castable instanceof AbstractSkill) {

            AbstractSkill skill = (AbstractSkill) castable;
            Skill skillAnnotation = castable.getClass().getAnnotation(Skill.class);

            skill.setId(skillAnnotation.id());
            skill.setName(skillAnnotation.name());
            skill.setDefaultProperties(SkillProperties.of(skillAnnotation.defaults()));
        }
    }

    private void fillMetaProperties(Castable castable, CastableCarrier castableCarrier) {
        CastableProperties properties = castable.getProperties(castableCarrier);

        for (Field field : castable.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(MetaProperty.class)) {
                try {
                    boolean accessibility = field.isAccessible();
                    field.setAccessible(true);

                    MetaProperty propertyAnnotation = field.getAnnotation(MetaProperty.class);
                    Object property = properties.getOrDefault(propertyAnnotation.value(), field.get(castable));

                    if (property != null) {
                        if (!field.getDeclaringClass().isAssignableFrom(property.getClass())) {
                            throw new PropertyClassMismatchException(castable, propertyAnnotation);
                        } else {
                            field.set(castable, property);
                        }
                    }

                    field.setAccessible(accessibility);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
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
