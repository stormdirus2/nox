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

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
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

    @Inject(method = "initialize", at = @At("TAIL"))
    public void nox$modifyAttributes(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        //Overridden
    }

    @Inject(method = "initialize", at = @At("HEAD"))
    public void nox$maybeApplyHostileAttributes(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        if (this instanceof Monster) {
            this.nox$hostileAttributes((MobEntity) (Object) this);
        }
    }

    public void nox$hostileAttributes(MobEntity mob) {
        mob.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(new EntityAttributeModifier("Nox: Hostile bonus", 0.5, EntityAttributeModifier.Operation.MULTIPLY_BASE));
        mob.setHealth(mob.getMaxHealth());
        mob.getAttributeInstance(EntityAttributes.GENERIC_FOLLOW_RANGE).addPersistentModifier(new EntityAttributeModifier("Nox: Hostile bonus", 0.5, EntityAttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Override
    public void nox$onPushAway(Entity entity, CallbackInfo ci) {
        if (this instanceof Monster && this.getTarget() == null && entity instanceof PlayerEntity player && this.canTarget(player)) {
            nox$maybeAngerOnShove(player);
        }
    }

    public void nox$maybeAngerOnShove(PlayerEntity player) {
        this.setTarget(player);
    }

    @Override
    public void nox$invulnerableCheck(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (this instanceof Monster) {
            if (source.getAttacker() instanceof MobEntity attacker && attacker instanceof Monster) {
                if (attacker.getTarget() != null && attacker.getTarget() == this.getTarget()) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

}
