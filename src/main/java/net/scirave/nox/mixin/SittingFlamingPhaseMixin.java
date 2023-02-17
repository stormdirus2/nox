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

import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.boss.dragon.phase.SittingFlamingPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SittingFlamingPhase.class)
public class SittingFlamingPhaseMixin {

    @Shadow
    private int timesRun;

    @ModifyArg(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/phase/PhaseManager;setPhase(Lnet/minecraft/entity/boss/dragon/phase/PhaseType;)V", ordinal = 1))
    public PhaseType<?> nox$enderDragonLessBreathPhase(PhaseType<?> type) {
        if (this.timesRun >= 2) {
            return PhaseType.TAKEOFF;
        }
        return type;
    }

}
