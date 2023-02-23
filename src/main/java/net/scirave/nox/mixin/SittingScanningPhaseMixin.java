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
import net.minecraft.entity.boss.dragon.phase.SittingScanningPhase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(SittingScanningPhase.class)
public class SittingScanningPhaseMixin {

    @ModifyArg(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/phase/PhaseManager;setPhase(Lnet/minecraft/entity/boss/dragon/phase/PhaseType;)V", ordinal = 0))
    public PhaseType<?> nox$enderDragonLessScanning(PhaseType<?> type) {
        return PhaseType.SITTING_FLAMING;
    }

}
