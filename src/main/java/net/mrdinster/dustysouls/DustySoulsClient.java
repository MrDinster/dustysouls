package net.mrdinster.dustysouls;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleProviderRegistry;
import net.fabricmc.fabric.impl.client.rendering.EntityRendererRegistryImpl;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.particle.EndRodParticle;
import net.mrdinster.dustysouls.entity.ModEntities;
import net.mrdinster.dustysouls.entity.client.render.*;
import net.mrdinster.dustysouls.menu.ModMenuTypes;
import net.mrdinster.dustysouls.menu.custom.RemagerScreen;
import net.mrdinster.dustysouls.particle.ModParticleTypes;


public class DustySoulsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        EntityRendererRegistryImpl.register(ModEntities.REMAGER, RemagerRenderer::new);
        EntityRendererRegistryImpl.register(ModEntities.DODO, DodoRenderer::new);
        EntityRendererRegistryImpl.register(ModEntities.TREKKER, TrekkerRenderer::new);
        EntityRendererRegistryImpl.register(ModEntities.FLEAGLE, FleagleRenderer::new);
        EntityRendererRegistryImpl.register(ModEntities.BOULDER, BoulderRenderer::new);
        EntityRendererRegistryImpl.register(ModEntities.SCRATCH, ScratchRenderer::new);
        EntityRendererRegistryImpl.register(ModEntities.CREEPUS, CreepusRenderer::new);


        ParticleProviderRegistry.getInstance().register(ModParticleTypes.SLEEP_PARTICLE, EndRodParticle.Provider::new);
        ParticleProviderRegistry.getInstance().register(ModParticleTypes.DODO_FEATHER_PARTICLE, EndRodParticle.Provider::new);

        MenuScreens.register(ModMenuTypes.REMAGER_MENU_TYPE, RemagerScreen::new);

    }
}
