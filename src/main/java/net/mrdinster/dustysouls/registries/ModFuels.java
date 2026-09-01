package net.mrdinster.dustysouls.registries;

import net.fabricmc.fabric.api.registry.FuelValueEvents;
import net.mrdinster.dustysouls.item.ModItems;

public class ModFuels {
    public static void registerFuels() {
        FuelValueEvents.BUILD.register((builder, context) -> {
            builder.add(ModItems.ANCIENT_STICK, 1200);
                });
    }
}
