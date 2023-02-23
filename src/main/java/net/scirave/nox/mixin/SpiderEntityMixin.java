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

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AvoidSunlightGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.math.BlockPos;
import net.scirave.nox.Nox;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin extends HostileEntityMixin {

    @Inject(method = "initGoals", at = @At("HEAD"))
    public void nox$spiderInitGoals(CallbackInfo ci) {
        this.goalSelector.add(1, new AvoidSunlightGoal((SpiderEntity) (Object) this));
    }

    @Override
    public void nox$onSuccessfulAttack(LivingEntity target) {
        if (NoxConfig.spiderAttacksPlaceWebs && this.getType().getWidth() >= EntityType.CAVE_SPIDER.getWidth()) {
            BlockPos pos = target.getBlockPos();
            if (this.world.getBlockState(pos).getMaterial().isReplaceable())
                this.world.setBlockState(pos, Nox.NOX_COBWEB.getDefaultState());
        }
    }

    @Override
    public void nox$shouldTakeDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        super.nox$shouldTakeDamage(source, amount, cir);
        if (source.getName().equals("fall")) {
            cir.setReturnValue(!NoxConfig.spidersImmuneToFallDamage);
        }
    }

}
