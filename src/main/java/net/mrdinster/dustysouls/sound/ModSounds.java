package net.mrdinster.dustysouls.sound;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.mrdinster.dustysouls.DustySouls;

public class ModSounds {


    public static final SoundEvent DODO_IDLE = registerSoundEvent("dodo_idle");
    public static final SoundEvent DODO_HURT = registerSoundEvent("dodo_hurt");
    public static final SoundEvent DODO_DEATH = registerSoundEvent("dodo_death");

    public static final SoundEvent BOULDER_HURT = registerSoundEvent("boulder_hurt");
    public static final SoundEvent BOULDER_DEATH = registerSoundEvent("boulder_death");

    public static final SoundEvent FLEAGLE_IDLE = registerSoundEvent("fleagle_idle");
    public static final SoundEvent FLEAGLE_HURT = registerSoundEvent("fleagle_hurt");
    public static final SoundEvent FLEAGLE_DEATH = registerSoundEvent("fleagle_death");

    public static final SoundEvent LILCHICK_IDLE = registerSoundEvent("lilchick_idle");
    public static final SoundEvent LILCHICK_HURT = registerSoundEvent("lilchick_hurt");
    public static final SoundEvent LILCHICK_DEATH = registerSoundEvent("lilchick_death");

    public static final SoundEvent TREKKER_IDLE = registerSoundEvent("trekker_idle");

    public static final SoundEvent SCRATCH_HURT = registerSoundEvent("scratch_hurt");


    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, name);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
    }


    public static void registerSounds () {
        DustySouls.LOGGER.info("Registering Mod Sounds for" + DustySouls.MOD_ID);
    }
}
