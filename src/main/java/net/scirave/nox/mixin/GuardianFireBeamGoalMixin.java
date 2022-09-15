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

import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuardianEntity.FireBeamGoal.class)
public class GuardianFireBeamGoalMixin {

    @Redirect(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/GuardianEntity;isSilent()Z"))
    public boolean nox$guardianShutUp(GuardianEntity instance) {
        if (instance.getTarget() instanceof PlayerEntity) {
            return instance.isSilent();
        }
        return true;
    }

}
