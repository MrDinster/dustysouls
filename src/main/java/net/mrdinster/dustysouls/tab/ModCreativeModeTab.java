package net.mrdinster.dustysouls.tab;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.block.ModBlocks;
import net.mrdinster.dustysouls.item.ModItems;

public class ModCreativeModeTab {
    public static final CreativeModeTab DUSTY_SOULS_TAB = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "dusty_souls_item"),
            FabricCreativeModeTab.builder().icon( () -> new ItemStack(ModItems.TELDEN_INGOT))
                    .title(Component.literal("Dusty Souls"))

                    //check the literal in this case

                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.TEMPUS_PICKAXE);
                        output.accept(ModItems.TELDEN_UPGRADE);
                        output.accept(ModItems.TELDEN_INGOT);
                        output.accept(ModItems.TEMPUS_STONE);
                        output.accept(ModItems.TEMPUS_NUGGET);

                        output.accept(ModItems.TELDEN_HELMET);
                        output.accept(ModItems.TELDEN_CHESTPLATE);
                        output.accept(ModItems.TELDEN_LEGGINGS);
                        output.accept(ModItems.TELDEN_BOOTS);

                        output.accept(ModItems.CLAWS);
                        output.accept(ModItems.GLOVE);
                        output.accept(ModItems.SLIMUS_BALL);
                        output.accept(ModItems.ANCIENT_STICK);

                        output.accept(ModItems.WEBBIES);
                        output.accept(ModItems.KEAR);
                        output.accept(ModItems.NANGU);
                        output.accept(ModItems.FRUIT_SALAD);
                        output.accept(ModItems.CRAKED_EGG);

                        output.accept(ModItems.REMAGER_SPAWN_EGG);
                        output.accept(ModItems.DODO_SPAWN_EGG);
                        output.accept(ModItems.TREKKER_SPAWN_EGG);
                        output.accept(ModItems.FLEAGLE_SPAWN_EGG);
                        output.accept(ModItems.BOULDER_SPAWN_EGG);
                        output.accept(ModItems.SCRATCH_SPAWN_EGG);
                        output.accept(ModItems.CREEPUS_SPAWN_EGG);

                        output.accept(ModBlocks.DODO_EGG);
                        output.accept(ModBlocks.TREKKER_EGG);
                        output.accept(ModBlocks.FLEAGLE_EGG);


                    })

                    .build());

    public static void registerModCreativeModeTab() {
        DustySouls.LOGGER.info("Registering Creative Mode Tabs for" + DustySouls.MOD_ID);
    }

}
