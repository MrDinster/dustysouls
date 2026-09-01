package net.mrdinster.dustysouls.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class TempusPickaxeItem extends Item {

    public TempusPickaxeItem(Properties properties) {
        super(properties);
    }
    @Override
    public InteractionResult useOn(UseOnContext context) {
        Player player = context.getPlayer();

        if (player == null) {
            return InteractionResult.PASS;
        }
        Direction clickedFace = context.getClickedFace();
        BlockPos clickedPos = context.getClickedPos();

        BlockPos blockUnderPlayer = player.blockPosition().below();

        if (clickedFace == Direction.UP && clickedPos.equals(blockUnderPlayer)) {
            return InteractionResult.FAIL;
        }


        if (context.getLevel().isClientSide()) {
            Vec3 hitLocation = context.getClickLocation();
             BlockState state = context.getLevel().getBlockState(clickedPos);


            for (int i = 0; i < 50; i++) {
                context.getLevel().addParticle(new
                                BlockParticleOption(ParticleTypes.BLOCK, state),
                        hitLocation.x, hitLocation.y, hitLocation.z,
                        (context.getLevel().getRandom().nextDouble() - 0.5) * 0.2,
                        context.getLevel().getRandom().nextDouble() * 0.2,
                        (context.getLevel().getRandom().nextDouble() - 0.5) * 0.2
                );
            }

            for (int i = 0; i < 2; i++) {
                context.getLevel().addParticle(
                        ParticleTypes.ITEM_SLIME,
                        hitLocation.x, hitLocation.y, hitLocation.z,
                        (context.getLevel().getRandom().nextDouble() - 0.5) * 0.2,
                        context.getLevel().getRandom().nextDouble() * 0.2,
                        (context.getLevel().getRandom().nextDouble() - 0.5) * 0.2
                );
            }

            context.getLevel().playSound(player, clickedPos, SoundEvents.SLIME_HURT, SoundSource.PLAYERS, 1.0F, 0.6F);

            Vec3 currentVelocity = player.getDeltaMovement();
            player.setDeltaMovement(currentVelocity.x, 0.5, currentVelocity.z);
        }
        else {
            if (player instanceof ServerPlayer serverPlayer) {
                context.getItemInHand().hurtAndBreak(1, serverPlayer, EquipmentSlot.MAINHAND);
            }
        }

            player.getCooldowns().addCooldown(this.getDefaultInstance(), 10);


        return InteractionResult.SUCCESS;
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack itemStack) {
        return ItemUseAnimation.SPYGLASS;
    }
}
