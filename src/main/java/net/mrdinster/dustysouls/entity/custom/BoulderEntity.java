package net.mrdinster.dustysouls.entity.custom;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.mrdinster.dustysouls.sound.ModSounds;

import java.util.List;

public class BoulderEntity extends Monster implements GeoEntity {
    private final RawAnimation WALK = RawAnimation.begin().thenLoop("boulder.walk");
    private final RawAnimation IDLE = RawAnimation.begin().thenLoop("boulder.idle");

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private int specialAbilityCooldown = 100;

    public BoulderEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
        this.xpReward = 10;
    }


    public static AttributeSupplier.Builder createAttributes() {
        return Monster.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 30)
                .add(Attributes.ATTACK_DAMAGE, 0.3D)
                .add(Attributes.FOLLOW_RANGE, 24.0)
                .add(Attributes.MOVEMENT_SPEED, 0.3);

    }

    @Override
    protected void registerGoals() {
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(3, new RandomLookAroundGoal(this));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, Player.class, 4));
    }

    @Override
    public void tick() {
        emitGroundParticles(1 + this.getRandom().nextInt(1));

        if (!this.level().isClientSide()) {
            handleSpecialAbility();
        }

        super.tick();
    }

    private void handleSpecialAbility() {
        if (this.specialAbilityCooldown > 0) {
            this.specialAbilityCooldown--;
        } else {
            AABB area = this.getBoundingBox().inflate(4.0D, 4.0D, 4.0D);

       
            boolean playerNearby = !this.level().getEntitiesOfClass(Player.class, area,
                    player -> !player.isSpectator() && !player.isCreative()).isEmpty();

            if (playerNearby) {
                List<LivingEntity> targets = this.level().getEntitiesOfClass(LivingEntity.class, area);

                for (LivingEntity entity : targets) {
                    if (entity != this) {

    
                        if (entity instanceof Player player && (player.isCreative() || player.isSpectator())) {
                            continue; 
                        }

                        entity.hurtServer((ServerLevel) level(), this.damageSources().mobAttack(this), 7.0F);
                        entity.push(0.0D, 0.5D, 0.0D);
                        entity.hurtMarked = true;
                    }
                }

                emitAscendingParticles();
                this.specialAbilityCooldown = 140;
            }
        }
    }


    public void emitAscendingParticles() {
        BlockState ground = !this.getInBlockState().isAir() ? this.getInBlockState() : this.getBlockStateOn();

        if (ground.getRenderShape() != RenderShape.INVISIBLE && this.level() instanceof ServerLevel serverLevel) {
            Vec3 entityPos = this.position();
            BlockParticleOption particleData = new BlockParticleOption(ParticleTypes.BLOCK, ground);

         for (int i = 0; i < 90; i++) {
                double offsetX = (this.random.nextDouble() - 0.5D) * 8.0D; // Rango de -4.0 a 4.0 bloques
                double offsetZ = (this.random.nextDouble() - 0.5D) * 8.0D; // Rango de -4.0 a 4.0 bloques
                double offsetY = this.random.nextDouble() * 1.5D;          // Altura inicial variada para romper la línea plana
                serverLevel.sendParticles(
                        particleData,
                        entityPos.x + offsetX,
                        entityPos.y + offsetY,
                        entityPos.z + offsetZ,
                        1,
                        0.0D,
                        0.5D,
                        0.0D,
                        1.0D
                );
            }
        }
    }

    public void emitGroundParticles(final int amount) {
        if (!this.isPassenger()) {
            Vec3 boundingBoxCenter = this.getBoundingBox().getCenter();
            Vec3 position = new Vec3(boundingBoxCenter.x, this.position().y, boundingBoxCenter.z);
            BlockState ground = !this.getInBlockState().isAir() ? this.getInBlockState() : this.getBlockStateOn();
            if (ground.getRenderShape() != RenderShape.INVISIBLE) {
                for (int i = 0; i < amount; i++) {
                    this.level().addParticle(new BlockParticleOption(ParticleTypes.BLOCK, ground), position.x, position.y, position.z, 0.0, 0.0, 0.0);
                }
            }
        }
    }


    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.BREEZE_IDLE_GROUND;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.BOULDER_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.BOULDER_DEATH;
    }


    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("controller", 6, this::predicate));
        controllers.add(new AnimationController<>("idle_controller", 6, this::idlePredicate));
    }

    private PlayState idlePredicate(AnimationTest<BoulderEntity> event) {
        event.setAnimation(IDLE);
        return PlayState.CONTINUE;
    }

    private PlayState predicate(AnimationTest<BoulderEntity> event) {
        if (event.isMoving()) {
            event.setAnimation(WALK);
            return PlayState.CONTINUE;
        }

        return PlayState.STOP;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
