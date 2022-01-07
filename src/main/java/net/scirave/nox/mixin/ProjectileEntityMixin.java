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

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileEntity.class)
public abstract class ProjectileEntityMixin {

    @Shadow
    @Nullable
    public abstract Entity getOwner();

    @Inject(method = "canHit", at = @At("HEAD"), cancellable = true)
    public void nox$phaseThroughBystanders(Entity entity, CallbackInfoReturnable<Boolean> cir) {

        Entity owner = this.getOwner();

        if (owner instanceof HostileEntity mob1 && entity instanceof HostileEntity mob2) {
            if (mob1.getTarget() != null && mob1.getTarget() == mob2.getTarget()) {
                cir.setReturnValue(false);
            }
        } else if (owner instanceof GolemEntity golem) {
            if (!(entity instanceof Monster) && entity != golem.getTarget()) {
                cir.setReturnValue(false);
            }
        }
    }
}
