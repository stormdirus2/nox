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
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.entity.mob.PhantomEntity;
import net.scirave.nox.util.Nox$MiningInterface;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin implements Nox$MiningInterface {

    private boolean mining = false;

    @Shadow
    public abstract boolean isBaby();

    @Shadow
    @Nullable
    public abstract EntityAttributeInstance getAttributeInstance(EntityAttribute attribute);

    @Shadow
    public abstract Random getRandom();

    @Shadow
    public abstract boolean isAlive();

    @Shadow
    public abstract void setHealth(float health);

    @Shadow
    public abstract float getMaxHealth();

    @Shadow
    public abstract boolean canSee(Entity entity);

    @Shadow
    public abstract boolean canTarget(LivingEntity target);

    @Shadow
    public abstract boolean isUsingItem();

    @Shadow
    public abstract void stopUsingItem();

    @Inject(method = "blockedByShield", at = @At("HEAD"), cancellable = true)
    public void nox$ghastFireballsPierce(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (
                (source.isExplosive() && source.getAttacker() instanceof GhastEntity) ||
                        (source.getAttacker() instanceof PhantomEntity)
        ) {
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At("HEAD"), cancellable = true)
    public void nox$onStatusEffect(StatusEffectInstance effect, Entity source, CallbackInfoReturnable<Boolean> cir) {
        //Overridden
    }

    @Inject(method = "onDeath", at = @At("HEAD"))
    public void nox$onDeath(DamageSource source, CallbackInfo ci) {
        //Overridden
    }

    @Inject(method = "applyDamage", at = @At("HEAD"))
    public void nox$onDamaged(DamageSource source, float amount, CallbackInfo ci) {
        //Overridden
    }

    @Inject(method = "pushAway", at = @At("HEAD"))
    public void nox$onPushAway(Entity entity, CallbackInfo ci) {
        //Overridden
    }


    @Inject(method = "tick", at = @At("HEAD"))
    public void nox$onTick(CallbackInfo ci) {
        //Overridden
    }

    @Override
    public boolean isMining() {
        return this.mining;
    }

    @Override
    public void setMining(boolean bool) {
        this.mining = bool;
    }

}
