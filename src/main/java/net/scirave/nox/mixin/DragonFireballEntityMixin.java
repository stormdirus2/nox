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

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.ExplosiveProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DragonFireballEntity.class)
public abstract class DragonFireballEntityMixin extends ExplosiveProjectileEntity {

    protected DragonFireballEntityMixin(EntityType<? extends ExplosiveProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onCollision", at = @At("HEAD"), cancellable = true)
    public void nox$enderDragonLessFireballCollision(HitResult hitResult, CallbackInfo ci) {
        if (hitResult.getType() == HitResult.Type.ENTITY && (((EntityHitResult) hitResult).getEntity() instanceof EnderDragonPart)) {
            ci.cancel();
        }
    }

    @ModifyArg(method = "onCollision", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/World;spawnEntity(Lnet/minecraft/entity/Entity;)Z"))
    public Entity nox$enderDragonAdjustedFireball(Entity entity) {
        if (NoxConfig.buffEnderDragonFireball && this.getOwner() instanceof EnderDragonEntity && entity instanceof AreaEffectCloudEntity cloud) {
            cloud.setDuration(NoxConfig.enderDragonBreathDuration);
            cloud.setRadius(NoxConfig.enderDragonBreathRadius);
            cloud.setWaitTime(cloud.getWaitTime() / 2);
            cloud.setRadiusGrowth(0);
            cloud.addEffect(new StatusEffectInstance(StatusEffects.INSTANT_DAMAGE, 1, 2));
        }
        return entity;
    }

}
