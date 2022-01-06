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
import net.minecraft.text.Text;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntityMixin {

    private static void nox$applySlimeAttributes(SlimeEntity slime) {
        slime.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(new EntityAttributeModifier("Nox: Slime bonus", 1.5, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        slime.setHealth(slime.getMaxHealth());
        slime.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).addPersistentModifier(new EntityAttributeModifier("Nox: Slime bonus", 2.5, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        slime.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK).addPersistentModifier(new EntityAttributeModifier("Nox: Slime bonus", 0, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        slime.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addPersistentModifier(new EntityAttributeModifier("Nox: Slime bonus", 0.3, EntityAttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Shadow
    public abstract int getSize();

    @Inject(method = "remove", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/SlimeEntity;setSize(IZ)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void nox$slimeReapplyAttributes(Entity.RemovalReason reason, CallbackInfo ci, int i, Text text, boolean bl, float f, int j, int k, int l, float g, float h, SlimeEntity slimeEntity) {
        nox$applySlimeAttributes(slimeEntity);
        this.nox$hostileAttributes(slimeEntity);
    }

    @Override
    public void nox$modifyAttributes(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        nox$applySlimeAttributes((SlimeEntity) (Object) this);
    }

    @Override
    public void nox$invulnerableCheck(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        super.nox$invulnerableCheck(damageSource, cir);
        if (damageSource.getName().equals("fall") || (damageSource.isProjectile() && !damageSource.bypassesArmor())) {
            cir.setReturnValue(true);
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
        cloud.setWaitTime(7 * this.getSize());
        cloud.setDuration(cloud.getDuration() * this.getSize() / 4);
        cloud.setRadiusGrowth(-cloud.getRadius() / (float) cloud.getDuration());
        cloud.addEffect(new StatusEffectInstance(StatusEffects.POISON, 60, 1));
        this.world.spawnEntity(cloud);
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/SlimeEntity;applyDamageEffects(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V"))
    public void nox$slimeOnAttack(LivingEntity victim, CallbackInfo ci) {
        //Overridden
    }

    @Override
    public void nox$onStatusEffect(StatusEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
        if (effect.getEffectType() == StatusEffects.POISON) {
            cir.setReturnValue(false);
        }
    }

}
