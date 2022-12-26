/*
 * -------------------------------------------------------------------
 * Nox
 * Copyright (c) 2022 SciRave
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * -------------------------------------------------------------------
 */

package net.scirave.nox.mixin;

import net.minecraft.entity.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(value = SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntityMixin {

    @Shadow
    public abstract int getSize();

    @Shadow public abstract void setSize(int size, boolean heal);

    @Inject(method = "canSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/ChunkRandom;getSlimeRandom(IIJJ)Lnet/minecraft/util/math/random/Random;"), cancellable = true)
    private static void nox$slimeSpawnNaturally(EntityType<SlimeEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (NoxConfig.slimeNaturalSpawn) {
            if (world.getLightLevel(pos) <= 7) {
                cir.setReturnValue(SlimeEntity.canMobSpawn(type, world, spawnReason, pos, random));
            }
        }
    }
    
    @Inject(method = "initialize", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/MobEntity;initialize(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/world/LocalDifficulty;Lnet/minecraft/entity/SpawnReason;Lnet/minecraft/entity/EntityData;Lnet/minecraft/nbt/NbtCompound;)Lnet/minecraft/entity/EntityData;"))
    public void nox$betterSlimeSpawn(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        int size = 3;
        float random = this.getRandom().nextFloat() * 10 + difficulty.getClampedLocalDifficulty();

        if (random < 5) {
            size = 2;
        }
        if (random < 2) {
            size = 1;
        }
        if (random > 9.5) {
            size = 4;
        }

        this.setSize(size, true);
    } 

    @Inject(method = "remove", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/SlimeEntity;setSize(IZ)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void nox$slimeReapplyAttributes(Entity.RemovalReason reason, CallbackInfo ci, int i, Text text, boolean bl, float f, int j, int k, int l, float g, float h, SlimeEntity slimeEntity) {
        if (this.world instanceof ServerWorld serverWorld) {
            slimeEntity.initialize(serverWorld, serverWorld.getLocalDifficulty(this.getBlockPos()), SpawnReason.REINFORCEMENT, null, null);
        }
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/SlimeEntity;applyDamageEffects(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V"))
    public void nox$slimeOnAttack(LivingEntity victim, CallbackInfo ci) {
        //Overridden
    }

    @Inject(method = "getTicksUntilNextJump", at = @At("HEAD"), cancellable = true)
    private void nox$makeSlimesJumpConstantly(CallbackInfoReturnable<Integer> cir) {
        if (NoxConfig.slimesJumpConstantly)
            cir.setReturnValue(4);
    }

    @Override
    public void nox$modifyAttributes(EntityType<?> entityType, World world, CallbackInfo ci) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addTemporaryModifier(new EntityAttributeModifier("Nox: Slime bonus", NoxConfig.slimeHealthMultiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        this.setHealth(this.getMaxHealth());
        this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).addTemporaryModifier(new EntityAttributeModifier("Nox: Slime bonus", NoxConfig.slimeViewDistanceMultiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).addTemporaryModifier(new EntityAttributeModifier("Nox: Slime bonus", NoxConfig.slimeKnockbackMultiplier, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addTemporaryModifier(new EntityAttributeModifier("Nox: Slime bonus", NoxConfig.slimeSpeedMultiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Override
    public void nox$shouldTakeDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        super.nox$shouldTakeDamage(source, amount, cir);
        if ((source.getName().equals("fall") && !NoxConfig.slimesTakeFallDamage) || (source.isProjectile() && !source.bypassesArmor() && !NoxConfig.slimesTakeProjectileDamage)) {
            cir.setReturnValue(false);
        }
    }

    @Override
    public void nox$onDeath(DamageSource source, CallbackInfo ci) {
        if (this.getWorld() instanceof ServerWorld) {
            this.nox$slimeOnDeath();
        }
    }

    public void nox$slimeOnDeath() {
        if (NoxConfig.slimePoisonCloudDurationDivisor > 0) {
            AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
            cloud.setRadius(NoxConfig.slimePoisonCloudRadiusMultiplier * this.getSize());
            cloud.setRadiusOnUse(-0.5F);
            cloud.setWaitTime(10 + 15 * (this.getSize() - 1));
            cloud.setDuration(cloud.getDuration() * this.getSize() / NoxConfig.slimePoisonCloudDurationDivisor);
            cloud.setRadiusGrowth(-cloud.getRadius() / (float) cloud.getDuration());
            cloud.addEffect(new StatusEffectInstance(StatusEffects.POISON, NoxConfig.slimePoisonDuration, NoxConfig.slimePoisonAmplifier - 1));
            this.world.spawnEntity(cloud);
        }
    }

    @Override
    public void nox$onStatusEffect(StatusEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
        if (effect.getEffectType() == StatusEffects.POISON) {
            cir.setReturnValue(false);
        }
    }

}
