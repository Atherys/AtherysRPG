package com.atherys.rpg.resource;

import com.atherys.rpg.api.resource.Resource;
import org.spongepowered.api.text.format.TextColors;

public class Rage extends AbstractResource {

    protected Rage(double starting) {
        super(
                TextColors.DARK_RED,
                "atherys:rage",
                "Rage",
                100.0d,
                starting
        );
    }

    @Override
    public Resource copy() {
        return new Rage(getCurrent());
    }

}
