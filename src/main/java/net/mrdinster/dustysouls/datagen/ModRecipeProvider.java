package net.mrdinster.dustysouls.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.SmithingTransformRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.mrdinster.dustysouls.item.ModItems;
import net.mrdinster.dustysouls.tags.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        return new RecipeProvider(registries, output) {
            @Override
            public void buildRecipes() {
                nineBlockStorageRecipes(RecipeCategory.MISC, ModItems.TEMPUS_NUGGET, RecipeCategory.MISC, ModItems.TEMPUS_STONE);


                copySmithingTemplate(ModItems.TELDEN_UPGRADE, Items.MOSS_BLOCK);

                SmithingTransformRecipeBuilder.smithing(
                                Ingredient.of(ModItems.TELDEN_UPGRADE),
                                Ingredient.of(Items.DIAMOND_HELMET),
                                Ingredient.of(ModItems.TELDEN_INGOT),
                                RecipeCategory.COMBAT,
                                ModItems.TELDEN_HELMET)
                        .unlocks("has_telden_upgrade", this.has(ModItems.TELDEN_UPGRADE))
                        .save(output, "telden_helmet_smithing");

                SmithingTransformRecipeBuilder.smithing(
                                Ingredient.of(ModItems.TELDEN_UPGRADE),
                                Ingredient.of(Items.DIAMOND_CHESTPLATE),
                                Ingredient.of(ModItems.TELDEN_INGOT),
                                RecipeCategory.COMBAT,
                                ModItems.TELDEN_CHESTPLATE)
                        .unlocks("has_telden_upgrade", this.has(ModItems.TELDEN_UPGRADE))
                        .save(output, "telden_chestplate_smithing");

                SmithingTransformRecipeBuilder.smithing(
                                Ingredient.of(ModItems.TELDEN_UPGRADE),
                                Ingredient.of(Items.DIAMOND_LEGGINGS),
                                Ingredient.of(ModItems.TELDEN_INGOT),
                                RecipeCategory.COMBAT,
                                ModItems.TELDEN_LEGGINGS)
                        .unlocks("has_telden_upgrade", this.has(ModItems.TELDEN_UPGRADE))
                        .save(output, "telden_leggings_smithing");

                SmithingTransformRecipeBuilder.smithing(
                                Ingredient.of(ModItems.TELDEN_UPGRADE),
                                Ingredient.of(Items.DIAMOND_BOOTS),
                                Ingredient.of(ModItems.TELDEN_INGOT),
                                RecipeCategory.COMBAT,
                                ModItems.TELDEN_BOOTS)
                        .unlocks("has_telden_upgrade", this.has(ModItems.TELDEN_UPGRADE))
                        .save(output, "telden_boots_smithing");





                shaped(RecipeCategory.TOOLS, ModItems.TEMPUS_PICKAXE)
                        .pattern("TST")
                        .pattern(" A ")
                        .pattern(" A ")
                        .define('T', ModItems.TEMPUS_STONE)
                        .define('A', ModItems.ANCIENT_STICK)
                        .define('S', ModItems.SLIMUS_BALL)
                        .unlockedBy(getHasName(ModItems.ANCIENT_STICK), has(ModItems.ANCIENT_STICK))
                        .save(output);

                shapeless(RecipeCategory.TOOLS, ModItems.GLOVE)
                        .requires(ModItems.SLIMUS_BALL)
                        .requires(ModItems.CLAWS)
                        .requires(Items.LEATHER)
                        .unlockedBy(getHasName(ModItems.CLAWS), has(ModItems.CLAWS))
                        .unlockedBy(getHasName(ModItems.SLIMUS_BALL), has(ModItems.SLIMUS_BALL))
                        .save(output);


                shapeless(RecipeCategory.FOOD, ModItems.FRUIT_SALAD)
                        .requires(ModItems.KEAR)
                        .requires(ModItems.NANGU)
                        .requires(ModItems.WEBBIES)
                        .requires(Items.BOWL)
                        .unlockedBy(getHasName(ModItems.KEAR), has(ModItems.KEAR))
                        .unlockedBy(getHasName(Items.STICK), has(Items.STICK))
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "DustySouls Recipes";
    }
}
