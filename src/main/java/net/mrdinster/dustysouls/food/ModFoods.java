package net.mrdinster.dustysouls.food;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.Consumables;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class ModFoods {
    public static final FoodProperties WEBBIES = new FoodProperties.Builder().nutrition(3)
            .saturationModifier(0.4f).alwaysEdible().build();
    public static final Consumable WEBBIES_CONSUMABLE = Consumables.defaultFood()
            .consumeSeconds(0.5f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED, 300, 2))).build();

    public static final FoodProperties KEAR = new FoodProperties.Builder().nutrition(4)
            .saturationModifier(0.5f).build();

    public static final FoodProperties NANGU = new FoodProperties.Builder().nutrition(4)
            .saturationModifier(0.45f).build();
    public static final Consumable NANGU_CONSUMABLE = Consumables.defaultFood()
            .consumeSeconds(1f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HASTE, 500, 0))).build();


    public static final FoodProperties FRUIT_SALAD = new FoodProperties.Builder().nutrition(4)
            .saturationModifier(0.45f).build();
    public static final Consumable FRUIT_SALAD_CONSUMABLE = Consumables.defaultFood()
            .consumeSeconds(1f)
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.HASTE, 200, 0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED, 200, 1)))
            .build();


    public static final FoodProperties CRACKED_EGG = new FoodProperties.Builder().nutrition(6)
            .saturationModifier(0.6f).build();
    public static final Consumable CRACKED_EGG_CONSUMABLE = Consumables.defaultFood()
            .consumeSeconds(1f).onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SATURATION, 200, 1))).build();


}
