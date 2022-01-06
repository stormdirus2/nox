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

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.function.Predicate;

@Mixin(ActiveTargetGoal.class)
public abstract class ActiveTargetGoalMixin extends TrackTargetGoal {

    @Shadow
    protected TargetPredicate targetPredicate;

    @Shadow
    @Nullable
    protected LivingEntity targetEntity;

    public ActiveTargetGoalMixin(MobEntity mob, boolean checkVisibility) {
        super(mob, checkVisibility);
    }

    @Shadow
    protected abstract void findClosestTarget();

    @Inject(method = "<init>(Lnet/minecraft/entity/mob/MobEntity;Ljava/lang/Class;IZZLjava/util/function/Predicate;)V", at = @At("TAIL"))
    public void nox$seeThroughWalls(MobEntity mob, Class targetClass, int reciprocalChance, boolean checkVisibility, boolean checkCanNavigate, Predicate targetPredicate, CallbackInfo ci) {
        if (mob instanceof Monster) {
            this.targetPredicate.ignoreVisibility();
        }
    }

    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    public void nox$noRandomTarget(CallbackInfoReturnable<Boolean> cir) {
        this.findClosestTarget();
        cir.setReturnValue(this.targetEntity != null);
    }

}
