package net.mrdinster.dustysouls.item;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.equipment.ArmorType;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.entity.ModEntities;
import net.mrdinster.dustysouls.food.ModFoods;
import net.mrdinster.dustysouls.item.custom.GloveItem;
import net.mrdinster.dustysouls.item.custom.TempusPickaxeItem;

import java.util.function.Function;

public class ModItems {
    //Remember datagen (ModModelProvider)

    public static final Item TELDEN_UPGRADE = registerItem("telden_upgrade",
            properties -> new Item(properties.rarity(Rarity.UNCOMMON)));
    public static final Item TELDEN_INGOT = registerItem("telden_ingot", Item::new);
    public static final Item TEMPUS_STONE = registerItem("tempus_stone", Item::new);
    public static final Item TEMPUS_NUGGET = registerItem("tempus_nugget", Item::new);
    public static final Item CLAWS = registerItem("claws", Item::new);
    public static final Item SLIMUS_BALL = registerItem("slimus_ball", Item::new);
    public static final Item ANCIENT_STICK = registerItem("ancient_stick", Item::new);

    //Foods
    public static final Item WEBBIES = registerItem("webbies", properties ->
            new Item(properties.food(ModFoods.WEBBIES, ModFoods.WEBBIES_CONSUMABLE)));
    public static final Item KEAR = registerItem("kear", properties ->
            new Item(properties.food(ModFoods.KEAR)));
    public static final Item NANGU = registerItem("nangu", properties ->
            new Item(properties.food(ModFoods.NANGU, ModFoods.NANGU_CONSUMABLE)));
    public static final Item FRUIT_SALAD = registerItem("fruit_salad", properties ->
            new Item(properties.food(ModFoods.FRUIT_SALAD, ModFoods.FRUIT_SALAD_CONSUMABLE).usingConvertsTo(Items.BOWL).stacksTo(1)));
    public static final Item CRAKED_EGG = registerItem("craked_egg", properties ->
            new Item(properties.food(ModFoods.CRACKED_EGG, ModFoods.CRACKED_EGG_CONSUMABLE)));

    //Spawn Eggs
    public static final Item REMAGER_SPAWN_EGG = registerItem("remager_spawn_egg",
            setting -> new SpawnEggItem(setting.spawnEgg(ModEntities.REMAGER)));
    public static final Item DODO_SPAWN_EGG = registerItem("dodo_spawn_egg",
            setting -> new SpawnEggItem(setting.spawnEgg(ModEntities.DODO)));
    public static final Item TREKKER_SPAWN_EGG = registerItem("trekker_spawn_egg",
            setting -> new SpawnEggItem(setting.spawnEgg(ModEntities.TREKKER)));
    public static final Item FLEAGLE_SPAWN_EGG = registerItem("fleagle_spawn_egg",
            setting -> new SpawnEggItem(setting.spawnEgg(ModEntities.FLEAGLE)));
    public static final Item BOULDER_SPAWN_EGG = registerItem("boulder_spawn_egg",
            setting -> new SpawnEggItem(setting.spawnEgg(ModEntities.BOULDER)));
    public static final Item SCRATCH_SPAWN_EGG = registerItem("scratch_spawn_egg",
            setting -> new SpawnEggItem(setting.spawnEgg(ModEntities.SCRATCH)));
    public static final Item CREEPUS_SPAWN_EGG = registerItem("creepus_spawn_egg",
            setting -> new SpawnEggItem(setting.spawnEgg(ModEntities.CREEPUS)));

    //Complex Items
    public static final Item TEMPUS_PICKAXE = registerItem("tempus_pickaxe",
            properties -> new TempusPickaxeItem(properties.durability(32)));
    public static final Item GLOVE = registerItem("glove",
            properties -> new GloveItem(properties.durability(320)));

    //Armor
    public static final Item TELDEN_HELMET = registerItem("telden_helmet", properties ->
            new Item(properties.humanoidArmor(ModArmorMaterials.TELDEN_ARMOR_MATERIAL, ArmorType.HELMET)));
    public static final Item TELDEN_CHESTPLATE = registerItem("telden_chestplate", properties ->
            new Item(properties.humanoidArmor(ModArmorMaterials.TELDEN_ARMOR_MATERIAL, ArmorType.CHESTPLATE)));
    public static final Item TELDEN_LEGGINGS = registerItem("telden_leggings", properties ->
            new Item(properties.humanoidArmor(ModArmorMaterials.TELDEN_ARMOR_MATERIAL, ArmorType.LEGGINGS)));
    public static final Item TELDEN_BOOTS = registerItem("telden_boots", properties ->
            new Item(properties.humanoidArmor(ModArmorMaterials.TELDEN_ARMOR_MATERIAL, ArmorType.BOOTS)));

    private static Item registerItem(String name, Function<Item.Properties, Item> function) {
        return Registry.register(BuiltInRegistries.ITEM, Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, name),
                function.apply(new Item.Properties().setId(ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, name)))));
    }

    public static void registerModItems() {
        DustySouls.LOGGER.info("Registering Mod Items for" + DustySouls.MOD_ID);
    }
}
