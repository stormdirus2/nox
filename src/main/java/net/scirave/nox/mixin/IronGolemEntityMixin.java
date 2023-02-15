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
import net.minecraft.util.math.Box;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(IronGolemEntity.class)
public abstract class IronGolemEntityMixin extends GolemEntityMixin {

    @Shadow
    public abstract boolean canTarget(EntityType<?> type);

    @Shadow
    public abstract boolean tryAttack(Entity target);

    private boolean nox$canSweepAttack = true;

    @Inject(method = "tryAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/IronGolemEntity;applyDamageEffects(Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/Entity;)V"))
    public void nox$ironGolemSweepAttack(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (NoxConfig.ironGolemsHaveASweepAttack) {
            if (this.nox$canSweepAttack) {
                this.nox$canSweepAttack = false;
                List<MobEntity> list = this.world.getEntitiesByClass(MobEntity.class, Box.of(target.getPos(), 1, 1, 1), (mob) -> (mob instanceof Monster || mob.getTarget() == (Object) this) && this.canTarget(mob.getType()) && this.canTarget(mob));
                for (MobEntity mob : list) {
                    this.tryAttack(mob);
                }
            }
            this.nox$canSweepAttack = true;
        }
    }

}
