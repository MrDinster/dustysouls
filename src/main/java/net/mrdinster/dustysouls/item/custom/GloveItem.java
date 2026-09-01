package net.mrdinster.dustysouls.item.custom;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;

public class GloveItem extends Item {

    public GloveItem(Properties properties) {
        super(properties);
    }

    @Override
    public void inventoryTick(ItemStack itemStack, ServerLevel level, Entity owner, @Nullable EquipmentSlot slot) {
        if (owner instanceof Player player) {
            boolean inMainHand = player.getItemBySlot(EquipmentSlot.MAINHAND) == itemStack;
            boolean inOffHand = player.getItemBySlot(EquipmentSlot.OFFHAND) == itemStack;

            if (inMainHand || inOffHand) {
                player.addEffect(new MobEffectInstance(MobEffects.STRENGTH, 60, 1, false, true, true));

                if (!level.isClientSide()) {
                    if (level.getGameTime() % 80 == 0) {
                        if (player instanceof ServerPlayer serverPlayer) {
                            itemStack.hurtAndBreak(1, serverPlayer, slot);
                        }
                    }
                }
            }
        }
        super.inventoryTick(itemStack, level, owner, slot);
    }
}
