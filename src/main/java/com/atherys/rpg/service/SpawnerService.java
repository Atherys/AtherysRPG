package com.atherys.rpg.service;

import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.config.mob.SpawnersConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Singleton
public class SpawnerService {
    @Inject
    private SpawnersConfig spawnersConfig;

    @Inject
    private MobService mobService;

    private List<Spawner> spawners;
    private Task spawnerTask;

    public void init() {
        spawners = spawnersConfig.SPAWNERS.stream().map(spawnerConfig -> {
            EntityArchetype mobType = mobService.getMob(spawnerConfig.MOB_TYPE).orElseGet(() -> {
                AtherysRPG.getInstance().getLogger().warn("Spawner with location {} has misconfigured entity type.", spawnerConfig.LOCATION);
                return EntityArchetype.of(EntityTypes.COW);
            });

            return new Spawner(
                    spawnerConfig.LOCATION,
                    mobType,
                    spawnerConfig.RADIUS,
                    spawnerConfig.SURFACE_ONLY,
                    spawnerConfig.SPAWN_INTERVAL
            );
        })
        .collect(Collectors.toList());

        Task.builder()
                .execute(__ -> spawnMobs())
                .interval(1, TimeUnit.MINUTES)
                .submit(AtherysRPG.getInstance());
    }

    private void spawnMobs() {
        for (Spawner spawner : spawners) {
        }
    }

    private static class Spawner {
        private final Location<World> location;
        private final EntityArchetype mobType;
        private final double radius;
        private final boolean surfaceOnly;
        private final Duration spawnInterval;

        private int currentlySpawned;

        private Spawner(Location<World> location, EntityArchetype mobType, double radius, boolean surfaceOnly, Duration spawnInterval) {
            this.location = location;
            this.mobType = mobType;
            this.radius = radius;
            this.surfaceOnly = surfaceOnly;
            this.spawnInterval = spawnInterval;
        }
    }
}
