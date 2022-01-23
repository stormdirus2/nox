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

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArraySet;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AvoidSunlightGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.SpiderEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIntArray;
import net.minecraft.nbt.NbtList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.scirave.nox.Nox;
import net.scirave.nox.util.Nox$WebManagerInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(SpiderEntity.class)
public abstract class SpiderEntityMixin extends HostileEntityMixin implements Nox$WebManagerInterface {

    private static final String nox$TRACKED_WEBS_NBT_KEY = "nox:tracked_webs";

    private Object2IntOpenHashMap<BlockPos> nox$webToAgeMap;

    // TL;DR this Set is a lazy way to not have to worry about multiple methods messing with Cobweb removal logic.
    private ObjectArraySet<BlockPos> nox$flaggedForRemoval;

    @Inject(method = "initGoals", at = @At("HEAD"))
    public void nox$spiderInitGoals(CallbackInfo ci) {
        this.goalSelector.add(1, new AvoidSunlightGoal((SpiderEntity) (Object) this));
    }

    @Override
    protected void nox$readCustomDataFromNbt(NbtCompound nbt, CallbackInfo ci) {
        NbtList nbtWebList = (NbtList) nbt.get(nox$TRACKED_WEBS_NBT_KEY);
        if (nbtWebList != null)
            for (int i = 0; i < nbtWebList.size(); i++) {
                int[] webData = nbtWebList.getIntArray(i);
                BlockPos pos = new BlockPos(webData[0], webData[1], webData[2]);
                BlockState state = this.world.getBlockState(pos);
                if (state == nox$COBWEB || (state.getMaterial().isReplaceable() && this.world.setBlockState(pos, nox$COBWEB)))
                    this.nox$addTrackedWeb(pos, null, webData[3]);
            }
    }

    @Override
    protected void nox$writeCustomDataToNbt(NbtCompound nbt, CallbackInfo ci) {
        if (this.nox$webToAgeMap != null && this.nox$webToAgeMap.size() > 0) {
            NbtList nbtWebList = new NbtList();
            for (BlockPos web : this.nox$webToAgeMap.keySet())
                nbtWebList.add(new NbtIntArray(new int[]{web.getX(), web.getY(), web.getZ(), this.nox$getWebAge(web)}));
            nbt.put(nox$TRACKED_WEBS_NBT_KEY, nbtWebList);
        }
    }

    protected void nox$addTrackedWeb(BlockPos pos, LivingEntity target, int age) {
        if (this.nox$webToAgeMap == null)
            this.nox$webToAgeMap = new Object2IntOpenHashMap<>();
        if (target == null)
            while (this.nox$webToAgeMap.size() >= nox$MAX_WEBS_PER_SPIDER)
                this.nox$removeTrackedWeb(this.nox$getOldestWeb());
        else
            while (this.nox$webToAgeMap.size() >= nox$MAX_WEBS_PER_SPIDER)
                this.nox$removeTrackedWeb(this.nox$getFarthestWebFromTarget(target));
        this.nox$webToAgeMap.put(pos, age);
        this.nox$getWorldWebMap().put(pos, this);
    }

    private BlockPos nox$getOldestWeb() {
        BlockPos oldestWeb = null;
        int age, greatestAge = -1;
        for (BlockPos web : this.nox$webToAgeMap.keySet())
            if (oldestWeb == null)
                greatestAge = this.nox$getWebAge(oldestWeb = web);
            else if ((age = this.nox$getWebAge(web)) > greatestAge) {
                greatestAge = age;
                oldestWeb = web;
            }
        return oldestWeb;
    }

    private BlockPos nox$getFarthestWebFromTarget(LivingEntity target) {
        BlockPos farthestWeb = null;
        double sqDist, greatestSquaredDistance = -1;
        for (BlockPos web : this.nox$webToAgeMap.keySet())
            if (farthestWeb == null) {
                greatestSquaredDistance = target.squaredDistanceTo(web.getX(), web.getY(), web.getZ());
                farthestWeb = web;
            } else if ((sqDist = target.squaredDistanceTo(web.getX(), web.getY(), web.getZ())) > greatestSquaredDistance) {
                greatestSquaredDistance = sqDist;
                farthestWeb = web;
            }
        return farthestWeb;
    }

    private void nox$removeTrackedWeb(BlockPos pos) {
        if (this.world.getBlockState(pos) == nox$COBWEB)
            this.world.removeBlock(pos, false);
        this.nox$softRemoveTrackedWeb(pos);
    }

    private void nox$softRemoveTrackedWeb(BlockPos pos) {
        // Removes Cobweb BlockPos from Maps without removing it from the world
        this.nox$webToAgeMap.remove(pos, this.nox$getWebAge(pos));
        this.nox$getWorldWebMap().remove(pos);
        if (this.nox$flaggedForRemoval != null)
            this.nox$flaggedForRemoval.remove(pos);
    }

    @Override
    public void nox$flagForRemoval(BlockPos pos) {
        if (this.nox$flaggedForRemoval == null)
            this.nox$flaggedForRemoval = new ObjectArraySet<>();
        this.nox$flaggedForRemoval.add(pos);
    }

    @Override
    public void nox$onSuccessfulAttack(LivingEntity target) {
        if (Nox.CONFIG.spiderAttacksPlaceWebs) {
            BlockPos pos = target.getBlockPos();
            if (this.world.getBlockState(pos).getMaterial().isReplaceable() && this.world.setBlockState(pos, nox$COBWEB))
                this.nox$addTrackedWeb(pos, target, 0);
        }
    }

    @Override
    public void nox$onTick(CallbackInfo ci) {
        if (this.nox$webToAgeMap != null)
            for (BlockPos webPos : this.nox$webToAgeMap.keySet()) {
                if (this.nox$flaggedForRemoval != null && this.nox$flaggedForRemoval.contains(webPos))
                    this.nox$softRemoveTrackedWeb(webPos);
                else {
                    int webAge = this.nox$getWebAge(webPos);
                    if (webAge > nox$MAX_WEB_AGE) {
                        if (this.world.getOtherEntities(null, new Box(webPos).expand(0.3)).isEmpty())
                            this.nox$removeTrackedWeb(webPos);
                    } else this.nox$webToAgeMap.replace(webPos, (webAge + 1));
                }
            }
    }

    @Override
    protected void nox$onSetRemoved(Entity.RemovalReason reason, CallbackInfo ci) {
        // When the Spider is removed from the world for any reason, remove all of its webs.
        if (this.nox$webToAgeMap != null)
            for (BlockPos web : this.nox$webToAgeMap.keySet()) {
                if (this.world.getBlockState(web) == nox$COBWEB && (this.nox$flaggedForRemoval == null || !this.nox$flaggedForRemoval.contains(web)))
                    this.world.removeBlock(web, false);

                if (reason.shouldSave()) {
                    if (this.nox$flaggedForRemoval != null)
                        this.nox$flaggedForRemoval.remove(web);
                    this.nox$getWorldWebMap().remove(web);
                    this.world.removeBlock(web, false);
                } else
                    this.nox$softRemoveTrackedWeb(web);
            }
    }

    private int nox$getWebAge(BlockPos pos) {
        return this.nox$webToAgeMap.getInt(pos);
    }

    @Override
    public Map<BlockPos, Nox$WebManagerInterface> nox$getWorldWebMap() {
        Map<BlockPos, Nox$WebManagerInterface> worldWideWebs = nox$WEB_MAPS_BY_DIM.get(this.world.getRegistryKey());
        if (worldWideWebs == null)
            nox$WEB_MAPS_BY_DIM.put(this.world.getRegistryKey(), worldWideWebs = new Object2ObjectOpenHashMap<>());
        return worldWideWebs;
    }

    @Override
    public void nox$invulnerableCheck(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        super.nox$invulnerableCheck(source, cir);
        if (source.getName().equals("fall")) {
            cir.setReturnValue(Nox.CONFIG.spidersImmuneToFallDamage);
        }
    }

}
