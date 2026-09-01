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
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueOutput;
import net.mrdinster.dustysouls.event.RemagerRaidManager;
import net.mrdinster.dustysouls.item.ModItems;
import net.mrdinster.dustysouls.menu.ModMenuTypes;
import net.mrdinster.dustysouls.menu.custom.RemagerMenu;
import net.mrdinster.dustysouls.particle.ModParticleTypes;

import java.util.EnumSet;

public class RemagerEntity extends PathfinderMob implements GeoEntity {

    private int sleepParticleCooldown = 0;
    private Player tradingPlayer = null;

    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(RemagerEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> OVERWINTERING = SynchedEntityData.defineId(RemagerEntity.class, EntityDataSerializers.BOOLEAN);

    private final RawAnimation WALK = RawAnimation.begin().thenLoop("remager.walk");
    private final RawAnimation IDLE = RawAnimation.begin().thenLoop("remager.idle");
    private final RawAnimation SLEEP = RawAnimation.begin().thenLoop("remager.sleep");


    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);


    public RemagerEntity(EntityType<? extends PathfinderMob> type, Level level) {
        super(type, level);
    }


    public static AttributeSupplier.Builder createAttributes() {
        return PathfinderMob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new OverwinteringGoal(this));
        this.goalSelector.addGoal(0, new SleepGoal(this));
        this.goalSelector.addGoal(1, new RandomStrollGoal(this, 0.7D));
        this.goalSelector.addGoal(2, new PanicGoal(this, 0.9D));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 4));
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {

        if (RemagerRaidManager.isRaidActiveInArea(this.level(), this.blockPosition())) {
            if (!this.level().isClientSide()) {
                player.sendOverlayMessage(Component.literal("The air is heavy... The Remager refuses to barter!"));
            }
            return InteractionResult.SUCCESS;
        }


        if (this.isOverwintering()) {
            ItemStack itemstack = player.getItemInHand(hand);

            if (itemstack.is(ModItems.KEAR)) {
                if (!this.level().isClientSide()) {
                    if (!player.getAbilities().instabuild) {
                        itemstack.shrink(1);
                    }

                    this.setOverwintering(false);

                    if (this.level() instanceof ServerLevel serverLevel) {
                        serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER,
                                this.getX(), this.getEyeY(), this.getZ(), 15, 0.3,
                                0.3, 0.3, 0.05);
                        serverLevel.playSound(null, this.getX(), this.getY(), this.getZ(),
                                SoundEvents.ZOMBIE_VILLAGER_CURE, SoundSource.AMBIENT, 1.5F, 1.1F);

                    }

                    player.sendOverlayMessage(Component.literal("The Remager has awoken!"));
                }

                return InteractionResult.SUCCESS;
            } else {
                if (!this.level().isClientSide()) {
                    if (hand == InteractionHand.MAIN_HAND) {
                        player.sendOverlayMessage(Component.literal("It seems to be in a deep slumber... Maybe try feeding it some Kear"));
                    }
                }
                return InteractionResult.SUCCESS;
            }
        }


        if (!this.level().isClientSide() && !this.isSleepingTime()) {
            this.tradingPlayer = player;
            this.getNavigation().stop();


            player.openMenu(new SimpleMenuProvider((containerId, playerInventory, p) -> {
                return new RemagerMenu(containerId, playerInventory, ModMenuTypes.REMAGER_MENU_TYPE);
            }, Component.literal("")));

            return InteractionResult.SUCCESS;
        }

        if(!this.level().isClientSide() && this.isSleepingTime())
        {
            player.sendOverlayMessage(Component.literal("Now is not the time to trade"));
            return InteractionResult.SUCCESS;
        }


        return InteractionResult.SUCCESS;
    }



    @Override
    public void tick() {
        super.tick();
        if (this.tradingPlayer != null) {
            this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
        }

        if (!this.level().isClientSide()) {
            if (this.tradingPlayer != null) {
                if (!this.tradingPlayer.isAlive() || this.tradingPlayer.containerMenu == this.tradingPlayer.inventoryMenu || this.distanceToSqr(this.tradingPlayer) > 64.0) {
                    this.tradingPlayer = null;
                } else {
                    this.getNavigation().stop();
                    this.getLookControl().setLookAt(this.tradingPlayer, 30.0F, 30.0F);
                    this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
                }
            }
        }



        if (this.level().isClientSide()) {

            if (this.sleepParticleCooldown > 0) {
                this.sleepParticleCooldown--;
            }

            if (this.isSleepingTime() && this.sleepParticleCooldown == 0) {

                float angle = this.yBodyRot * (float) (Math.PI / 180.0);
                double rightOffsetVector = 0.35;

                double particleX = this.getX() - (double) (Mth.sin(angle - 1.57079f) * rightOffsetVector);
                double particleY = this.getEyeY() + 0.1;
                double particleZ = this.getZ() + (double) (Mth.cos(angle - 1.57079f) * rightOffsetVector);

                double velocityX = -(double) (Mth.sin(angle - 1.57079f) * 0.02);
                double velocityY = 0.06;
                double velocityZ = 0.0;

                this.level().addParticle(
                        ModParticleTypes.SLEEP_PARTICLE,
                        particleX, particleY, particleZ,
                        velocityX, velocityY, velocityZ
                );

                this.sleepParticleCooldown = 50;
            }
        }
    }


    @Override
    public void die(DamageSource damageSource) {
        super.die(damageSource);

        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, this.getX(),
                    this.getY() + 1.0,
                    this.getZ(),
                    2,
                    0.2, 0.2, 0.2,
                    0.02
            );
        }



        if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.SCULK_SOUL, this.getX(), this.getY() + 1.0, this.getZ(), 2, 0.2, 0.2, 0.2, 0.02);

            net.minecraft.world.phys.AABB area = this.getBoundingBox().inflate(16.0D);
            java.util.List<RemagerEntity> cercanos = serverLevel.getEntitiesOfClass(RemagerEntity.class, area);

            boolean raidStart = false;
            for (RemagerEntity remager : cercanos) {
                if (remager != this && !remager.isOverwintering() && !remager.isSleepingTime()) {

                    remager.triggerAnim("controller", "remager.scream");

                    serverLevel.playSound(null, remager.getX(), remager.getY(), remager.getZ(),
                            SoundEvents.RAID_HORN,
                            net.minecraft.sounds.SoundSource.HOSTILE, 1.0F, 1.0F);

                    raidStart = true;
                }
            }


            if (raidStart) {
                RemagerRaidManager.startRaid(serverLevel, this.blockPosition());
            }
        }


    }

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float amount) {
        boolean tookDamage = super.hurtServer(serverLevel, source, amount);

        float particleChance = 0.40f;
        boolean isPlayerAttack = source.getEntity() instanceof Player;

        if (tookDamage && isPlayerAttack && !this.isSleepingTime() && !this.isOverwintering() && this.random.nextFloat() < particleChance) {
            serverLevel.sendParticles(
                    ParticleTypes.ANGRY_VILLAGER,
                    this.getX(),
                    this.getEyeY(),
                    this.getZ(),
                    2,
                    0.3, 0.1, 0.3,
                    0.0
            );
        }
        return tookDamage;
    }


    @Override
    protected int getBaseExperienceReward(ServerLevel level) {
        return this.random.nextInt(2);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(SLEEPING, false);
        builder.define(OVERWINTERING, false);
    }


    public boolean isOverwintering() {
        return this.entityData.get(OVERWINTERING);
    }

    public void setOverwintering(boolean overwintering) {
        this.entityData.set(OVERWINTERING, overwintering);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("overwintering", this.isOverwintering());
    }
    @Override
    protected void readAdditionalSaveData(net.minecraft.world.level.storage.ValueInput input) {
        super.readAdditionalSaveData(input);

        boolean overwinter = input.getBooleanOr("overwintering", false);
        this.setOverwintering(overwinter);

        if (overwinter) {
            this.setSleepingTime(false);
            if (this.getNavigation() != null) {
                this.getNavigation().stop();
            }
            this.setDeltaMovement(0, this.getDeltaMovement().y, 0);
        }
    }


    public boolean isSleepingTime() {
        return this.entityData.get(SLEEPING);
    }

    public void setSleepingTime(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level().isClientSide() && !this.isOverwintering()) {
            boolean isNight = this.level().isDarkOutside();
            if (isNight != this.isSleepingTime()) {
                this.setSleepingTime(isNight);
            }
        }
    }


    private static class SleepGoal extends Goal {
        private final RemagerEntity remager;

        public SleepGoal(RemagerEntity remager) {
            this.remager = remager;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }


        @Override
        public boolean canUse() {
            return this.remager.isSleepingTime();
        }

        @Override
        public void start() {
            this.remager.getNavigation().stop();
            this.remager.setDeltaMovement(0, this.remager.getDeltaMovement().y, 0);
        }
    }


    private static class OverwinteringGoal extends Goal {
        private final RemagerEntity remager;public OverwinteringGoal(RemagerEntity remager) {
            this.remager = remager;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK, Goal.Flag.JUMP));
        }
        @Override
        public boolean canUse() {
            return this.remager.isOverwintering();
        }
        @Override
        public void start() {
            this.remager.getNavigation().stop();
            this.remager.setDeltaMovement(0, this.remager.getDeltaMovement().y, 0);
        }
    }


    @Override
    public boolean removeWhenFarAway(double distanceToClosestPlayer) {
        return false;
    }


    @Override
    protected SoundEvent getAmbientSound() {
        if (!!this.isSleepingTime() && !this.isOverwintering()) {
            return SoundEvents.VILLAGER_AMBIENT;
        }
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.VILLAGER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.VILLAGER_DEATH;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("controller", 6, this::predicate));
    }

    private PlayState predicate(AnimationTest<RemagerEntity> event) {
        if (this.isOverwintering()) {
            return event.setAndContinue(SLEEP);
        }
        if (this.isSleepingTime()) {
            return event.setAndContinue(SLEEP);
        }
        if (event.isMoving()) {
            event.setAnimation(WALK);
            return PlayState.CONTINUE;
        }
        else {
            event.setAnimation(IDLE);
        }
        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
}
