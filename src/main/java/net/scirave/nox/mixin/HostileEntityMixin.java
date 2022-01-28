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

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(HostileEntity.class)
public abstract class HostileEntityMixin extends MobEntityMixin {

    @Inject(method = "canSpawnInDark", at = @At("HEAD"), cancellable = true)
    private static void nox$onSpawnAttempt(EntityType<? extends HostileEntity> type, ServerWorldAccess world,
                                           SpawnReason spawnReason, BlockPos pos, Random random, CallbackInfoReturnable<Boolean> cir) {
        if (type == EntityType.CAVE_SPIDER && spawnReason == SpawnReason.NATURAL)
            if (pos.getY() >= world.getSeaLevel() || world.isSkyVisibleAllowingSea(pos))
                cir.setReturnValue(false);
    }

}
