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

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow
    public World world;
    @Shadow
    public boolean noClip;

    @Shadow
    public abstract BlockPos getBlockPos();

    @Shadow
    public abstract Box getBoundingBox();

    @Shadow
    public abstract Vec3d getPos();

    @Shadow
    public abstract World getWorld();

    @Shadow
    public abstract boolean isAlive();

    @Shadow
    public abstract double getX();

    @Shadow
    public abstract double getY();

    @Shadow
    public abstract double getZ();

    @Shadow
    public abstract EntityType<?> getType();

    @Inject(method = "isInvulnerableTo", at = @At("HEAD"), cancellable = true)
    public void nox$invulnerableCheck(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        // Overridden
    }

}
