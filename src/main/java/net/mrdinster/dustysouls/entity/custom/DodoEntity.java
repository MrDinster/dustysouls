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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.mrdinster.dustysouls.block.ModBlocks;
import net.mrdinster.dustysouls.entity.ModEntities;
import net.mrdinster.dustysouls.item.ModItems;
import net.mrdinster.dustysouls.particle.ModParticleTypes;
import net.mrdinster.dustysouls.sound.ModSounds;
import org.jspecify.annotations.Nullable;

public class DodoEntity extends Animal implements GeoEntity {

    private final RawAnimation WALK = RawAnimation.begin().thenLoop("dodo.walk");
    private final RawAnimation IDLE = RawAnimation.begin().thenLoop("dodo.idle");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public int eggTime;

    public DodoEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);

        this.resetEggTime();
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 14)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new RandomStrollGoal(this, 1.2D));
        this.goalSelector.addGoal(0, new PanicGoal(this, (double)1.0D));
        this.goalSelector.addGoal(1, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(1, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(1, new LookAtPlayerGoal(this, Player.class, 4));
        this.goalSelector.addGoal(2, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(3, new FollowParentGoal(this, 1.0D));
        this.goalSelector.addGoal(4, new WaterAvoidingRandomStrollGoal(this, 1.2D));
    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
        boolean damaged = super.hurtServer(serverLevel, source, amount);

        if (damaged) {
            serverLevel.sendParticles(ModParticleTypes.DODO_FEATHER_PARTICLE, this.getX(), this.getY() + 0.4D, this.getZ(), 8, 0.2D, 0.3D, 0.2D, 0.1D);
        }
        return damaged;
    }


    private void resetEggTime() {
        this.eggTime = this.random.nextInt(3600) + 3600;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide() && this.isAlive() && !this.isBaby()) {
            if (--this.eggTime <= 0) {

                this.playSound(SoundEvents.CHICKEN_EGG, 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);

                this.spawnAtLocation((ServerLevel) level(), ModBlocks.DODO_EGG.asItem());

                this.gameEvent(GameEvent.ENTITY_PLACE);

                this.resetEggTime();
            }
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("EggLayTime", this.eggTime);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.getInt("EggLayTime").ifPresent(time -> this.eggTime = time);
    }



    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("controller", 6, this::predicate));
    }

    //ANIMATIONS
    private PlayState predicate(AnimationTest<DodoEntity> event) {
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
        return ModSounds.DODO_IDLE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DODO_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(final DamageSource source) {
        return ModSounds.DODO_HURT;
    }


    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal partner) {
        super.spawnChildFromBreeding(level, partner);
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
        return ModEntities.DODO.create(level, EntitySpawnReason.BREEDING);
    }


    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

}
