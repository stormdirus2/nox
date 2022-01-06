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

import net.minecraft.entity.mob.SkeletonEntity;
import net.scirave.nox.goals.Nox$FleeSunlightGoal;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(SkeletonEntity.class)
public abstract class SkeletonEntityMixin extends AbstractSkeletonEntityMixin {

    @Override
    public void nox$customSkeletonGoals() {
        this.goalSelector.add(0, new Nox$FleeSunlightGoal((SkeletonEntity) (Object) this, 1.0F));
    }

}
