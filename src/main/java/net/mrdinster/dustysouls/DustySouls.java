package net.mrdinster.dustysouls;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.mrdinster.dustysouls.block.ModBlocks;
import net.mrdinster.dustysouls.entity.ModEntities;
import net.mrdinster.dustysouls.entity.custom.*;
import net.mrdinster.dustysouls.event.RemagerRaidManager;
import net.mrdinster.dustysouls.item.ModItems;
import net.mrdinster.dustysouls.menu.ModMenuTypes;
import net.mrdinster.dustysouls.particle.ModParticleTypes;
import net.mrdinster.dustysouls.registries.ModFuels;
import net.mrdinster.dustysouls.sound.ModSounds;
import net.mrdinster.dustysouls.tab.ModCreativeModeTab;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DustySouls implements ModInitializer {
	public static final String MOD_ID = "dustysouls";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


	@Override
	public void onInitialize() {

		ModCreativeModeTab.registerModCreativeModeTab();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEntities.registerModEntities();
		ModParticleTypes.registerParticles();
		ModMenuTypes.registerModMenuTypes();
		ModFuels.registerFuels();
		ModSounds.registerSounds();

		ServerTickEvents.END_LEVEL_TICK.register(world -> {
			if (world instanceof ServerLevel serverLevel) {
				RemagerRaidManager.tick(serverLevel);
			}
		});



		Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath
				(DustySouls.MOD_ID, "sleep_particle"), ModParticleTypes.SLEEP_PARTICLE);
		Registry.register(BuiltInRegistries.PARTICLE_TYPE, Identifier.fromNamespaceAndPath
				(DustySouls.MOD_ID, "dodo_feather_particle"), ModParticleTypes.DODO_FEATHER_PARTICLE);

		//Attributes
		FabricDefaultAttributeRegistry.register(ModEntities.REMAGER, RemagerEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.DODO, DodoEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.TREKKER, TrekkerEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.FLEAGLE, FleagleEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.BOULDER, BoulderEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.SCRATCH, ScratchEntity.createAttributes());
		FabricDefaultAttributeRegistry.register(ModEntities.CREEPUS, CreepusEntity.createAttributes());
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
