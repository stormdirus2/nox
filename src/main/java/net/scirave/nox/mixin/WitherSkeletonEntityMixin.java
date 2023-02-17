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
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.scirave.nox.config.NoxConfig;
import net.scirave.nox.goals.Nox$MineBlockGoal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = WitherSkeletonEntity.class)
public abstract class WitherSkeletonEntityMixin extends AbstractSkeletonEntityMixin {

    @Inject(method = "initEquipment", at = @At("TAIL"))
    public void nox$witherSkeletonArchers(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        if (NoxConfig.witherSkeletonArchersExist && this.getRandom().nextBoolean()) {
            this.equipStack(EquipmentSlot.MAINHAND, new ItemStack(Items.BOW));
        }
    }

    @ModifyVariable(method = "createArrowProjectile", at = @At("HEAD"), argsOnly = true)
    public float nox$witherSkeletonArcherBuff(float original) {
        if (NoxConfig.witherSkeletonArcherDamageMultiplier > 1)
            return original * NoxConfig.witherSkeletonArcherDamageMultiplier;
        return original;
    }

    @Inject(method = "initGoals", at = @At("TAIL"))
    public void nox$witherSkeletonInitGoals(CallbackInfo ci) {
        this.goalSelector.add(4, new Nox$MineBlockGoal((WitherSkeletonEntity) (Object) this));
    }

    @Override
    public void nox$onTick(CallbackInfo ci) {
        if (NoxConfig.witherSkeletonsWitherAuraRadius > 0) {
            LivingEntity target = this.getTarget();
            if (target != null && !target.hasStatusEffect(StatusEffects.WITHER) && target.squaredDistanceTo((WitherSkeletonEntity) (Object) this) <= MathHelper.square(NoxConfig.witherSkeletonsWitherAuraRadius)) {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, NoxConfig.witherSkeletonsWitherAuraDuration), (WitherSkeletonEntity) (Object) this);
            }
        }
    }

    @Override
    public void nox$modifyAttributes(EntityType<?> entityType, World world, CallbackInfo ci) {
        if (NoxConfig.witherSkeletonKnockbackResistanceBonus > 0) {
            EntityAttributeInstance attr = this.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
            if (attr != null)
                attr.addPersistentModifier(new EntityAttributeModifier("Nox: Wither Skeleton bonus", NoxConfig.witherSkeletonKnockbackResistanceBonus, EntityAttributeModifier.Operation.ADDITION));
        }
    }

    @Override
    public boolean nox$isAllowedToMine() {
        return NoxConfig.witherSkeletonsBreakBlocks;
    }
}
