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

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ProjectileAttackGoal;
import net.minecraft.entity.mob.MobEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ProjectileAttackGoal.class)
public abstract class ProjectileAttackGoalMixin {

    @Shadow
    @Nullable
    private LivingEntity target;

    @Shadow
    @Final
    private MobEntity mob;

    @Shadow
    @Final
    private float maxShootRange;
    @Shadow
    private int updateCountdownTicks;
    private boolean movingLeft = false;

    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void nox$projectileStrafe(CallbackInfo ci) {
        if (this.target != null) {
            this.mob.lookAtEntity(target, 30.0F, 30.0F);
            boolean backward = false;

            double d = this.mob.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
            if (d < (this.maxShootRange * this.maxShootRange * 0.75)) {
                backward = true;
            }

            if ((double) this.mob.getRandom().nextFloat() < 0.1F) {
                this.movingLeft = !this.movingLeft;
            }

            this.mob.getMoveControl().strafeTo(backward ? -0.5F : 0.5F, this.movingLeft ? 0.5F : -0.5F);
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/RangedAttackMob;attack(Lnet/minecraft/entity/LivingEntity;F)V"), cancellable = true)
    public void nox$projectileMaybeDontShootShields(CallbackInfo ci) {
        if (this.nox$projectileShouldntShootShields()) {
            this.updateCountdownTicks++;
            ci.cancel();
        }
    }

    public boolean nox$projectileShouldntShootShields() {
        return false;
    }

}
