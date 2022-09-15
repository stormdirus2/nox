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

import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.HoldingPatternPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HoldingPatternPhase.class)
public abstract class HoldingPatternPhaseMixin extends AbstractPhase {

    @Shadow
    @Final
    private static TargetPredicate PLAYERS_IN_RANGE_PREDICATE;

    public HoldingPatternPhaseMixin(EnderDragonEntity dragon) {
        super(dragon);
    }

    @Shadow
    protected abstract void strafePlayer(PlayerEntity player);

    @Inject(method = "tickInRange", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/random/Random;nextInt(I)I", ordinal = 0), cancellable = true)
    public void nox$enderDragonLessStalling(CallbackInfo ci) {
        BlockPos blockPos = this.dragon.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPortalFeature.ORIGIN));
        PlayerEntity player = this.dragon.world.getClosestPlayer(PLAYERS_IN_RANGE_PREDICATE, this.dragon, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        if (player != null) {
            if (this.dragon.getRandom().nextBoolean()) {
                this.strafePlayer(player);
            } else {
                this.dragon.getPhaseManager().setPhase(PhaseType.CHARGING_PLAYER);
                this.dragon.getPhaseManager().create(PhaseType.CHARGING_PLAYER).setPathTarget(player.getPos());
            }
        }
        ci.cancel();
    }

}
