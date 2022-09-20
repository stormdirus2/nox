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
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
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

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntityMixin {

    @Inject(method = "canSpawn", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/ChunkRandom;getSlimeRandom(IIJJ)Lnet/minecraft/util/math/random/Random;"), cancellable = true)
    private static void nox$slimeSpawnNaturally(EntityType<SlimeEntity> type, WorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (NoxConfig.slimeNaturalSpawn) {
            if (world.getLightLevel(pos) <= 7) {
                cir.setReturnValue(SlimeEntity.canMobSpawn(type, world, spawnReason, pos, random));
            }
        }
    }

    @Shadow
    public abstract int getSize();

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

    @Override
    public void nox$modifyAttributes(EntityType<?> entityType, World world, CallbackInfo ci) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addTemporaryModifier(new EntityAttributeModifier("Nox: Slime bonus", 1.5, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        this.setHealth(this.getMaxHealth());
        this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).addTemporaryModifier(new EntityAttributeModifier("Nox: Slime bonus", 1.5, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).addTemporaryModifier(new EntityAttributeModifier("Nox: Slime bonus", 0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addTemporaryModifier(new EntityAttributeModifier("Nox: Slime bonus", 0.3, EntityAttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Override
    public void nox$shouldTakeDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        super.nox$shouldTakeDamage(source, amount, cir);
        if (source.getName().equals("fall") || (source.isProjectile() && !source.bypassesArmor())) {
            cir.setReturnValue(false);
        }
    }

    @Override
    public void nox$onDeath(DamageSource source, CallbackInfo ci) {
        this.nox$slimeOnDeath();
    }

    public void nox$slimeOnDeath() {
        AreaEffectCloudEntity cloud = new AreaEffectCloudEntity(this.world, this.getX(), this.getY(), this.getZ());
        cloud.setRadius(2.5F * this.getSize());
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(10 + 15 * (this.getSize() - 1));
        cloud.setDuration(cloud.getDuration() * this.getSize() / 4);
        cloud.setRadiusGrowth(-cloud.getRadius() / (float) cloud.getDuration());
        cloud.addEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 1));
        this.world.spawnEntity(cloud);
    }

    @Override
    public void nox$onStatusEffect(StatusEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
        if (effect.getEffectType() == StatusEffects.POISON) {
            cir.setReturnValue(false);
        }
    }

}
