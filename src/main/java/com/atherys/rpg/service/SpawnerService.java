package com.atherys.rpg.service;

import com.atherys.core.utils.MathUtils;
import com.atherys.rpg.AtherysRPG;
import com.atherys.rpg.config.mob.SpawnersConfig;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.apache.commons.lang3.RandomUtils;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.EntityArchetype;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.entity.living.Hostile;
import org.spongepowered.api.entity.living.Living;
import org.spongepowered.api.event.CauseStackManager;
import org.spongepowered.api.event.cause.EventContextKeys;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
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

    private static Random rand = new Random();

    public void init() {
        spawners = spawnersConfig.SPAWNERS.stream().map(spawnerConfig -> {
            EntityArchetype mobType = mobService.getMob(spawnerConfig.MOB_TYPE).orElseGet(() -> {
                AtherysRPG.getInstance().getLogger().warn("Spawner with location {} has misconfigured entity type.", spawnerConfig.POSITION);
                return EntityArchetype.of(EntityTypes.COW);
            });

            World world = Sponge.getServer().getWorld(spawnerConfig.WORLD).orElseThrow(() -> new IllegalArgumentException("World " + spawnerConfig.WORLD + " is not a valid world."));

            return new Spawner(
                    world.getLocation(spawnerConfig.POSITION),
                    mobType,
                    spawnerConfig.RADIUS,
                    spawnerConfig.SURFACE_ONLY,
                    spawnerConfig.SPAWN_INTERVAL,
                    spawnerConfig.MAXIMUM,
                    spawnerConfig.WAVE
            );
        })
        .collect(Collectors.toList());

        Task.builder()
                .execute(__ -> spawnMobs())
                .interval(1, TimeUnit.SECONDS)
                .submit(AtherysRPG.getInstance());
    }

    private void spawnMobs() {
        try (CauseStackManager.StackFrame frame = Sponge.getCauseStackManager().pushCauseFrame()) {
            frame.pushCause(AtherysRPG.getInstance());
            frame.addContext(EventContextKeys.SPAWN_TYPE, SpawnTypes.MOB_SPAWNER);

            for (Spawner spawner : spawners) {
                int spawned = spawner.currentlySpawned.size();
                if (spawner.secondsSinceLastSpawn >= spawner.spawnIntervalSeconds && spawned < spawner.maximum) {
                    spawner.secondsSinceLastSpawn = 0;
                    int toSpawn = Math.min(spawner.maximumWaveSize, spawner.maximum - spawned);
                    Location<World> location = spawner.location;

                    for (int i = 0; i < toSpawn; i++) {
                        double x = location.getX() + ThreadLocalRandom.current().nextDouble(-spawner.radius, spawner.radius);
                        double z = location.getZ() + ThreadLocalRandom.current().nextDouble(-spawner.radius, spawner.radius);
                        double y = location.getExtent().getHighestYAt((int) x, (int) z);

                        spawner.mobType.apply(location.getExtent().getLocation(x, y, z)).ifPresent(entity -> {
                            spawner.currentlySpawned.add(entity.getUniqueId());
                        });
                    }
                }
                spawner.secondsSinceLastSpawn++;
            }
        }
    }

    public void removeMob(Living mob) {
        for (Spawner spawner : spawners) {
            spawner.currentlySpawned.remove(mob.getUniqueId());
        }
    }

    private static class Spawner {
        private final Location<World> location;
        private final EntityArchetype mobType;
        private final double radius;
        private final boolean surfaceOnly;
        private final long spawnIntervalSeconds;
        private final int maximum;
        private final int maximumWaveSize;

        private Set<UUID> currentlySpawned;
        private long secondsSinceLastSpawn;

        private Spawner(Location<World> location, EntityArchetype mobType, double radius, boolean surfaceOnly, Duration spawnInterval, int maximum, int maximumWaveSize) {
            this.location = location;
            this.mobType = mobType;
            this.radius = radius;
            this.surfaceOnly = surfaceOnly;
            this.spawnIntervalSeconds = spawnInterval.getSeconds();
            this.maximum = maximum;
            this.maximumWaveSize = maximumWaveSize;

            this.currentlySpawned = new HashSet<>();
        }
    }
}
