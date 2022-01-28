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

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HuskEntity;
import net.scirave.nox.Nox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(HuskEntity.class)
public abstract class HuskEntityMixin extends ZombieEntityMixin {

    @ModifyArg(method = "tryAttack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z"))
    public StatusEffectInstance nox$huskBetterHunger(StatusEffectInstance effect) {
        if (Nox.CONFIG.huskAttacksApplyStrongerHunger)
            return new StatusEffectInstance(effect.getEffectType(), effect.getDuration(), 1);
        return effect;
    }

}
