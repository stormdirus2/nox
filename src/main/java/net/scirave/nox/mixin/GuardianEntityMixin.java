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

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuardianEntity.class)
public abstract class GuardianEntityMixin extends HostileEntityMixin {

    private static final BlockState nox$WATER = Blocks.WATER.getDefaultState();

    @Override
    public void nox$onDeath(DamageSource source, CallbackInfo ci) {
        BlockPos pos = this.getBlockPos();
        if (this.world.getBlockState(pos).getMaterial().isReplaceable()) {
            this.world.setBlockState(pos, nox$WATER);
        }
    }

}
