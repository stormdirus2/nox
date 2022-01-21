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

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.scirave.nox.util.Nox$WebManagerInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public abstract class BlockMixin {

    /**
     * @author AeiouEnigma
     * @reason Flag the BlockPos for removal from our Spider-placed Cobweb tracking Maps in the
     * event that something attempts to replace the Cobweb with another, non-Spider-placed Cobweb.
     */
    @Inject(
            method = "replace(Lnet/minecraft/block/BlockState;Lnet/minecraft/block/BlockState;Lnet/minecraft/world/WorldAccess;Lnet/minecraft/util/math/BlockPos;II)V",
            at = @At("HEAD")
    )
    private static void nox$onReplace(BlockState state, BlockState newState, WorldAccess world,
                                        BlockPos pos, int flags, int maxUpdateDepth, CallbackInfo ci) {
        if (!world.isClient() && state == Nox$WebManagerInterface.nox$COBWEB && state == newState && world instanceof World) {
            Nox$WebManagerInterface webOwner = Nox$WebManagerInterface.nox$getWebOwner((World) world, pos);
            if (webOwner != null)
                webOwner.nox$flagForRemoval(pos);
        }
    }

}
