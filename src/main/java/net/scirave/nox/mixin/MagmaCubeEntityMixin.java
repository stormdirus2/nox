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
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.scirave.nox.Nox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MagmaCubeEntity.class)
public abstract class MagmaCubeEntityMixin extends SlimeEntityMixin {

    private static final BlockState nox$LAVA_SOURCE = Blocks.LAVA.getDefaultState();
    private static final BlockState nox$FLOWING_LAVA = Blocks.LAVA.getDefaultState().with(Properties.LEVEL_15, 8);
    private static final BlockState nox$SMALL_LAVA = Blocks.LAVA.getDefaultState().with(Properties.LEVEL_15, 7);

    @Inject(method = "getTicksUntilNextJump", at = @At("HEAD"), cancellable = true)
    private void nox$makeMagmaCubesJumpConstantly(CallbackInfoReturnable<Integer> cir) {
        if (Nox.CONFIG.slimesJumpConstantly)
            cir.setReturnValue(4);
    }

    @Override
    public void nox$slimeOnAttack(LivingEntity victim, CallbackInfo ci) {
        victim.setOnFireFor(4);
    }

    private void nox$attemptLavaFill(BlockPos pos) {
        if (!this.world.isClient && this.world.getBlockState(pos).getMaterial().isReplaceable()) {
            this.world.setBlockState(pos, Nox.CONFIG.magmaCubeMakesLavaSourceBlocks ? nox$LAVA_SOURCE : nox$FLOWING_LAVA);
        }
    }

    private void nox$attemptSmallLavaFill(BlockPos pos) {
        // Used for aesthetics when magmaCubeMakesLavaSourceBlocks is false
        if (!this.world.isClient && this.world.getBlockState(pos).getMaterial().isReplaceable())
            this.world.setBlockState(pos, nox$SMALL_LAVA);
    }

    @Override
    public void nox$slimeOnDeath() {
        if (Nox.CONFIG.magmaCubeLeavesLavaWhenKilled) {
            BlockPos origin = this.getBlockPos();
            nox$attemptLavaFill(origin);
            int size = this.getSize();
            if (size < 2) {
                if (!Nox.CONFIG.magmaCubeMakesLavaSourceBlocks)
                    nox$attemptSmallLavaFill(origin.up());
            }
            else {
                if (Nox.CONFIG.magmaCubeMakesLavaSourceBlocks)
                    nox$attemptLavaFill(origin.up());
                else
                    nox$attemptSmallLavaFill(origin.up());
                nox$attemptLavaFill(origin.down());
                nox$attemptLavaFill(origin.north());
                nox$attemptLavaFill(origin.south());
                nox$attemptLavaFill(origin.east());
                nox$attemptLavaFill(origin.west());
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
    }


}
