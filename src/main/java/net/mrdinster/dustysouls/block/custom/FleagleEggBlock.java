package net.mrdinster.dustysouls.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.mrdinster.dustysouls.entity.ModEntities;

public class FleagleEggBlock extends Block {
    public static final IntegerProperty HATCH = BlockStateProperties.HATCH;
    public static final VoxelShape SHAPE = Block.box(1, 0, 1, 15, 16, 15);

    public FleagleEggBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(HATCH);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int currentHatch = state.getValue(HATCH);
        boolean isOnBoostBlock = level.getBlockState(pos.below()).is(Blocks.MAGMA_BLOCK);

        int chance = isOnBoostBlock ? 2 : 5;

        if (random.nextInt(chance) == 0) {
            level.playSound(null, pos, SoundEvents.SNIFFER_EGG_CRACK, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);

            if (currentHatch < 2) {

                level.setBlock(pos, state.setValue(HATCH, currentHatch + 1), 2);
                level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
            } else {
                level.destroyBlock(pos, false);

                Mob fleagle = ModEntities.FLEAGLE.create(level, EntitySpawnReason.BREEDING);
                if (fleagle != null) {
                    fleagle.setBaby(true);

                    fleagle.absSnapTo((double)pos.getX() + 0.5, (double)pos.getY(), (double)pos.getZ() + 0.5, random.nextFloat() * 360.0F, 0.0F);
                    level.addFreshEntity(fleagle);
                }

                // Efectos de sonido finales
                level.playSound(null, pos, SoundEvents.SNIFFER_EGG_HATCH, SoundSource.BLOCKS, 0.7F, 0.9F + random.nextFloat() * 0.2F);
                level.gameEvent(GameEvent.ENTITY_PLACE, pos, GameEvent.Context.of(fleagle));
            }
        }
    }
}
