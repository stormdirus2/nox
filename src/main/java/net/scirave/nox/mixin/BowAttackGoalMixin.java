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
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BowAttackGoal.class)
public class BowAttackGoalMixin {

    @Shadow
    @Final
    private HostileEntity actor;

    @Shadow
    private boolean movingToLeft;
    @Shadow
    private boolean backward;
    @Shadow
    @Final
    private float squaredRange;
    private int windup = -1;

    private boolean heldShield = false;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/HostileEntity;getItemUseTime()I"), cancellable = true)
    public void nox$bowDontShootShields(CallbackInfo ci) {
        LivingEntity target = this.actor.getTarget();
        if (target == null) return;

        DamageSource fakeSource = EntityDamageSource.mob(actor);
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
            windup = 6;
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void nox$newBowStrafe(CallbackInfo ci) {
        LivingEntity target = this.actor.getTarget();
        if (target != null) {

            this.backward = false;

            double d = this.actor.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
            if (d < (this.squaredRange * 0.25D)) {
                this.backward = true;
            }

            if ((double) this.actor.getRandom().nextFloat() < 0.1F) {
                this.movingToLeft = !this.movingToLeft;
            }

            this.actor.getMoveControl().strafeTo(backward ? -0.5F : 0.5F, this.movingToLeft ? 0.5F : -0.5F);
        }
    }

}
