package net.mrdinster.dustysouls.block;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.block.custom.DodoEggBlock;
import net.mrdinster.dustysouls.block.custom.FleagleEggBlock;
import net.mrdinster.dustysouls.block.custom.TrekkerEggBlock;

import java.util.function.Function;

public class ModBlocks {

    public static final Block DODO_EGG = registerBlock("dodo_egg",
            properties -> new DodoEggBlock(properties.strength(0.5f)
                    .sound(SoundType.METAL).randomTicks()));

    public static final Block TREKKER_EGG = registerBlock("trekker_egg",
            properties -> new TrekkerEggBlock(properties.strength(1f)
                    .sound(SoundType.METAL).randomTicks()));

    public static final Block FLEAGLE_EGG = registerBlock("fleagle_egg",
            properties -> new FleagleEggBlock(properties.strength(2f)
                    .sound(SoundType.METAL).randomTicks()));

    private static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> function) {
        Block toRegister = function.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK,
                Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, name))));
        registerBlockItem(name, toRegister);
        return Registry.register(BuiltInRegistries.BLOCK, Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, name), toRegister);
    }

    public static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, name),
                new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix()
                        .setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, name)))));
    }

    public static void registerModBlocks() {
        DustySouls.LOGGER.info("Registering Mod Blocks for" + DustySouls.MOD_ID);
    }
}
