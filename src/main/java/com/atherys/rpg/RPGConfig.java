package com.atherys.rpg;

import com.atherys.core.utils.PluginConfig;
import ninja.leaping.configurate.objectmapping.Setting;

import java.io.IOException;

public class RPGConfig extends PluginConfig {

    @Setting("is-default")
    public boolean IS_DEFAULT = true;

    @Setting("global-cooldown")
    public long GLOBAL_COOLDOWN = 500;

    @Setting("resource-regen-ticks")
    public int RESOURCE_REGEN_TICKS = 1;

    @Setting("effect-apply-ticks")
    public int EFFECT_APPLY_TICKS = 1;

    protected RPGConfig() throws IOException {
        super(AtherysRPG.getInstance().getWorkingDirectory(), "config.conf");
    }
}
