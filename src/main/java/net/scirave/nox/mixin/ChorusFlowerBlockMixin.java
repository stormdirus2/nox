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

import net.minecraft.block.BlockState;
import net.minecraft.block.ChorusFlowerBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChorusFlowerBlock.class)
public class ChorusFlowerBlockMixin extends AbstractBlockMixin {

    @Override
    public void nox$onBlockReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved, CallbackInfo c) {
        if (!moved && world instanceof ServerWorld serverWorld && NoxConfig.endermiteFlowerSpawn && world.random.nextBetween(1, 3) == 3) {
            EndermiteEntity endermite = EntityType.ENDERMITE.create(world);
            if (endermite != null) {
                endermite.refreshPositionAndAngles((double) pos.getX() + 0.5, pos.getY(), (double) pos.getZ() + 0.5, 0.0F, 0.0F);
                world.spawnEntity(endermite);
                endermite.playSpawnEffects();

                endermite.initialize(serverWorld, world.getLocalDifficulty(pos), SpawnReason.TRIGGERED, null, null);
            }
        }
    }
}
