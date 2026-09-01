package net.mrdinster.dustysouls.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.builder.SoundTypeBuilder;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricSoundsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.mrdinster.dustysouls.DustySouls;
import net.mrdinster.dustysouls.sound.ModSounds;

import java.util.concurrent.CompletableFuture;

public class ModSoundsProvider extends FabricSoundsProvider {
    public ModSoundsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider registryLookup, FabricSoundsProvider.SoundExporter exporter) {

        exporter.add(ModSounds.DODO_IDLE, SoundTypeBuilder.of(ModSounds.DODO_IDLE).subtitle("sounds.dustysouls.dodo_idle")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "dodo_idle1")))
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "dodo_idle2"))));

        exporter.add(ModSounds.DODO_HURT, SoundTypeBuilder.of(ModSounds.DODO_HURT).subtitle("sounds.dustysouls.dodo_hurt")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "dodo_hurt"))));

        exporter.add(ModSounds.DODO_DEATH, SoundTypeBuilder.of(ModSounds.DODO_DEATH).subtitle("sounds.dustysouls.dodo_death")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "dodo_death"))));


        exporter.add(ModSounds.BOULDER_HURT, SoundTypeBuilder.of(ModSounds.BOULDER_HURT).subtitle("sounds.dustysouls.boulder_hurt")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "boulder_hurt"))));

        exporter.add(ModSounds.BOULDER_DEATH, SoundTypeBuilder.of(ModSounds.BOULDER_DEATH).subtitle("sounds.dustysouls.dodo_death")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "boulder_death"))));


        exporter.add(ModSounds.FLEAGLE_IDLE, SoundTypeBuilder.of(ModSounds.FLEAGLE_IDLE).subtitle("sounds.dustysouls.fleagle_idle")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "fleagle_idle"))));

        exporter.add(ModSounds.FLEAGLE_HURT, SoundTypeBuilder.of(ModSounds.FLEAGLE_HURT).subtitle("sounds.dustysouls.fleagle_hurt")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "fleagle_hurt"))));

        exporter.add(ModSounds.FLEAGLE_DEATH, SoundTypeBuilder.of(ModSounds.FLEAGLE_DEATH).subtitle("sounds.dustysouls.fleagle_death")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "fleagle_death"))));


        exporter.add(ModSounds.LILCHICK_IDLE, SoundTypeBuilder.of(ModSounds.LILCHICK_IDLE).subtitle("sounds.dustysouls.lilchick_idle")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "lilchick_idle1")))
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "lilchick_idle2")))
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "lilchick_idle3"))));

        exporter.add(ModSounds.LILCHICK_HURT, SoundTypeBuilder.of(ModSounds.LILCHICK_HURT).subtitle("sounds.dustysouls.lilchick_hurt")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "lilchick_hurt"))));

        exporter.add(ModSounds.LILCHICK_DEATH, SoundTypeBuilder.of(ModSounds.LILCHICK_DEATH).subtitle("sounds.dustysouls.lilchick_death")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "lilchick_death"))));


        exporter.add(ModSounds.TREKKER_IDLE, SoundTypeBuilder.of(ModSounds.TREKKER_IDLE).subtitle("sounds.dustysouls.trekker_idle")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "trekker_idle"))));

        exporter.add(ModSounds.SCRATCH_HURT, SoundTypeBuilder.of(ModSounds.SCRATCH_HURT).subtitle("sounds.dustysouls.scratch_hurt")
                .sound(SoundTypeBuilder.RegistrationBuilder.ofFile(Identifier.fromNamespaceAndPath(DustySouls.MOD_ID, "scratch_hurt"))));
    }



    @Override
    public String getName() {
        return "DustySouls Sounds";
    }
}