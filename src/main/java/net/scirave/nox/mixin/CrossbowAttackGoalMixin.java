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

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.CrossbowUser;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.CrossbowAttackGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CrossbowAttackGoal.class)
public class CrossbowAttackGoalMixin {

    @Shadow
    @Final
    private HostileEntity actor;

    @Shadow
    private int chargedTicksLeft;
    @Shadow
    private int cooldown;
    @Shadow
    private CrossbowAttackGoal.Stage stage;
    @Shadow
    @Final
    private float squaredRange;

    private boolean movingLeft = false;
    private int windup = -1;
    private boolean heldShield = false;

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/RangedAttackMob;attack(Lnet/minecraft/entity/LivingEntity;F)V"), cancellable = true)
    public void nox$crossbowDontShootShields(CallbackInfo ci) {
        LivingEntity target = this.actor.getTarget();
        if (target == null) return;

        Hand hand = ProjectileUtil.getHandPossiblyHolding(this.actor, Items.CROSSBOW);
        if (hand != null && EnchantmentHelper.getLevel(Enchantments.PIERCING, this.actor.getStackInHand(hand)) > 0)
            return;

        DamageSource fakeSource = EntityDamageSource.mob(actor);
        fakeSource.setProjectile();

        if (windup > -1) {
            if (windup > 0) {
                ci.cancel();
            }
            windup--;
        } else if (target.isBlocking() && target.blockedByShield(fakeSource)) {
            heldShield = true;
            ci.cancel();
        } else if (heldShield) {
            heldShield = false;
            windup = 6;
            ci.cancel();
        }
    }

    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void nox$crossbowLessDelay(CallbackInfo ci) {
        this.cooldown = 0;
        if (this.chargedTicksLeft > 20) {
            this.chargedTicksLeft = 20;
        }
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void nox$crossbowStrafe(CallbackInfo ci) {
        LivingEntity target = this.actor.getTarget();
        if (this.stage != CrossbowAttackGoal.Stage.UNCHARGED && target != null) {

            this.actor.lookAtEntity(target, 30.0F, 30.0F);
            boolean backward = false;

            double d = this.actor.squaredDistanceTo(target.getX(), target.getY(), target.getZ());
            if (d < this.squaredRange * 0.5D) {
                backward = true;
            }

            if ((double) this.actor.getRandom().nextFloat() < 0.1F) {
                this.movingLeft = !this.movingLeft;
            }

            this.actor.getMoveControl().strafeTo(backward ? -0.5F : 0.5F, this.movingLeft ? 0.5F : -0.5F);
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/intprovider/UniformIntProvider;get(Lnet/minecraft/util/math/random/Random;)I"))
    public void nox$crossbowPrioritizeCharging(CallbackInfo ci) {
        if (this.stage == CrossbowAttackGoal.Stage.UNCHARGED) {
            this.actor.setCurrentHand(ProjectileUtil.getHandPossiblyHolding(this.actor, Items.CROSSBOW));
            this.stage = CrossbowAttackGoal.Stage.CHARGING;
            ((CrossbowUser) this.actor).setCharging(true);
        }
    }

}
