package net.mrdinster.dustysouls.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.mrdinster.dustysouls.DustySouls;

public class ModParticleTypes {

    public static final SimpleParticleType SLEEP_PARTICLE = FabricParticleTypes.simple();
    public static final SimpleParticleType DODO_FEATHER_PARTICLE = FabricParticleTypes.simple();


    public static void registerParticles() {
        DustySouls.LOGGER.info("Registering Mod Particle Types for" + DustySouls.MOD_ID);
    }
}
