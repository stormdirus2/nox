/*
 * -------------------------------------------------------------------
 * Nox
 * Copyright (c) 2023 SciRave
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * -------------------------------------------------------------------
 */

package net.scirave.nox.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.scirave.nox.config.NoxConfig;
import net.scirave.nox.goals.Nox$MineBlockGoal;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin extends HostileEntityMixin {

    @Shadow
    public abstract void setTarget(@Nullable LivingEntity target);

    @Shadow
    protected abstract boolean teleportRandomly();

    @Shadow
    public abstract void setProvoked();

    @Inject(method = "setTarget", at = @At("HEAD"))
    public void nox$endermanBlindOnProvoked(LivingEntity target, CallbackInfo ci) {
        if (NoxConfig.endermanAppliesBlindnessOnAggro && this.getTarget() != target && target != null) {
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, NoxConfig.endermanBlindnessStareDuration), (EndermanEntity) (Object) this);
        }
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/EndermanEntity;teleportRandomly()Z", ordinal = 1), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
    public void nox$endermanLessRandomTeleport(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir, boolean entity) {
        if (source.getName().equals("onFire") || source.isMagic()) {
            cir.setReturnValue(entity);
        }
    }

    @Inject(method = "damage", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/damage/DamageSource;getAttacker()Lnet/minecraft/entity/Entity;"))
    public void nox$endermanTeleportOnDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (this.isAlive() && NoxConfig.endermanTeleportsFromMeleeHit && source.getAttacker() instanceof LivingEntity && !source.getName().equals("onFire") && !source.isMagic()) {
            for (int i = 0; i < 64; ++i) {
                if (this.teleportRandomly()) {
                    break;
                }
            }
        }
    }

    @Inject(method = "initGoals", at = @At("HEAD"))
    public void nox$endermanInitGoals(CallbackInfo ci) {
        this.goalSelector.add(1, new Nox$MineBlockGoal((EndermanEntity) (Object) this));
    }

    @Override
    public void nox$maybeAngerOnShove(PlayerEntity player) {
        super.nox$maybeAngerOnShove(player);
        this.setProvoked();
    }

    @Override
    public void nox$onSuccessfulAttack(LivingEntity target) {
        if (NoxConfig.endermanAppliesBlindnessOnHit)
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.DARKNESS, NoxConfig.endermanBlindnessHitDuration), (EndermanEntity) (Object) this);
    }

    @Override
    public boolean nox$isAllowedToMine() {
        return NoxConfig.endermenBreakBlocks;
    }
}
