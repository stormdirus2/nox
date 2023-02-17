/*
 * -------------------------------------------------------------------
 * Nox
 * Copyright (c) 2023 SciRave
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * -------------------------------------------------------------------
 */

package net.scirave.nox.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.phase.AbstractPhase;
import net.minecraft.entity.boss.dragon.phase.PhaseType;
import net.minecraft.entity.boss.dragon.phase.StrafePlayerPhase;
import net.scirave.nox.util.NoxUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StrafePlayerPhase.class)
public abstract class StrafePlayerPhaseMixin extends AbstractPhase {

    @Shadow
    @Nullable
    private LivingEntity target;

    @Shadow
    @Nullable
    private Path path;

    private long cooldown = 0;

    private int fireballShots = 0;

    public StrafePlayerPhaseMixin(EnderDragonEntity dragon) {
        super(dragon);
    }

    @Inject(method = "beginPhase", at = @At("HEAD"))
    public void nox$resetDragonFireballs(CallbackInfo ci) {
        this.fireballShots = 0;
    }

    @Inject(method = "serverTick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;squaredDistanceTo(Lnet/minecraft/entity/Entity;)D"), cancellable = true)
    public void nox$enderDragonBetterFireball(CallbackInfo ci) {
        if (cooldown > 0 || this.target == null) {
            cooldown--;
        } else if (this.target.squaredDistanceTo(this.dragon) < 4096.0D && this.dragon.canSee(this.target)) {
            cooldown = 20;
            fireballShots++;
            NoxUtil.EnderDragonShootFireball(this.dragon, this.target);
            if (this.path != null) {
                while (!this.path.isFinished()) {
                    this.path.next();
                }
            }
            if (fireballShots >= 5) {
                this.dragon.getPhaseManager().setPhase(PhaseType.LANDING_APPROACH);
            }
        }
        ci.cancel();
    }

}
