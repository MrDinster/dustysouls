package net.mrdinster.dustysouls.tags;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.mrdinster.dustysouls.DustySouls;

public class ModTags {
    public static class Items {
        public static final TagKey<Item> TELDEN_REPAIR = createTag("telden_repair");


        public static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, name));
        }
    }
}
