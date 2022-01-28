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

import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.scirave.nox.Nox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GolemEntity.class)
public abstract class GolemEntityMixin extends MobEntityMixin {

    @Override
    public void nox$modifyAttributes(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        EntityAttributeInstance attr;
        if (Nox.CONFIG.golemBaseHealthMultiplier > 1) {
            attr = this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
            if (attr != null) {
                attr.addPersistentModifier(new EntityAttributeModifier("Nox: Golem bonus", Nox.CONFIG.golemBaseHealthMultiplier - 1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
                this.setHealth(this.getMaxHealth());
            }
        }
        if (Nox.CONFIG.golemFollowRangeMultiplier >= 0) {
            attr = this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE);
            if (attr != null)
                attr.addPersistentModifier(new EntityAttributeModifier("Nox: Golem bonus", Nox.CONFIG.golemFollowRangeMultiplier - 1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        }
    }
}
