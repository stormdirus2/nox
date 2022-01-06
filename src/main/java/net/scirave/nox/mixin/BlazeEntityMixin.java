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
import net.minecraft.entity.mob.BlazeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlazeEntity.class)
public abstract class BlazeEntityMixin extends HostileEntityMixin {

    @Override
    public void nox$onTick(CallbackInfo ci) {
        LivingEntity target = this.getTarget();
        if (target != null && target.squaredDistanceTo((BlazeEntity) (Object) this) <= 4) {
            target.setOnFireFor(4);
        }
    }

}
