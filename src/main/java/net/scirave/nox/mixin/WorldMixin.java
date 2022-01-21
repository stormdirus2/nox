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
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.scirave.nox.util.Nox$WebManagerInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {

    @Final
    @Shadow
    public boolean isClient;

    @Shadow
    public abstract BlockState getBlockState(BlockPos pos);

    /**
     * @author AeiouEnigma
     * @reason If setBlockState is called to change the BlockState of one of our mapped
     * Spider-placed Cobweb positions, we want to remove the BlockPos from our tracking Maps.
     */
    @Inject(method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z", at = @At("HEAD"))
    private void nox$onSetBlockState(BlockPos pos, BlockState state, int flags, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir) {
        if (!this.isClient && this.getBlockState(pos) == Nox$WebManagerInterface.nox$COBWEB) {
            Nox$WebManagerInterface webOwner = Nox$WebManagerInterface.nox$getWebOwner((World) (Object) this, pos);
            if (webOwner != null)
                webOwner.nox$flagForRemoval(pos);
        }
    }

}
