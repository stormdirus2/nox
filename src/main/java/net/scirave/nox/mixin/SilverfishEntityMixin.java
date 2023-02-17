/*
 * -------------------------------------------------------------------
 * Nox
 * Copyright (c) 2023 SciRave
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * -------------------------------------------------------------------
 */

package net.scirave.nox.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.SilverfishEntity;
import net.minecraft.world.World;
import net.scirave.nox.config.NoxConfig;
import net.scirave.nox.util.Nox$PouncingEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SilverfishEntity.class)
public abstract class SilverfishEntityMixin extends HostileEntityMixin implements Nox$PouncingEntityInterface {

    @Override
    public void nox$modifyAttributes(EntityType<?> entityType, World world, CallbackInfo ci) {
        if (NoxConfig.silverfishMoveSpeedMultiplier > 1) {
            EntityAttributeInstance attr = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
            if (attr != null)
                attr.addTemporaryModifier(new EntityAttributeModifier("Nox: Silverfish bonus", NoxConfig.silverfishMoveSpeedMultiplier - 1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        }
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    public void nox$silverfishInitGoals(CallbackInfo ci) {
        this.goalSelector.add(2, new PounceAtTargetGoal((SilverfishEntity) (Object) this, 0.2F));
    }

    @Override
    public void nox$onSuccessfulAttack(LivingEntity target) {
        if (NoxConfig.silverfishAttacksGiveMiningFatigue) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.MINING_FATIGUE, NoxConfig.silverfishMiningFatigueBiteDuration, 2), (SilverfishEntity) (Object) this);
        }
    }

    @Override
    public void nox$shouldTakeDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        super.nox$shouldTakeDamage(source, amount, cir);
        if (source.getName().equals("fall"))
            cir.setReturnValue(NoxConfig.silverfishImmuneToFallDamage);
        else if (source.getName().equals("drown"))
            cir.setReturnValue(NoxConfig.silverfishCanDrown);
        else if (source.getName().equals("inWall"))
            cir.setReturnValue(NoxConfig.silverfishCanSuffocate);
    }

    @Override
    public boolean nox$isAllowedToPounce() {
        return NoxConfig.silverfishPounceAtTarget;
    }

}
