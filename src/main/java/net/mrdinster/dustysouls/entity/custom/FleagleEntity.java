package net.mrdinster.dustysouls.entity.custom;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.mrdinster.dustysouls.entity.ModEntities;
import net.mrdinster.dustysouls.item.ModItems;
import net.mrdinster.dustysouls.sound.ModSounds;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class FleagleEntity extends Animal implements GeoEntity {
    private final RawAnimation WALK = RawAnimation.begin().thenLoop("fleagle.walk");
    private final RawAnimation IDLE = RawAnimation.begin().thenLoop("fleagle.idle");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    public FleagleEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStrollGoal(this, 0.4D));
        this.goalSelector.addGoal(1, new PanicGoal(this, 0.4D));
        this.goalSelector.addGoal(2, new BreedGoal(this, 0.4D));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 4));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 0.9D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 0.4D));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.isBaby()) {
            return;
        }

        if (!this.level().isClientSide() && this.level() instanceof ServerLevel serverLevel && this.tickCount % 10 == 0) {
            AABB area = this.getBoundingBox().inflate(5.0D);

            List groundItems = serverLevel.getEntitiesOfClass(ItemEntity.class, area);

            ItemEntity goldFound = null;
            ItemEntity tempusFound = null;
            ItemEntity coalFound = null;

            for (Object obj : groundItems) {
                if (!(obj instanceof net.minecraft.world.entity.item.ItemEntity itemEntity)) continue;

                if (!itemEntity.isAlive() || itemEntity.isRemoved()) continue;

                ItemStack stack = itemEntity.getItem();

                if (stack.is(net.minecraft.world.item.Items.GOLD_INGOT)) {
                    goldFound = itemEntity;
                }
                else if (stack.is(ModItems.TEMPUS_STONE)) {
                    tempusFound = itemEntity;
                }
                else if (stack.is(net.minecraft.world.item.Items.COAL) || stack.is(net.minecraft.world.item.Items.CHARCOAL)) {
                    coalFound = itemEntity;
                }

                if (goldFound != null && tempusFound != null && coalFound != null) {
                    break;
                }
            }

            if (goldFound != null && tempusFound != null) {

                double fX = (goldFound.getX() + tempusFound.getX()) / 2.0D;
                double fY = (goldFound.getY() + tempusFound.getY()) / 2.0D;
                double fZ = (goldFound.getZ() + tempusFound.getZ()) / 2.0D;

                serverLevel.playSound(null, fX, fY, fZ,
                        net.minecraft.sounds.SoundEvents.LIGHTNING_BOLT_THUNDER,
                        SoundSource.AMBIENT, 1.0F, 1.2F);

                serverLevel.sendParticles(ParticleTypes.FLAME,
                        fX, fY + 0.2D, fZ, 25, 0.4, 0.4, 0.4, 0.1);

                serverLevel.sendParticles(ParticleTypes.LAVA,
                        fX, fY + 0.2D, fZ, 10, 0.2, 0.2, 0.2, 0.05);

                ItemStack stackOro = goldFound.getItem();
                stackOro.shrink(1);
                if (stackOro.isEmpty()) goldFound.discard();
                else goldFound.setItem(stackOro);

                ItemStack stackTempus = tempusFound.getItem();
                stackTempus.shrink(1);
                if (stackTempus.isEmpty()) tempusFound.discard();
                else tempusFound.setItem(stackTempus);

                ItemEntity teldenIngot = new ItemEntity(
                        serverLevel, fX, fY, fZ, new ItemStack(ModItems.TELDEN_INGOT)
                );
                teldenIngot.setDeltaMovement(0, 0.2, 0);
                serverLevel.addFreshEntity(teldenIngot);

                this.getLookControl().setLookAt(fX, fY, fZ, 30.0F, 30.0F);
            }

            if (coalFound != null) {
                double cX = coalFound.getX();
                double cY = coalFound.getY();
                double cZ = coalFound.getZ();

                serverLevel.playSound(null, cX, cY, cZ,
                        net.minecraft.sounds.SoundEvents.AMETHYST_BLOCK_CHIME,
                        SoundSource.AMBIENT, 1.0F, 1.0F);

                serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE,
                        cX, cY + 0.2D, cZ, 15, 0.3, 0.3, 0.3, 0.05);

                serverLevel.sendParticles(ParticleTypes.CHERRY_LEAVES,
                        cX, cY + 0.2D, cZ, 10, 0.2, 0.2, 0.2, 0.1);

                ItemStack stackCoal = coalFound.getItem();
                stackCoal.shrink(1);
                if (stackCoal.isEmpty()) coalFound.discard();
                else coalFound.setItem(stackCoal);

                ItemEntity diamondEntity = new ItemEntity(
                        serverLevel, cX, cY, cZ, new ItemStack(net.minecraft.world.item.Items.DIAMOND)
                );
                diamondEntity.setDeltaMovement(0, 0.2, 0);
                serverLevel.addFreshEntity(diamondEntity);

                this.getLookControl().setLookAt(cX, cY, cZ, 30.0F, 30.0F);
            }
        }
    }

    @Override
    public float getAgeScale() {
        return this.isBaby() ? 0.25F : 1.0F;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("controller", 6, this::predicate));
    }


    private PlayState predicate(AnimationTest<FleagleEntity> event) {
        if (event.isMoving()) {
            event.setAnimation(WALK);
            return PlayState.CONTINUE;
        }
        if (!event.isMoving()){
            event.setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        if (this.isBaby()) {
            return ModSounds.LILCHICK_IDLE;
        }
        return ModSounds.FLEAGLE_IDLE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        if (this.isBaby()) {
            return ModSounds.LILCHICK_HURT;
        }
        return ModSounds.FLEAGLE_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        if (this.isBaby()) {
            return ModSounds.LILCHICK_DEATH;
        }
        return ModSounds.FLEAGLE_DEATH;
    }



    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModItems.KEAR);
    }

    @Override
    public void finalizeSpawnChildFromBreeding(ServerLevel level, Animal partner, @Nullable AgeableMob offspring) {
        super.finalizeSpawnChildFromBreeding(level, partner, offspring);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return ModEntities.FLEAGLE.create(level, EntitySpawnReason.BREEDING);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
