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

import net.minecraft.entity.passive.SnowGolemEntity;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(SnowGolemEntity.class)
public abstract class SnowGolemEntityMixin extends GolemEntityMixin {

    @ModifyArgs(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/ProjectileAttackGoal;<init>(Lnet/minecraft/entity/ai/RangedAttackMob;DIF)V"))
    public void nox$snowGolemFasterShooting(Args args) {
        args.set(2, (int)(((int) args.get(2)) / Math.max(NoxConfig.snowGolemAttackRechargeSpeedMultiplier, 0)));
        args.set(3, ((float) args.get(3)) * Math.max(NoxConfig.snowGolemAttackRangeMultiplier, 1));
    }
    @ModifyArgs(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/thrown/SnowballEntity;setVelocity(DDDFF)V"))
    public void nox$snowGolemShotMixin(Args args) {
        args.set(3, NoxConfig.snowGolemShotSpeed);
        args.set(4, NoxConfig.snowGolemInverseAccuracy);
    }
}