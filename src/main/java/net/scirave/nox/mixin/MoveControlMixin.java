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

import net.minecraft.entity.ai.control.MoveControl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MoveControl.class)
public class MoveControlMixin {

    @Shadow
    protected double speed;

    @Inject(method = "strafeTo", at = @At("TAIL"))
    public void nox$fasterStrafing(float forward, float sideways, CallbackInfo ci) {
        this.speed *= 1.5;
    }

}
