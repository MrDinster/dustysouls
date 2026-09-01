package net.mrdinster.dustysouls.item;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.tags.ModTags;

public class ModArmorMaterials {
    public static final ResourceKey<? extends Registry<EquipmentAsset>> REGISTRY_KEY =
            ResourceKey.createRegistryKey(Identifier.withDefaultNamespace("equipment_asset"));
    public static final ResourceKey<EquipmentAsset> TELDEN_KEY = ResourceKey.create(REGISTRY_KEY, Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "telden"));

    public static final ArmorMaterial TELDEN_ARMOR_MATERIAL = new ArmorMaterial(750,
            ArmorMaterials.makeDefense(3, 6, 8, 3, 19),
            15, SoundEvents.ARMOR_EQUIP_NETHERITE, 2, 0.3F, ModTags.Items.TELDEN_REPAIR, TELDEN_KEY);
}
