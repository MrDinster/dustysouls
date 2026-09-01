package net.mrdinster.dustysouls.menu;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.menu.custom.RemagerMenu;

public class ModMenuTypes {

    public static final MenuType<RemagerMenu> REMAGER_MENU_TYPE = Registry.register(
            BuiltInRegistries.MENU,
            Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "remager_menu"),
            new MenuType<>(RemagerMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );

    public static void registerModMenuTypes() {
        DustySouls.LOGGER.info("Registering ModMenuTypes for " + DustySouls.MOD_ID);
    }
}
