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
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.world.World;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GolemEntity.class)
public abstract class GolemEntityMixin extends MobEntityMixin {

    @Override
    public void nox$modifyAttributes(EntityType<?> entityType, World world, CallbackInfo ci) {
        if (NoxConfig.buffGolems) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addTemporaryModifier(new EntityAttributeModifier("Nox: Golem bonus", NoxConfig.golemBaseHealthMultiplier - 1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
            this.setHealth(this.getMaxHealth());
            this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).addTemporaryModifier(new EntityAttributeModifier("Nox: Golem bonus", NoxConfig.golemFollowRangeMultiplier - 1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        }
    }
}
