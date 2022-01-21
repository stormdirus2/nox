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
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.BlockPos;
import net.scirave.nox.util.Nox$WebManagerInterface;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(ServerPlayerInteractionManager.class)
public abstract class ServerPlayerInteractionManagerMixin {

    @Shadow
    protected ServerWorld world;
    @Final
    @Shadow
    protected ServerPlayerEntity player;

    @Shadow
    public abstract boolean isCreative();

    /**
     * @author AeiouEnigma
     * @reason We have two objectives when managing our Spider-placed Cobwebs. We need to ensure that they drop nothing
     * when harvested, and we need to ensure that when they are removed from the world we immediately update our Maps
     * so that the BlockPos is no longer designated as containing a Spider-placed Cobweb.
     *
     * This inject covers the case where a Player is directly breaking a Cobweb.
     * BlockMixin::nox$onReplace and WorldMixin::nox$onSetBlockState are intended to cover all other cases.
     */
    @Inject(
            method = "tryBreakBlock",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/block/Block;onBreak(Lnet/minecraft/world/World;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;Lnet/minecraft/entity/player/PlayerEntity;)V",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true
    )
    private void nox$onTryBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir, BlockState blockState, BlockEntity blockEntity, Block block) {
        if (blockState == Nox$WebManagerInterface.nox$COBWEB && !this.isCreative()) {
            Nox$WebManagerInterface webOwner = Nox$WebManagerInterface.nox$getWebOwner(this.world, pos);
            if (webOwner != null) {
                webOwner.nox$flagForRemoval(pos);
                boolean bl = this.world.removeBlock(pos, false);
                if (bl)
                    block.onBroken(this.world, pos, blockState);
                ItemStack itemStack = this.player.getMainHandStack();
                itemStack.postMine(this.world, blockState, pos, this.player);
                if (bl && this.player.canHarvest(blockState)) {
                    this.player.incrementStat(Stats.MINED.getOrCreateStat(block));
                    this.player.addExhaustion(0.005f);
                }
                // This @Inject is intended to fully replace the vanilla method execution for tracked Cobwebs.
                cir.setReturnValue(true);
            }
        }
    }

}
