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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.MagmaCubeEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = MagmaCubeEntity.class)
public abstract class MagmaCubeEntityMixin extends SlimeEntityMixin {

    private static final BlockState LAVA = Blocks.LAVA.getDefaultState();

    @Override
    public void nox$slimeOnAttack(LivingEntity victim, CallbackInfo ci) {
        victim.setOnFireFor(4);
    }

    private void nox$attemptLavaFill(BlockPos pos) {
        if (this.world.getBlockState(pos).getMaterial().isReplaceable()) {
            this.world.setBlockState(pos, LAVA);
        }
    }

    @Override
    public void nox$slimeOnDeath() {
        BlockPos origin = this.getBlockPos();
        nox$attemptLavaFill(origin);
        int size = this.getSize();
        if (size >= 2) {
            nox$attemptLavaFill(origin.up());
            nox$attemptLavaFill(origin.down());
            nox$attemptLavaFill(origin.north());
            nox$attemptLavaFill(origin.south());
            nox$attemptLavaFill(origin.east());
            nox$attemptLavaFill(origin.west());
        }
        if (size >= 3) {
            nox$attemptLavaFill(origin.up().north());
            nox$attemptLavaFill(origin.up().south());
            nox$attemptLavaFill(origin.up().east());
            nox$attemptLavaFill(origin.up().west());
            nox$attemptLavaFill(origin.down().north());
            nox$attemptLavaFill(origin.down().south());
            nox$attemptLavaFill(origin.down().east());
            nox$attemptLavaFill(origin.down().west());
            nox$attemptLavaFill(origin.north().east());
            nox$attemptLavaFill(origin.north().west());
            nox$attemptLavaFill(origin.south().east());
            nox$attemptLavaFill(origin.south().west());
        }
    }


}
