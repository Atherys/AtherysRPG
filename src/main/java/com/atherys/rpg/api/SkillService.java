package com.atherys.rpg.api;

import com.atherys.rpg.api.exception.CastException;
import com.atherys.rpg.api.skill.CastResult;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.CastableCarrier;

import java.util.Collection;
import java.util.Optional;

public interface SkillService {

    Collection<Castable> getPrototypes();

    Optional<Castable> getById(String castableId);

    boolean addPrototype(Castable castable);

    boolean removePrototype(Castable castable);

    CastResult cast(Castable castable, CastableCarrier castableCarrier, long timestamp, String... args) throws CastException;

}
