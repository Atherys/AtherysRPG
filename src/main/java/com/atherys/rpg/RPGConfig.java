package com.atherys.rpg;

import com.atherys.core.utils.PluginConfig;

import java.io.IOException;

public class RPGConfig extends PluginConfig {

    public long GLOBAL_COOLDOWN = 500;

    protected RPGConfig(String directory, String filename) throws IOException {
        super(directory, filename);
    }
}
