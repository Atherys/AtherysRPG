package com.atherys.rpg.event;

import com.atherys.rpg.skill.SkillService;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public final class SkillRegistrationEvent implements Event {

    private Cause cause;

    private SkillService skillService;

    public SkillRegistrationEvent(SkillService skillService) {
        this.cause = Cause.builder()
                .append(skillService)
                .build(Sponge.getCauseStackManager().getCurrentContext());
        this.skillService = skillService;
    }

    @Override
    public Cause getCause() {
        return null;
    }

    public SkillService getSkillService() {
        return skillService;
    }
}
