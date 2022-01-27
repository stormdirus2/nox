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

import net.minecraft.entity.EntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.scirave.nox.Nox;
import net.scirave.nox.util.Nox$PouncingEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SilverfishEntity.class)
public abstract class SilverfishEntityMixin extends HostileEntityMixin implements Nox$PouncingEntityInterface {

    @Override
    public void nox$modifyAttributes(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        if (Nox.CONFIG.silverfishMoveSpeedMultiplier > 1)
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addPersistentModifier(new EntityAttributeModifier("Nox: Silverfish bonus", Nox.CONFIG.silverfishMoveSpeedMultiplier - 1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    public void nox$silverfishInitGoals(CallbackInfo ci) {
        this.goalSelector.add(2, new PounceAtTargetGoal((SilverfishEntity) (Object) this, 0.2F));
    }

    @Override
    public void nox$onSuccessfulAttack(LivingEntity target) {
        if (Nox.CONFIG.silverfishAttacksGiveMiningFatigue)
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, 300, 2), (SilverfishEntity) (Object) this);
    }

    @Override
    public void nox$invulnerableCheck(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        super.nox$invulnerableCheck(source, cir);
        if (source.getName().equals("fall") && Nox.CONFIG.silverfishImmuneToFallDamage)
            cir.setReturnValue(true);
        else if (source.getName().equals("drown") && !Nox.CONFIG.silverfishCanDrown)
            cir.setReturnValue(true);
        else if (source.getName().equals("inWall") && !Nox.CONFIG.silverfishCanSuffocate)
            cir.setReturnValue(true);
    }

    @Override
    public boolean nox$isAllowedToPounce() {
        return Nox.CONFIG.silverfishPounceAtTarget;
    }

}
