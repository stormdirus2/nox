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
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.CaveSpiderEntity;
import net.minecraft.util.math.BlockPos;
import net.scirave.nox.Nox;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(CaveSpiderEntity.class)
public abstract class CaveSpiderEntityMixin extends SpiderEntityMixin {

    @Override
    public void nox$onSuccessfulAttack(LivingEntity target) {
        if (Nox.CONFIG.caveSpiderAttacksPlaceWebs) {
            BlockPos pos = target.getBlockPos();
            if (this.world.getBlockState(pos).getMaterial().isReplaceable() && this.world.setBlockState(pos, nox$COBWEB))
                this.nox$addTrackedWeb(pos, target, 0);
        }
        if (Nox.CONFIG.caveSpidersApplySlowness)
            target.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 200, 2), (CaveSpiderEntity) (Object) this);
    }

}
