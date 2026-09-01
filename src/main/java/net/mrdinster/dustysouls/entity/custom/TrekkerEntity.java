package net.mrdinster.dustysouls.entity.custom;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.equine.AbstractHorse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.mrdinster.dustysouls.entity.ModEntities;
import net.mrdinster.dustysouls.item.ModItems;
import net.mrdinster.dustysouls.sound.ModSounds;
import org.jspecify.annotations.Nullable;

public class TrekkerEntity extends AbstractHorse implements GeoEntity {
    private final RawAnimation WALK = RawAnimation.begin().thenLoop("trekker.walk");
    private final RawAnimation IDLE = RawAnimation.begin().thenLoop("trekker.idle");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public TrekkerEntity(EntityType<? extends AbstractHorse> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return AbstractHorse.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.JUMP_STRENGTH, 0.5D)
                .add(Attributes.STEP_HEIGHT, 1.5D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStrollGoal(this, 0.5D));
        this.goalSelector.addGoal(0, new PanicGoal(this, 0.5D));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new BreedGoal(this, 0.4D));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 4));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 0.4D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 0.4D));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("controller", 12, this::predicate));
    }



    private PlayState predicate(AnimationTest<TrekkerEntity> event) {
        if (event.isMoving()) {
            event.setAnimation(WALK);

            double animationSpeed = 1.0D;

            if (this.getFirstPassenger() instanceof Player) {
                double movementSpeed = this.getDeltaMovement().horizontalDistance();
                animationSpeed = (movementSpeed * 4.0D) * 1.5D;

                animationSpeed = Math.max(0.6D, Math.min(animationSpeed, 3.5D));
            }

            event.controller().setAnimationSpeed(animationSpeed);
            return PlayState.CONTINUE;
        }

        event.controller().setAnimationSpeed(1.0D);
        event.setAnimation(IDLE);
        return PlayState.CONTINUE;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.TREKKER_IDLE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.SNIFFER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(final DamageSource source) {
        return SoundEvents.SNIFFER_HURT;
    }


    @Override
    public boolean isSaddled() {
        return true;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return itemStack.is(ModItems.KEAR);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob partner) {
        return ModEntities.TREKKER.create(level, EntitySpawnReason.BREEDING);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
