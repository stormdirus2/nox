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
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WitchEntity.class)
public abstract class WitchEntityMixin extends HostileEntityMixin {

    @Shadow
    public abstract boolean isDrinking();

    @Redirect(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/WitchEntity;isDrinking()Z"))
    public boolean nox$witchDontDrinkInRange(WitchEntity instance) {
        LivingEntity target = this.getTarget();
        if (target != null && target.squaredDistanceTo((WitchEntity) (Object) this) <= 49) {
            return true;
        }
        return this.isDrinking();
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
    public void nox$invulnerableCheck(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        super.nox$invulnerableCheck(damageSource, cir);
        if (damageSource.isMagic() || damageSource.isProjectile()) {
            cir.setReturnValue(true);
        }
    }

}
