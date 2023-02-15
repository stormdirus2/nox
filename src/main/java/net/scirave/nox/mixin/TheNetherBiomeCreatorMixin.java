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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.biome.TheNetherBiomeCreator;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TheNetherBiomeCreator.class)
public class TheNetherBiomeCreatorMixin {

    @Redirect(method = "createNetherWastes", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/SpawnSettings$Builder;spawn(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/world/biome/SpawnSettings$SpawnEntry;)Lnet/minecraft/world/biome/SpawnSettings$Builder;", ordinal = 0))
    private static SpawnSettings.Builder nox$adjustNetherWastesSpawns(SpawnSettings.Builder instance, SpawnGroup spawnGroup, SpawnSettings.SpawnEntry spawnEntry) {
        if (NoxConfig.blazeNaturalSpawn) {
            instance.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.BLAZE, 10, 2, 3));
        }
        return instance.spawn(spawnGroup, new SpawnSettings.SpawnEntry(EntityType.GHAST, MathHelper.ceil(spawnEntry.getWeight().getValue() * 1.5), spawnEntry.minGroupSize, spawnEntry.maxGroupSize));
    }

    @Redirect(method = "createBasaltDeltas", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/SpawnSettings$Builder;spawn(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/world/biome/SpawnSettings$SpawnEntry;)Lnet/minecraft/world/biome/SpawnSettings$Builder;", ordinal = 0))
    private static SpawnSettings.Builder nox$adjustBasaltDeltasSpawns(SpawnSettings.Builder instance, SpawnGroup spawnGroup, SpawnSettings.SpawnEntry spawnEntry) {
        if (NoxConfig.blazeNaturalSpawn) {
            instance.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.BLAZE, 20, 1, 3));
        }
        return instance.spawn(spawnGroup, new SpawnSettings.SpawnEntry(EntityType.GHAST, MathHelper.ceil(spawnEntry.getWeight().getValue() * 1.5), spawnEntry.minGroupSize, spawnEntry.maxGroupSize * 4));
    }

    @Redirect(method = "createCrimsonForest", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/SpawnSettings$Builder;spawn(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/world/biome/SpawnSettings$SpawnEntry;)Lnet/minecraft/world/biome/SpawnSettings$Builder;", ordinal = 0))
    private static SpawnSettings.Builder nox$adjustCrimsonForestSpawns(SpawnSettings.Builder instance, SpawnGroup spawnGroup, SpawnSettings.SpawnEntry spawnEntry) {
        if (NoxConfig.blazeNaturalSpawn) {
            instance.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.BLAZE, 3, 1, 3));
        }
        instance.spawn(spawnGroup, new SpawnSettings.SpawnEntry(EntityType.GHAST, 60, 1, 4));
        if (NoxConfig.spawnGhastsInMoreBiomes)
            instance.spawn(spawnGroup, new SpawnSettings.SpawnEntry(EntityType.GHAST, NoxConfig.increaseGhastSpawns ? 60 : 40, 1, 4));
        return instance.spawn(spawnGroup, spawnEntry);
    }

    @Redirect(method = "createWarpedForest", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/SpawnSettings$Builder;spawn(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/world/biome/SpawnSettings$SpawnEntry;)Lnet/minecraft/world/biome/SpawnSettings$Builder;", ordinal = 0))
    private static SpawnSettings.Builder nox$adjustWarpedForestSpawns(SpawnSettings.Builder instance, SpawnGroup spawnGroup, SpawnSettings.SpawnEntry spawnEntry) {
        if (NoxConfig.witherSkeletonsSpawnNaturallyInNether)
            instance.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.WITHER_SKELETON, 40, 1, 4));
        if (NoxConfig.spawnGhastsInMoreBiomes)
            instance.spawn(spawnGroup, new SpawnSettings.SpawnEntry(EntityType.GHAST, NoxConfig.increaseGhastSpawns ? 30 : 20, 1, 4));
        return instance.spawn(spawnGroup, new SpawnSettings.SpawnEntry(EntityType.ENDERMAN, 100, 4, 4));
    }

    @Redirect(method = "createSoulSandValley", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/SpawnSettings$Builder;spawn(Lnet/minecraft/entity/SpawnGroup;Lnet/minecraft/world/biome/SpawnSettings$SpawnEntry;)Lnet/minecraft/world/biome/SpawnSettings$Builder;", ordinal = 1))
    private static SpawnSettings.Builder nox$adjustSoulSandValleySpawns(SpawnSettings.Builder instance, SpawnGroup spawnGroup, SpawnSettings.SpawnEntry spawnEntry) {
        if (NoxConfig.witherSkeletonsSpawnNaturallyInNether)
            instance.spawn(SpawnGroup.MONSTER, new SpawnSettings.SpawnEntry(EntityType.WITHER_SKELETON, 5, 1, 4));
        return instance.spawn(spawnGroup, new SpawnSettings.SpawnEntry(EntityType.GHAST, MathHelper.ceil(spawnEntry.getWeight().getValue() * (NoxConfig.increaseGhastSpawns ? 1.5 : 1)), spawnEntry.minGroupSize, spawnEntry.maxGroupSize));
    }

}
