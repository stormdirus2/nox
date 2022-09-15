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

import net.minecraft.entity.EntityType;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GhastEntity.class)
public abstract class GhastEntityMixin extends MobEntityMixin {

    @Inject(method = "isFireballFromPlayer", at = @At("HEAD"), cancellable = true)
    private static void nox$ghastNoInstantDeath(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }

    @Inject(method = "getFireballStrength", at = @At("RETURN"), cancellable = true)
    public void nox$ghastStrongerFireballs(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(cir.getReturnValue() * 2);
    }

    @Override
    public void nox$modifyAttributes(EntityType<?> entityType, World world, CallbackInfo ci) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addTemporaryModifier(new EntityAttributeModifier("Nox: Ghast bonus", 1.5, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        this.setHealth(this.getMaxHealth());
    }


}
