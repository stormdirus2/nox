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

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(DefaultBiomeFeatures.class)
public class DefaultBiomeFeaturesMixin {

    @ModifyArgs(method = "addMonsters", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/SpawnSettings$SpawnEntry;<init>(Lnet/minecraft/entity/EntityType;III)V", ordinal = 7))
    private static void nox$witchIncreasedSpawn(Args args) {
        args.set(1, ((int) args.get(1)) * 2);
        args.set(3, ((int) args.get(3)) * 3);
    }

    @ModifyArgs(method = "addMonsters", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/SpawnSettings$SpawnEntry;<init>(Lnet/minecraft/entity/EntityType;III)V", ordinal = 5))
    private static void nox$slimeDecreasedSpawn(Args args) {
        args.set(1, ((int) args.get(1))/2);
        args.set(2, ((int) args.get(2))/2);
    }

    @ModifyArgs(method = "addOceanMobs", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/SpawnSettings$SpawnEntry;<init>(Lnet/minecraft/entity/EntityType;III)V", ordinal = 2))
    private static void nox$drownedIncreasedSpawn1(Args args) {
        args.set(1, ((int) args.get(1)) * 2);
        args.set(2, ((int) args.get(2)) * 4);
        args.set(3, ((int) args.get(3)) * 4);
    }

    @ModifyArgs(method = "addWarmOceanMobs", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/SpawnSettings$SpawnEntry;<init>(Lnet/minecraft/entity/EntityType;III)V", ordinal = 3))
    private static void nox$drownedIncreasedSpawn2(Args args) {
        args.set(1, ((int) args.get(1)) * 2);
        args.set(2, ((int) args.get(2)) * 4);
        args.set(3, ((int) args.get(3)) * 4);
    }

    @Inject(method = "addCaveMobs", at = @At("TAIL"))
    private static void nox$caveSpiderSpawns(SpawnSettings.Builder builder, CallbackInfo ci) {
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.CAVE_SPIDER, 80, 4, 4));
    }

    @Inject(method = "addOceanMobs", at = @At("TAIL"))
    private static void nox$guardianSpawns1(SpawnSettings.Builder builder, int squidWeight, int squidMaxGroupSize, int codWeight, CallbackInfo ci) {
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.GUARDIAN, 10, 4, 4));
    }

    @Inject(method = "addWarmOceanMobs", at = @At("TAIL"))
    private static void nox$guardianSpawns2(SpawnSettings.Builder builder, int squidWeight, int squidMinGroupSize, CallbackInfo ci) {
        builder.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.GUARDIAN, 10, 4, 4));
    }

}
