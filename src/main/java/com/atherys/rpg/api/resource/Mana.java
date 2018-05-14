package com.atherys.rpg.api.resource;

import org.spongepowered.api.text.format.TextColors;

public class Mana extends AbstractResource {

    protected Mana(double starting) {
        super(
                TextColors.BLUE,
                "atherys:mana",
                "Mana",
                100.0d,
                starting
        );
    }

}
