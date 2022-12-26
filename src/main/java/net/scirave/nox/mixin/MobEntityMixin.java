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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.World;
import net.scirave.nox.config.NoxConfig;
import net.scirave.nox.util.NoxUtil;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = MobEntity.class)
public abstract class MobEntityMixin extends LivingEntityMixin {

    @Shadow
    @Final
    protected GoalSelector goalSelector;

    @Shadow
    @Nullable
    public abstract LivingEntity getTarget();

    @Shadow
    public abstract void setTarget(@Nullable LivingEntity target);

    @Shadow
    public abstract void equipStack(EquipmentSlot slot, ItemStack stack);

    @Inject(method = "tryAttack", at = @At("RETURN"))
    public void nox$onAttack(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && target instanceof LivingEntity living) {
            nox$onSuccessfulAttack(living);
        }
    }

    public void nox$onSuccessfulAttack(LivingEntity target) {
        //Overridden
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    public void nox$initGoals(CallbackInfo ci) {
        //Overridden
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    public void nox$modifyAttributes(EntityType<?> entityType, World world, CallbackInfo ci) {
        //Overridden
    }


    @Inject(method = "<init>", at = @At("TAIL"))
    public void nox$hostileAttributes(EntityType<?> entityType, World world, CallbackInfo ci) {
        if (this instanceof Monster) {
            this.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).addTemporaryModifier(new EntityAttributeModifier("Nox: Hostile bonus", NoxConfig.monsterRangeMultiplier - 1, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        }
    }

    @Inject(method = "initEquipment", at = @At("TAIL"))
    public void nox$difficultyScaling(Random random, LocalDifficulty localDifficulty, CallbackInfo ci) {
        if (this instanceof Monster && NoxConfig.monsterGearScales) {
            NoxUtil.weaponRoulette((ServerWorld) this.getWorld(), (MobEntity) (Object) this, random, localDifficulty);
            NoxUtil.armorRoulette((ServerWorld) this.getWorld(), (MobEntity) (Object) this, random, localDifficulty);
        }
    }

    @Override
    public void nox$onDamaged(DamageSource source, float amount, CallbackInfo ci) {
        if (this instanceof Monster && source.getAttacker() != null) {
            if (this.isUsingItem()) {
                this.stopUsingItem();
            }
        }
    }

    @Override
    public void nox$onPushAway(Entity entity, CallbackInfo ci) {
        if (this instanceof Monster && NoxConfig.monsterAngerOnShove && this.getTarget() == null && entity instanceof PlayerEntity player && this.canTarget(player)) {
            nox$maybeAngerOnShove(player);
        }
    }

    public void nox$maybeAngerOnShove(PlayerEntity player) {
        this.setTarget(player);
    }

    @Override
    public void nox$shouldTakeDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (amount < this.getHealth() || (this.getHealth()/this.getMaxHealth()) > 0.25) {
            Entity attacker = source.getAttacker();
            if (attacker instanceof MobEntity mob && NoxUtil.isAnAlly(mob, (MobEntity) (Object) this)) {
                cir.setReturnValue(false);
            }
        }
    }

}
