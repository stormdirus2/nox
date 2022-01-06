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

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AvoidSunlightGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin extends HostileEntityMixin {

    private static final BlockState COBWEB = Blocks.COBWEB.getDefaultState();

    @Inject(method = "initGoals", at = @At("HEAD"))
    public void nox$spiderAvoidSunlight(CallbackInfo ci) {
        this.goalSelector.add(1, new AvoidSunlightGoal((SpiderEntity) (Object) this));
    }

    @Override
    public void nox$onAttack(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValue() && target instanceof LivingEntity living) {
            BlockPos pos = living.getBlockPos();
            if (this.world.getBlockState(pos).getMaterial().isReplaceable()) {
                this.world.setBlockState(pos, COBWEB);
            }
        }
    }

    @Override
    public void nox$invulnerableCheck(DamageSource damageSource, CallbackInfoReturnable<Boolean> cir) {
        super.nox$invulnerableCheck(damageSource, cir);
        if (damageSource.getName().equals("fall")) {
            cir.setReturnValue(true);
        }
    }
}
