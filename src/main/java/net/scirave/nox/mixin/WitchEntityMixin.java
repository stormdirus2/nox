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

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.FleeEntityGoal;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(WitchEntity.class)
public abstract class WitchEntityMixin extends HostileEntityMixin {

    @Shadow
    public abstract boolean isDrinking();

    @Inject(method = "initGoals", at = @At("TAIL"))
    public void nox$witchDrinkingFlee(CallbackInfo ci) {
        this.goalSelector.add(1, new FleeEntityGoal((WitchEntity) (Object) this, LivingEntity.class, 4.0F, 1.2D, 1.5D, (living) -> {
            if (!this.isDrinking()) return false;

            if (living instanceof PlayerEntity) {
                return true;
            } else if (living instanceof MobEntity mob) {
                return mob.getTarget() == (Object) this;
            }
            return false;
        }));
    }

    @ModifyArgs(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/ProjectileAttackGoal;<init>(Lnet/minecraft/entity/ai/RangedAttackMob;DIF)V"))
    public void nox$witchFasterAttack(Args args) {
        args.set(2, MathHelper.ceil((int) args.get(2) * 0.75));
    }

    @ModifyArg(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionUtil;setPotion(Lnet/minecraft/item/ItemStack;Lnet/minecraft/potion/Potion;)Lnet/minecraft/item/ItemStack;"))
    public Potion nox$witchUpgradedPotions(Potion original) {

        if (Potions.WATER_BREATHING.equals(original)) {
            return Potions.LONG_WATER_BREATHING;
        } else if (Potions.FIRE_RESISTANCE.equals(original)) {
            return Potions.LONG_FIRE_RESISTANCE;
        } else if (Potions.HEALING.equals(original)) {
            return Potions.STRONG_HEALING;
        } else if (Potions.SWIFTNESS.equals(original)) {
            return Potions.STRONG_SWIFTNESS;
        }

        return original;
    }

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeInstance;addTemporaryModifier(Lnet/minecraft/entity/attribute/EntityAttributeModifier;)V"))
    public void nox$witchNoDrinkingSlowdown(EntityAttributeInstance instance, EntityAttributeModifier modifier) {
        // No slowdown!
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionUtil;setPotion(Lnet/minecraft/item/ItemStack;Lnet/minecraft/potion/Potion;)Lnet/minecraft/item/ItemStack;"))
    public ItemStack nox$witchLingeringPotions(ItemStack original) {
        return new ItemStack(Items.LINGERING_POTION);
    }

    @ModifyArg(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/potion/PotionUtil;setPotion(Lnet/minecraft/item/ItemStack;Lnet/minecraft/potion/Potion;)Lnet/minecraft/item/ItemStack;"))
    public Potion nox$witchUpgradedSlowness(Potion original) {
        if (Potions.SLOWNESS.equals(original)) {
            return Potions.STRONG_SLOWNESS;
        }
        return original;
    }

    @Override
    public void nox$invulnerableCheck(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        super.nox$invulnerableCheck(source, cir);
        if (source.isMagic()) {
            cir.setReturnValue(true);
        }
    }

    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/WitchEntity;isDrinking()Z"))
    public boolean nox$witchDrinkWhileAttack(WitchEntity instance) {
        return false;
    }

    @ModifyArgs(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/projectile/thrown/PotionEntity;setVelocity(DDDFF)V"))
    public void nox$witchBetterAim(Args args) {
        args.set(1, (double) args.get(1) * 0.50);
        args.set(3, (float) ((float) args.get(3) + 0.25));
        args.set(4, (float) args.get(4) / 4);
    }

}
