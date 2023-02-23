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

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MeleeAttackGoal.class)
public abstract class MeleeAttackGoalMixin {

    @Shadow
    @Final
    protected PathAwareEntity mob;

    @Shadow
    protected abstract double getSquaredMaxAttackDistance(LivingEntity entity);

    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    public void nox$meleeUpdateCheck(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity == null) {
            cir.setReturnValue(false);
        } else if (!livingEntity.isAlive()) {
            if (this.getSquaredMaxAttackDistance(livingEntity) >= this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ())) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "shouldContinue", at = @At("HEAD"), cancellable = true)
    public void nox$meleeContinueCheck(CallbackInfoReturnable<Boolean> cir) {
        LivingEntity livingEntity = this.mob.getTarget();
        if (livingEntity == null) {
            cir.setReturnValue(false);
        } else if (!livingEntity.isAlive()) {
            if (this.getSquaredMaxAttackDistance(livingEntity) >= this.mob.squaredDistanceTo(livingEntity.getX(), livingEntity.getY(), livingEntity.getZ())) {
                cir.setReturnValue(!(livingEntity instanceof PlayerEntity) || !livingEntity.isSpectator() && !((PlayerEntity) livingEntity).isCreative());
            }
        }
    }

}
