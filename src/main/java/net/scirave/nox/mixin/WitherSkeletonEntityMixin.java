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
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.scirave.nox.goals.Nox$MineBlockGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WitherSkeletonEntity.class)
public abstract class WitherSkeletonEntityMixin extends AbstractSkeletonEntityMixin {

    @Inject(method = "initEquipment", at = @At("TAIL"))
    public void nox$witherSkeletonArchers(LocalDifficulty difficulty, CallbackInfo ci) {
        if (this.getRandom().nextBoolean()) {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        }
    }

    @ModifyVariable(method = "createArrowProjectile", at = @At("HEAD"), argsOnly = true)
    public float nox$witherSkeletonArcherBuff(float original) {
        return original * 1.5F;
    }


    @Inject(method = "initGoals", at = @At("TAIL"))
    public void nox$witherSkeletonBreakWalls(CallbackInfo ci) {
        this.goalSelector.add(4, new Nox$MineBlockGoal((WitherSkeletonEntity) (Object) this));
    }

    @Override
    public void nox$onTick(CallbackInfo ci) {
        LivingEntity target = this.getTarget();
        if (target != null && !target.hasStatusEffect(StatusEffects.WITHER) && target.squaredDistanceTo((WitherSkeletonEntity) (Object) this) <= 4) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 80), (WitherSkeletonEntity) (Object) this);
        }
    }

    @Override
    public void nox$modifyAttributes(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        this.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE).addPersistentModifier(new EntityAttributeModifier("Nox: Wither Skeleton bonus", 0.3, EntityAttributeModifier.Operation.ADDITION));
    }

}
