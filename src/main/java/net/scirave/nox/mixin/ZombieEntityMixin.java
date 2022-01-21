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
import net.minecraft.entity.ai.goal.AvoidSunlightGoal;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.scirave.nox.Nox;
import net.scirave.nox.goals.Nox$FleeSunlightGoal;
import net.scirave.nox.goals.Nox$MineBlockGoal;
import net.scirave.nox.util.Nox$PounceGoalInterface;
import net.scirave.nox.util.Nox$PouncingEntityInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ZombieEntity.class)
public abstract class ZombieEntityMixin extends HostileEntityMixin implements Nox$PouncingEntityInterface {

    @Shadow
    public abstract boolean isBaby();

    @Shadow
    protected abstract boolean burnsInDaylight();

    @Override
    public void nox$modifyAttributes(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        if (Nox.CONFIG.zombieKnockbackResistanceMultiplier > 1 && (Nox.CONFIG.babyZombiesGetKnockbackResistance || !this.isBaby())) {
            this.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE)
                    .addPersistentModifier(new EntityAttributeModifier("Nox: Zombie bonus", Nox.CONFIG.zombieKnockbackResistanceMultiplier - 1, EntityAttributeModifier.Operation.ADDITION));
        }
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    public void nox$zombieInitGoals(CallbackInfo ci) {
        if (this.burnsInDaylight()) {
            nox$zombieHideFromSun();
        }

        this.goalSelector.add(0, new Nox$MineBlockGoal((ZombieEntity) (Object) this));

        PounceAtTargetGoal goal = new PounceAtTargetGoal((ZombieEntity) (Object) this, 0.25F);
        ((Nox$PounceGoalInterface) goal).nox$setPounceCooldown(Math.max(Nox.CONFIG.zombiePounceCooldown, 0));
        this.goalSelector.add(1, goal);
    }

    public void nox$zombieHideFromSun() {
        this.goalSelector.add(1, new AvoidSunlightGoal((ZombieEntity) (Object) this));
        this.goalSelector.add(0, new Nox$FleeSunlightGoal((ZombieEntity) (Object) this, 1.0F));
    }

    @Override
    public boolean nox$isAllowedToMine() {
        return Nox.CONFIG.zombiesBreakBlocks;
    }

    @Override
    public boolean nox$isAllowedToPounce() {
        return Nox.CONFIG.zombiesPounceAtTarget;
    }

}
