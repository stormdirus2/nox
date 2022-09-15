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
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EvokerEntity;
import net.minecraft.world.World;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EvokerEntity.class)
public abstract class EvokerEntityMixin extends HostileEntityMixin {

    @Override
    public void nox$modifyAttributes(EntityType<?> entityType, World world, CallbackInfo ci) {
        if (NoxConfig.evokerBaseHealthMultiplier > 1) {
            EntityAttributeInstance attr = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (attr != null) {
                this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addTemporaryModifier(new EntityAttributeModifier("Nox: Evoker bonus", NoxConfig.evokerBaseHealthMultiplier - 1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
                this.setHealth(this.getMaxHealth());
            }
        }
    }

    @Override
    public void nox$shouldTakeDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        super.nox$shouldTakeDamage(source, amount, cir);
        if (NoxConfig.evokersImmuneToMagic && source.isMagic())
            cir.setReturnValue(false);
        if (NoxConfig.evokersResistProjectiles && source.isProjectile() && !source.bypassesArmor())
            cir.setReturnValue(false);
    }

}
