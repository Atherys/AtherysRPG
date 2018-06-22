package com.atherys.rpg.event;

import com.atherys.rpg.api.skill.CastResult;
import com.atherys.rpg.api.skill.Castable;
import com.atherys.rpg.api.skill.CastableCarrier;

public class PostCastEvent extends CastEvent {

    private CastResult result;

    public PostCastEvent(CastableCarrier character, Castable castable, CastResult result, long timestamp, String... args) {
        super(character, castable, timestamp, args);
        this.result = result;
    }

    public CastResult getResult() {
        return result;
    }
}
