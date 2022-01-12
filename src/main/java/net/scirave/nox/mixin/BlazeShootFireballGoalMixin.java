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
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.mob.BlazeEntity;
import net.scirave.nox.Nox;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlazeEntity.ShootFireballGoal.class)
public abstract class BlazeShootFireballGoalMixin {

    @Shadow
    private int fireballCooldown;

    @Shadow
    @Final
    private BlazeEntity blaze;
    @Shadow
    private int fireballsFired;
    private int windup = -1;
    private boolean movingLeft = false;
    private boolean heldShield = false;

    @Shadow
    protected abstract double getFollowRange();

    @Inject(method = "tick", at = @At("HEAD"))
    public void nox$blazeLessFireballCooldown(CallbackInfo ci) {
        if (Nox.CONFIG.blazeFireballRechargeSpeedMultiplier > 1)
            this.fireballCooldown -= (Nox.CONFIG.blazeFireballRechargeSpeedMultiplier - 1);
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/BlazeEntity;getX()D", ordinal = 0), cancellable = true)
    public void nox$blazeDontShootShields(CallbackInfo ci) {
        if (this.fireballsFired == 1) {
            LivingEntity target = this.blaze.getTarget();
            if (target == null) return;

            DamageSource fakeSource = EntityDamageSource.mob(this.blaze);
            fakeSource.setProjectile();

            if (windup > -1) {
                if (windup > 0) {
                    ci.cancel();
                }
                windup--;
            } else if (target.isBlocking() && target.blockedByShield(fakeSource)) {
                heldShield = true;
                ci.cancel();
            } else if (heldShield) {
                heldShield = false;
                windup = 4;
                ci.cancel();
            }
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void nox$blazeStrafe(CallbackInfo ci) {
        LivingEntity target = this.blaze.getTarget();
        if (target != null) {

            this.blaze.lookAtEntity(target, 30.0F, 30.0F);
            boolean backward = false;

            double d = this.blaze.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
            if (d < this.getFollowRange() * 0.75) {
                backward = true;
            }

            if ((double) this.blaze.getRandom().nextFloat() < 0.1F) {
                this.movingLeft = !this.movingLeft;
            }

            this.blaze.getMoveControl().strafeTo(backward ? -0.5F : 0.5F, this.movingLeft ? 0.5F : -0.5F);
        }
    }

}
