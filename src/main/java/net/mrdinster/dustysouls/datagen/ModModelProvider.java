package net.mrdinster.dustysouls.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.mrdinster.dustysouls.block.ModBlocks;
import net.mrdinster.dustysouls.item.ModArmorMaterials;
import net.mrdinster.dustysouls.item.ModItems;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricPackOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createNonTemplateModelBlock(ModBlocks.DODO_EGG);
        blockModelGenerators.createNonTemplateModelBlock(ModBlocks.TREKKER_EGG);
        blockModelGenerators.createNonTemplateModelBlock(ModBlocks.FLEAGLE_EGG);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModItems.TEMPUS_PICKAXE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.TELDEN_UPGRADE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.TELDEN_INGOT, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.TEMPUS_STONE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.TEMPUS_NUGGET, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.CLAWS, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GLOVE, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SLIMUS_BALL, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.ANCIENT_STICK, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.WEBBIES, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.KEAR, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.NANGU, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.FRUIT_SALAD, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.CRAKED_EGG, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.REMAGER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.DODO_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.TREKKER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.FLEAGLE_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.BOULDER_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.SCRATCH_SPAWN_EGG, ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.CREEPUS_SPAWN_EGG, ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateTrimmableItem(ModItems.TELDEN_HELMET, ModArmorMaterials.TELDEN_KEY,
                ItemModelGenerators.TRIM_PREFIX_HELMET, false);
        itemModelGenerators.generateTrimmableItem(ModItems.TELDEN_CHESTPLATE, ModArmorMaterials.TELDEN_KEY,
                ItemModelGenerators.TRIM_PREFIX_CHESTPLATE, false);
        itemModelGenerators.generateTrimmableItem(ModItems.TELDEN_LEGGINGS, ModArmorMaterials.TELDEN_KEY,
                ItemModelGenerators.TRIM_PREFIX_LEGGINGS, false);
        itemModelGenerators.generateTrimmableItem(ModItems.TELDEN_BOOTS, ModArmorMaterials.TELDEN_KEY,
                ItemModelGenerators.TRIM_PREFIX_BOOTS, false);
    }




}
