package com.atherys.rpg.config.mob;

import com.flowpowered.math.vector.Vector3d;
import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@ConfigSerializable
public class SpawnerConfig {
    @Setting("world")
    public String WORLD;

    @Setting("position")
    public Vector3d POSITION;

    @Setting("radius")
    public double RADIUS = 50;

    @Setting("surface-only")
    public boolean SURFACE_ONLY = false;

    @Setting("mob-type")
    public String MOB_TYPE = "zombie";

    @Setting("spawn-interval")
    public Duration SPAWN_INTERVAL = Duration.of(10, ChronoUnit.MINUTES);

    @Setting("maximum-spawned")
    public int MAXIMUM = 15;

    @Setting("maximum-wave")
    public int WAVE = 3;
}
