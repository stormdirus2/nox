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
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.IronGolemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntityMixin {

    @Shadow public abstract boolean canTarget(EntityType<?> type);

    @Shadow public abstract boolean tryAttack(Entity target);

    private boolean canSweepAttack = true;

    @Inject(method = "tryAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/IronGolemEntity;applyDamageEffects(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V"))
    public void nox$ironGolemSweepAttack(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (this.canSweepAttack) {
            this.canSweepAttack = false;
            List<MobEntity> list = this.world.getEntitiesByClass(MobEntity.class, target.getBoundingBox().expand(1.0D, 0.25D, 1.0D), (mob) -> (mob instanceof Monster || mob.getTarget() == (Object) this) && this.canTarget(mob.getType()) && this.canTarget(mob));
            for (MobEntity mob : list) {
                this.tryAttack(mob);
            }
        }
        this.canSweepAttack = true;
    }

}
