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

import net.minecraft.entity.EntityData;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.gen.feature.EndPortalFeature;
import net.scirave.nox.Nox;
import net.scirave.nox.util.NoxUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EnderDragonEntity.class)
public abstract class EnderDragonEntityMixin extends MobEntityMixin {

    private static final TargetPredicate nox$RANGE_PREDICATE = TargetPredicate.createAttackable();
    private int nox$fireballCooldown = 0;

    @ModifyArg(method = "createEnderDragonAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", ordinal = 0))
    private static double nox$enderDragonMoreHealth(double original) {
        if (Nox.CONFIG.enderDragonBaseHealthMultiplier > 1)
            return original * Nox.CONFIG.enderDragonBaseHealthMultiplier;
        return original;
    }

    @Override
    public void nox$invulnerableCheck(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        super.nox$invulnerableCheck(source, cir);
        if (source.isExplosive() && Nox.CONFIG.enderDragonIsImmuneToExplosionDamage) {
            cir.setReturnValue(true);
        }
    }

    @Override
    public void nox$onTick(CallbackInfo ci) {
        if (nox$fireballCooldown <= 0) {
            BlockPos blockPos = this.world.getTopPosition(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, new BlockPos(EndPortalFeature.ORIGIN));
            PlayerEntity player = this.world.getClosestPlayer(nox$RANGE_PREDICATE, (EnderDragonEntity) (Object) this, blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (player != null && player.squaredDistanceTo((EnderDragonEntity) (Object) this) >= 49.0D && this.canSee(player)) {
                nox$fireballCooldown = Nox.CONFIG.enderDragonFireballCooldown;
                NoxUtil.EnderDragonShootFireball((EnderDragonEntity) (Object) this, player);
            }
        } else {
            nox$fireballCooldown--;
        }

    }

    @Override
    public void nox$modifyAttributes(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        //Non-applicable
    }

    @Override
    public void nox$hostileAttributes(MobEntity mob) {
        //Non-applicable
    }

}
