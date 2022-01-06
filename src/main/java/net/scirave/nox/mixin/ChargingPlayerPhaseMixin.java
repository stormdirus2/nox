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
import net.minecraft.entity.boss.dragon.phase.ChargingPlayerPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.EndPortalFeature;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChargingPlayerPhase.class)
public abstract class ChargingPlayerPhaseMixin extends AbstractPhase {

    private static final TargetPredicate RANGE_PREDICATE = TargetPredicate.createAttackable().ignoreVisibility();

    @Shadow
    @Nullable
    private Vec3d pathTarget;

    @Shadow
    private int chargingTicks;

    public ChargingPlayerPhaseMixin(EnderDragonEntity dragon) {
        super(dragon);
    }

    @Shadow
    public abstract void setPathTarget(Vec3d pathTarget);

    @Inject(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/boss/dragon/phase/PhaseManager;setPhase(Lnet/minecraft/entity/boss/dragon/phase/PhaseType;)V", ordinal = 1), cancellable = true)
    public void nox$enderDragonLongerCharging(CallbackInfo ci) {
        if (this.pathTarget != null && this.chargingTicks++ < 200) {
            double d = this.pathTarget.squaredDistanceTo(this.dragon.getX(), this.dragon.getY(), this.dragon.getZ());
            if (d < 100.0D || d > 22500.0D || this.dragon.horizontalCollision || this.dragon.verticalCollision) {
                ++this.chargingTicks;
            }
        } else {
            this.dragon.getPhaseManager().setPhase(PhaseType.LANDING_APPROACH);
        }
        ci.cancel();
    }

    @Inject(method = "serverTick", at = @At(value = "HEAD"))
    public void nox$enderDragonBetterCharging(CallbackInfo ci) {
        BlockPos blockPos = this.dragon.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPortalFeature.ORIGIN));
        PlayerEntity player = this.dragon.world.getClosestPlayer(RANGE_PREDICATE, this.dragon, blockPos.getX(), blockPos.getY(), blockPos.getZ());
        if (player != null) {
            this.setPathTarget(player.getPos());

        }
    }

}
