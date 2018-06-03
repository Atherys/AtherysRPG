package com.atherys.rpg.api.skill;

import com.atherys.rpg.api.LivingRepresentable;

import java.util.Collection;
import java.util.Optional;

public interface CastableCarrier extends LivingRepresentable {

    Collection<Castable> getCastables();

    void addCastable(Castable castable);

    void removeCastable(Castable castable);

    Optional<? extends Castable> getCastableById(String id);

    Optional<? extends Castable> getCastableByCombo(MouseButtonCombo combo);

    Optional<MouseButtonCombo> getCurrentCombo();

    void cast(Castable castable);

}
