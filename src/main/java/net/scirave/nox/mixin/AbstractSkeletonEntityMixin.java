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

import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.mob.AbstractSkeletonEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractSkeletonEntity.class)
public abstract class AbstractSkeletonEntityMixin extends HostileEntityMixin {

    @Inject(method = "initGoals", at = @At("HEAD"))
    public void nox$skeletonsSwim(CallbackInfo ci) {
        this.goalSelector.add(1, new SwimGoal((AbstractSkeletonEntity) (Object) this));
        this.nox$customSkeletonGoals();
    }

    public void nox$customSkeletonGoals() {
        //Overridden
    }

}
