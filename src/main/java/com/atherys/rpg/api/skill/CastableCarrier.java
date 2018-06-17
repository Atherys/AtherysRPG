package com.atherys.rpg.api.skill;

import com.atherys.rpg.api.LivingRepresentable;

import java.util.Optional;
import java.util.Set;

public interface CastableCarrier extends LivingRepresentable {

    Set<Castable> getCastables();

    default boolean hasCastable(Castable castable) {
        return getCastables().contains(castable);
    }

    default boolean addCastable(Castable castable) {
        return getCastables().add(castable);
    }

    default boolean removeCastable(Castable castable) {
        return getCastables().remove(castable);
    }

    default Optional<? extends Castable> getCastableById(String id) {
        for ( Castable castable : getCastables() ) if ( castable.getId().equals(id) ) return Optional.of(castable);
        return Optional.empty();
    }

    CastResult cast(Castable castable, String... args);

    default CastResult castById(String castableId, String... args) {
        Optional<? extends Castable> castable = getCastableById(castableId);
        if ( castable.isPresent() ) return castable.get().cast(this, args);
        else return CastResult.noSuchSkill();
    }

}
