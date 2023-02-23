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
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.mob.CreeperEntity;
import net.scirave.nox.config.NoxConfig;
import net.scirave.nox.goals.Nox$CreeperBreachGoal;
import net.scirave.nox.util.Nox$CreeperBreachInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CreeperEntity.class)
public abstract class CreeperEntityMixin extends HostileEntityMixin implements Nox$CreeperBreachInterface {

    @Inject(method = "initGoals", at = @At("TAIL"))
    public void nox$creeperInitGoals(CallbackInfo ci) {
        this.goalSelector.add(2, new FleeEntityGoal((CreeperEntity) (Object) this, LivingEntity.class,
                4.0F, 1.2D, 1.5D, (living) -> {
            if (!NoxConfig.creepersRunFromShields) return false;
            if (living instanceof LivingEntity livingEntity) {
                return livingEntity.isBlocking() && livingEntity.blockedByShield(EntityDamageSource.explosion((CreeperEntity) (Object) this));
            }
            return false;
        }));
        if (NoxConfig.creeperBreachDistance > 0) {
            this.goalSelector.add(3, new Nox$CreeperBreachGoal((CreeperEntity) (Object) this));
        }
        //this.goalSelector.add(3, new PounceAtTargetGoal((CreeperEntity) (Object) this, 0.4F));
    }

    @Override
    public boolean nox$isAllowedToBreachWalls() {
        return NoxConfig.creepersBreachWalls;
    }

}
