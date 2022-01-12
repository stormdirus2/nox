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

import net.minecraft.entity.mob.PillagerEntity;
import net.scirave.nox.Nox;
import net.scirave.nox.goals.Nox$MineBlockGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PillagerEntity.class)
public abstract class PillagerEntityMixin extends HostileEntityMixin {

    @Override
    public void nox$initGoals(CallbackInfo ci) {
        if (Nox.CONFIG.pillagersBreakBlocks)
            this.goalSelector.add(4, new Nox$MineBlockGoal((PillagerEntity) (Object) this));
    }

}
