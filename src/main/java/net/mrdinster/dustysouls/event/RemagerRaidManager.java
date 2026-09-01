package net.mrdinster.dustysouls.event;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.BossEvent;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.phys.AABB;
import net.mrdinster.dustysouls.entity.ModEntities;

import java.util.List;

public class RemagerRaidManager {
    private static ServerBossEvent bossEvent;
    private static int currentWave = 0;
    private static BlockPos raidCenter;
    private static int waveCooldownTicks = 0;

    private static int totalMobsInWave = 0;

    public static boolean isRaidActive(Level level) {
        return bossEvent != null;
    }

    public static boolean isRaidActiveInArea(Level level, BlockPos pos) {
        if (bossEvent == null || raidCenter == null || level == null) {
            return false;
        }
        return pos.closerThan(raidCenter, 50.0D);
    }

    public static void startRaid(ServerLevel level, BlockPos center) {
        if (bossEvent != null) return;

        raidCenter = center;
        currentWave = 1;
        waveCooldownTicks = 60;

        bossEvent = new ServerBossEvent(
                java.util.UUID.randomUUID(),
                Component.literal("The Uprising"),
                BossEvent.BossBarColor.GREEN,
                BossEvent.BossBarOverlay.NOTCHED_10
        );
        bossEvent.setProgress(1.0F);

        spawnWave(level);
    }

    public static void tick(ServerLevel serverLevel) {
        if (bossEvent == null || serverLevel == null || raidCenter == null) {
            return;
        }

        if (waveCooldownTicks > 0) {
            waveCooldownTicks--;
        }

        List<ServerPlayer> jugadoresEnRango = serverLevel.getPlayers(player ->
                player.blockPosition().closerThan(raidCenter, 50.0D) && player.isAlive()
        );

        for (ServerPlayer player : serverLevel.getPlayers(p -> true)) {
            if (jugadoresEnRango.contains(player)) {
                if (!bossEvent.getPlayers().contains(player)) {
                    bossEvent.addPlayer(player);
                }
            } else {
                bossEvent.removePlayer(player);
            }
        }

        if (jugadoresEnRango.isEmpty()) {
            return;
        }

        AABB areaEscanear = new AABB(raidCenter).inflate(64.0D);
        List<Mob> mobsVivosEnMundo = serverLevel.getEntitiesOfClass(Mob.class, areaEscanear, mob ->
                mob.isAlive() && (mob.getType() == ModEntities.BOULDER || mob.getType() == ModEntities.CREEPUS || mob.getType() == ModEntities.SCRATCH)
        );

        int mobsVivosCount = mobsVivosEnMundo.size();

        if (mobsVivosCount == 0 && waveCooldownTicks == 0) {
            advanceWave(serverLevel);
            return;
        }

        if (totalMobsInWave > 0 && bossEvent != null) {
            float progreso = (float) mobsVivosCount / (float) totalMobsInWave;
            bossEvent.setProgress(net.minecraft.util.Mth.clamp(progreso, 0.0F, 1.0F));
        }
    }

    private static void spawnWave(ServerLevel level) {
        AABB raidArea = new AABB(raidCenter).inflate(40.0D);
        int playerCount = Math.max(1, level.getEntitiesOfClass(ServerPlayer.class, raidArea).size());

        int numBoulders = 0;
        int numCreepus = 0;
        int numScratchs = 0;

        if (currentWave == 1) {
            numBoulders = 1 + playerCount;
            numCreepus = 2 * playerCount;
        } else if (currentWave == 2) {
            numBoulders = 2 + playerCount;
            numCreepus = 3 * playerCount;
            numScratchs = 1 + playerCount;
        } else if (currentWave == 3) {
            numBoulders = 2 + playerCount;
            numCreepus = 4 * playerCount;
            numScratchs = 3 + playerCount;
        }

        totalMobsInWave = numBoulders + numCreepus + numScratchs;

        for (int i = 0; i < numBoulders; i++) spawnRaidMob(level, ModEntities.BOULDER);
        for (int i = 0; i < numCreepus; i++) spawnRaidMob(level, ModEntities.CREEPUS);
        for (int i = 0; i < numScratchs; i++) spawnRaidMob(level, ModEntities.SCRATCH);
    }

    private static void spawnRaidMob(ServerLevel level, EntityType<? extends Mob> entityType) {
        Mob mob = entityType.create(level, EntitySpawnReason.EVENT);
        if (mob != null) {
            double offsetX = (level.getRandom().nextDouble() - 0.5D) * 24.0D;
            double offsetZ = (level.getRandom().nextDouble() - 0.5D) * 24.0D;
            BlockPos spawnPos = level.getHeightmapPos(Heightmap.Types.WORLD_SURFACE, raidCenter.offset((int)offsetX, 0, (int)offsetZ));


            mob.absSnapTo(spawnPos.getX() + 0.5D, spawnPos.getY(), spawnPos.getZ() + 0.5D, level.getRandom().nextFloat() * 360.0F, 0.0F);
            mob.finalizeSpawn(level, level.getCurrentDifficultyAt(spawnPos), EntitySpawnReason.EVENT, null);
            level.addFreshEntity(mob);

            double spawnX = spawnPos.getX() + 0.5D;
            double spawnY = spawnPos.getY();
            double spawnZ = spawnPos.getZ() + 0.5D;

            level.sendParticles(ParticleTypes.POOF, spawnX, spawnY + 0.5D, spawnZ, 20, 0.3, 0.5, 0.3, 0.05);

            level.sendParticles(ParticleTypes.WITCH, spawnX, spawnY + 1.0D, spawnZ, 15, 0.4, 0.6, 0.4, 0.02);
        }
    }

    private static void advanceWave(ServerLevel level) {
        currentWave++;
        waveCooldownTicks = 80;

        if (currentWave > 3) {
            endRaid(true, level);
        } else {
            if (bossEvent != null) {
                //bossEvent.setName(Component.literal("The Uprising"));
                bossEvent.setProgress(1.0F);
            }
            spawnWave(level);
        }
    }

    public static void endRaid(boolean victory, ServerLevel level) {
        if (bossEvent != null) {
            bossEvent.removeAllPlayers();
            bossEvent = null;
        }

        if (victory && level != null && raidCenter != null) {
            level.getPlayers(p -> p.blockPosition().closerThan(raidCenter, 50.0D)).forEach(player -> {
                player.sendSystemMessage(Component.literal("§aThe Souls have been avenged! The retribution is over."));
            });
        }

        raidCenter = null;
        currentWave = 0;
        totalMobsInWave = 0;
    }
}

