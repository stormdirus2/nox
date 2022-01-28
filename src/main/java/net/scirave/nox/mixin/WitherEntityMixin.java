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
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.boss.WitherEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WitherSkeletonEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameRules;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.scirave.nox.Nox;
import net.scirave.nox.util.NoxUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WitherEntity.class)
public abstract class WitherEntityMixin extends HostileEntityMixin {

    @Shadow
    private int blockBreakingCooldown;

    private int nox$reinforcementsCooldown = Nox.CONFIG.witherCallReinforcementsCooldown;

    @ModifyArg(method = "createWitherAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", ordinal = 0))
    private static double nox$witherMoreHealth(double original) {
        if (Nox.CONFIG.witherBaseHealthMultiplier > 1)
            return original * Nox.CONFIG.witherBaseHealthMultiplier;
        return original;
    }

    @ModifyArg(method = "createWitherAttributes", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;add(Lnet/minecraft/entity/attribute/EntityAttribute;D)Lnet/minecraft/entity/attribute/DefaultAttributeContainer$Builder;", ordinal = 3))
    private static double nox$witherMoreRange(double original) {
        if (Nox.CONFIG.witherFollowRangeMultiplier > 1)
            return original * Nox.CONFIG.witherFollowRangeMultiplier;
        return original;
    }

    private void nox$witherBreakBlocks() {
        if (!this.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) return;
        Box box = this.getBoundingBox().expand(1, 0, 1);

        int i = MathHelper.floor(box.minX);
        int j = MathHelper.floor(box.minY);
        int k = MathHelper.floor(box.minZ);
        int l = MathHelper.floor(box.maxX);
        int m = MathHelper.floor(box.maxY);
        int n = MathHelper.floor(box.maxZ);
        boolean bl = false;

        for (int o = i; o <= l; ++o) {
            for (int p = j; p <= m; ++p) {
                for (int q = k; q <= n; ++q) {
                    BlockPos blockPos = new BlockPos(o, p, q);
                    BlockState blockState = this.world.getBlockState(blockPos);
                    if (!blockState.isAir() && !blockState.isIn(BlockTags.WITHER_IMMUNE)) {
                        if (NoxUtil.isAtWoodLevel(blockState)) {
                            bl = this.world.removeBlock(blockPos, false) || bl;
                        } else {
                            bl = this.world.breakBlock(blockPos, true, (WitherEntity) (Object) this) || bl;
                        }
                    }
                }
            }
        }

        if (bl) {
            this.world.syncWorldEvent(null, 1022, this.getBlockPos(), 0);
        }

    }

    @Inject(method = "onSummoned", at = @At("TAIL"))
    private void nox$onSummoned(CallbackInfo ci) {
        this.setHealth(this.getMaxHealth());
    }

    @Inject(method = "mobTick", at = @At("HEAD"))
    public void nox$witherNoVanillaBreak(CallbackInfo ci) {
        if (Nox.CONFIG.witherRapidlyBreaksSurroundingBlocks)
            this.blockBreakingCooldown = 20;
    }

    @Inject(method = "mobTick", at = @At("TAIL"))
    public void nox$witherBetterBreak(CallbackInfo ci) {
        if (Nox.CONFIG.witherRapidlyBreaksSurroundingBlocks)
            nox$witherBreakBlocks();
    }

    @Override
    public void nox$onTick(CallbackInfo ci) {
        LivingEntity target = this.getTarget();
        if (this.getWorld() instanceof ServerWorld serverWorld) {
            if (nox$reinforcementsCooldown > 0) {
                nox$reinforcementsCooldown--;
            } else if (target != null && target.squaredDistanceTo((WitherEntity) (Object) this) <= MathHelper.square(Nox.CONFIG.witherReinforcementsTriggerRadius)) {
                nox$reinforcementsCooldown = Nox.CONFIG.witherCallReinforcementsCooldown;
                for (int i = 0; i < Nox.CONFIG.witherReinforcementsGroupSize; i++) {
                    WitherSkeletonEntity skeleton = EntityType.WITHER_SKELETON.create(serverWorld);
                    if (skeleton != null) {
                        skeleton.setPos(this.getX() + this.getRandom().nextInt(-2, 2), this.getY(), this.getZ() + this.getRandom().nextInt(-2, 2));
                        skeleton.initialize(serverWorld, this.world.getLocalDifficulty(skeleton.getBlockPos()), SpawnReason.REINFORCEMENT, null, null);
                        serverWorld.spawnEntityAndPassengers(skeleton);
                        skeleton.setTarget(target);
                        skeleton.playSpawnEffects();
                    }
                }
            }
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
