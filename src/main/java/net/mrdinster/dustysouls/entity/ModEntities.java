package net.mrdinster.dustysouls.entity;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.entity.client.render.BoulderRenderer;
import net.mrdinster.dustysouls.entity.custom.*;

public class ModEntities {

    public static final EntityType<RemagerEntity> REMAGER = register("remager",
            EntityType.Builder.<RemagerEntity>of(RemagerEntity::new, MobCategory.CREATURE)
                    .sized(0.75f, 1.7f));

    public static final EntityType<DodoEntity> DODO = register("dodo",
            EntityType.Builder.<DodoEntity>of(DodoEntity::new, MobCategory.CREATURE)
                    .sized(0.8f, 1.8f)
    );

    public static final EntityType<TrekkerEntity> TREKKER = register("trekker",
            EntityType.Builder.<TrekkerEntity>of(TrekkerEntity::new, MobCategory.CREATURE)
                    .sized(1.1f, 2.0f)
    );

    public static final EntityType<FleagleEntity> FLEAGLE = register("fleagle",
            EntityType.Builder.<FleagleEntity>of(FleagleEntity::new, MobCategory.CREATURE)
                    .sized(2.0f, 4.6f)
    );

    public static final EntityType<BoulderEntity> BOULDER = register("boulder",
            EntityType.Builder.<BoulderEntity>of(BoulderEntity::new, MobCategory.MONSTER)
                    .sized(0.8f, 1.8f)
    );


    public static final EntityType<ScratchEntity> SCRATCH = register("scratch",
            EntityType.Builder.<ScratchEntity>of(ScratchEntity::new, MobCategory.MONSTER)
                    .sized(1.2f, 1.9f)
    );

    public static final EntityType<CreepusEntity> CREEPUS = register("creepus",
            EntityType.Builder.<CreepusEntity>of(CreepusEntity::new, MobCategory.MONSTER)
                    .sized(0.9f, 2.1f)
    );

    private static <T extends Entity> EntityType<T> register(String name, EntityType.Builder<T> builder) {
        ResourceKey<EntityType<?>> key = ResourceKey.create(Registries.ENTITY_TYPE, Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, name));
        return Registry.register(BuiltInRegistries.ENTITY_TYPE, key, builder.build(key));
    }


    public static void registerModEntities() {
        DustySouls.LOGGER.info("Registering EntityTypes for " + DustySouls.MOD_ID);
    }

}
