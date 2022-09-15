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

import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.scirave.nox.util.NoxUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemEntity.class)
public abstract class ItemEntityMixin {

    @Shadow
    public abstract ItemStack getStack();

    @Inject(method = "isFireImmune", at = @At("HEAD"), cancellable = true)
    public void nox$fireImmuneItems(CallbackInfoReturnable<Boolean> cir) {
        if (this.getStack().getItem().getRegistryEntry().isIn(NoxUtil.FIREPROOF)) {
            cir.setReturnValue(true);
        }
    }

}
