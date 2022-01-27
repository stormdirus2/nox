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
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.VindicatorEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.scirave.nox.Nox;
import net.scirave.nox.goals.Nox$MineBlockGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VindicatorEntity.class)
public abstract class VindicatorEntityMixin extends HostileEntityMixin {

    @Override
    public void nox$initGoals(CallbackInfo ci) {
        this.goalSelector.add(1, new Nox$MineBlockGoal((VindicatorEntity) (Object) this));
    }

    @Override
    public void nox$modifyAttributes(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        if (Nox.CONFIG.vindicatorKnockbackResistanceBonus > 0)
            this.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).addPersistentModifier(new EntityAttributeModifier("Nox: Vindicator bonus", Nox.CONFIG.vindicatorKnockbackResistanceBonus, EntityAttributeModifier.Operation.ADDITION));
    }

    @Override
    public boolean nox$isAllowedToMine() {
        return Nox.CONFIG.vindicatorsBreakBlocks;
    }

}
