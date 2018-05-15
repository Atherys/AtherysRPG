package com.atherys.rpg.resource;

import com.atherys.rpg.api.resource.AbstractResource;
import org.spongepowered.api.text.format.TextColors;

public class ActionPoints extends AbstractResource {

    protected ActionPoints(double starting) {
        super(
                TextColors.GOLD,
                "atherys:action_points",
                "Action Points",
                100.0d,
                starting
        );
    }

}
