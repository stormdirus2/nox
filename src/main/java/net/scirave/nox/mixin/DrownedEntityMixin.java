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

import net.minecraft.entity.mob.DrownedEntity;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(DrownedEntity.class)
public abstract class DrownedEntityMixin extends ZombieEntityMixin {

    @ModifyArg(method = "travel", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/DrownedEntity;updateVelocity(FLnet/minecraft/util/math/Vec3d;)V"))
    private float nox$drownedFasterSwimming(float original) {
        if (NoxConfig.drownedSwimSpeedMultiplier > 1) {
            return original * NoxConfig.drownedSwimSpeedMultiplier;
        }
        return original;
    }

}
