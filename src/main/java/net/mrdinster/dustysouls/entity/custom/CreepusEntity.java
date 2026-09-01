package net.mrdinster.dustysouls.entity.custom;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.util.DefaultRandomPos;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;

public class CreepusEntity extends Monster implements GeoEntity {
    private final RawAnimation WALK = RawAnimation.begin().thenLoop("creepus.walk");
    private final RawAnimation IDLE = RawAnimation.begin().thenLoop("creepus.idle");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int gasCooldownTicks = 0;

    public CreepusEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 5;
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 32)
                .add(Attributes.ATTACK_DAMAGE, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3);

    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new CreepusGasAttackGoal(this, 1.05D, 3.0F)); // IA Personalizada (Velocidad de persecución, Distancia de ataque)
        this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0D)); // Caminar si no hay enemigos
        this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
        this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }


    @Override
    public void aiStep() {
        super.aiStep();
        // Reducir el contador del cooldown en cada tick del servidor
        if (!this.level().isClientSide() && this.gasCooldownTicks > 0) {
            this.gasCooldownTicks--;
        }
    }

    public int getGasCooldownTicks() {
        return this.gasCooldownTicks;
    }

    public void setGasCooldownTicks(int ticks) {
        this.gasCooldownTicks = ticks;
    }


    private static class CreepusGasAttackGoal extends Goal {
        private final CreepusEntity creepus;
        private final double speedModifier;
        private final float attackRadiusSqr;
        private int fleeTicks = 0;
        private Vec3 fleePos = null;

        public CreepusGasAttackGoal(CreepusEntity creepus, double speedModifier, float attackRadius) {
            this.creepus = creepus;
            this.speedModifier = speedModifier;
            this.attackRadiusSqr = attackRadius * attackRadius;
            this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        @Override
        public boolean canUse() {
            LivingEntity target = this.creepus.getTarget();
            return target != null && target.isAlive() && (this.creepus.getGasCooldownTicks() == 0 || this.fleeTicks > 0);
        }

        @Override
        public void start() {
            this.creepus.getNavigation().moveTo(this.creepus.getTarget(), this.speedModifier);
        }

        @Override
        public void stop() {
            this.fleeTicks = 0;
            this.fleePos = null;
        }

        @Override
        public void tick() {
            LivingEntity target = this.creepus.getTarget();
            if (target == null) return;

            if (this.fleeTicks > 0) {
                this.fleeTicks--;
                if (this.fleePos == null || this.creepus.getNavigation().isDone()) {
                    this.fleePos = DefaultRandomPos.getPosAway(this.creepus, 16, 7, target.position());
                    if (this.fleePos != null) {
                        this.creepus.getNavigation().moveTo(this.fleePos.x, this.fleePos.y, this.fleePos.z, this.speedModifier * 1.1);
                    }
                }
                return;
            }

            this.creepus.getLookControl().setLookAt(target, 30.0F, 30.0F);
            double distanceSqr = this.creepus.distanceToSqr(target);

            if (distanceSqr <= this.attackRadiusSqr) {
                this.triggerGasAttack(target);
            } else {
                this.creepus.getNavigation().moveTo(target, this.speedModifier);
            }
        }

        private void triggerGasAttack(LivingEntity target) {
            Level world = this.creepus.level();

            if (!world.isClientSide()) {
                target.hurtServer((ServerLevel) world, world.damageSources().mobAttack(this.creepus), 2.0F);

                world.playSound(null, this.creepus.getX(), this.creepus.getY(), this.creepus.getZ(),
                        SoundEvents.BREWING_STAND_BREW, SoundSource.HOSTILE, 1.5F, 0.5F); // Tono bajo (0.5F) para sonar más denso


                target.addEffect(new MobEffectInstance(MobEffects.POISON, 100, 2), this.creepus);

                ColorParticleOption poisonParticles = ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, 0xFF4D9E24);

                ServerLevel serverWorld = (ServerLevel) world;
                for (int i = 0; i < 40; i++) {
                    serverWorld.sendParticles(poisonParticles,
                            this.creepus.getX() + (world.getRandom().nextDouble() - 0.5) * 2,
                            this.creepus.getY() + 0.5,
                            this.creepus.getZ() + (world.getRandom().nextDouble() - 0.5) * 2,
                            1, 0.2, 0.2, 0.2, 0.0);
                }

                for (int i = 0; i < 30; i++) {
                    serverWorld.sendParticles(ParticleTypes.SNEEZE,
                            this.creepus.getX() + (world.getRandom().nextDouble() - 0.5) * 2.5,
                            this.creepus.getY() + 0.5,
                            this.creepus.getZ() + (world.getRandom().nextDouble() - 0.5) * 2.5,
                            1,
                            (world.getRandom().nextDouble() - 0.5) * 0.1,
                            0.05,
                            (world.getRandom().nextDouble() - 0.5) * 0.1,
                            0.1);
                }


                this.creepus.setGasCooldownTicks(160);
                this.fleeTicks = 60;
                this.fleePos = DefaultRandomPos.getPosAway(this.creepus, 16, 7, target.position());
                if (this.fleePos != null) {
                    this.creepus.getNavigation().moveTo(this.fleePos.x, this.fleePos.y, this.fleePos.z, this.speedModifier * 1.1);
                }
            }
        }
    }


    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.CREEPER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(final DamageSource source) {
        return SoundEvents.CREEPER_HURT;
    }




    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("controller", 6, this::predicate));
    }



    private PlayState predicate(AnimationTest<CreepusEntity> event) {
        if (event.isMoving()) {
            event.setAnimation(WALK);
        } else {
            event.setAnimation(IDLE);
        }

        return PlayState.CONTINUE;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
