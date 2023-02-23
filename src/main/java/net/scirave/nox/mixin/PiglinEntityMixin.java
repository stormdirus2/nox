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

import com.google.common.collect.ImmutableList;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.mob.AbstractPiglinEntity;
import net.minecraft.entity.mob.PiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.List;

@Mixin(PiglinEntity.class)
public abstract class PiglinEntityMixin extends HostileEntityMixin {

    @Shadow
    public abstract Brain<PiglinEntity> getBrain();

    @Override
    public void nox$maybeAngerOnShove(PlayerEntity player) {
        super.nox$maybeAngerOnShove(player);
        this.getBrain().remember(MemoryModuleType.ANGRY_AT, player.getUuid(), 600L);
        List<AbstractPiglinEntity> piglins = this.getBrain().getOptionalMemory(MemoryModuleType.NEARBY_ADULT_PIGLINS).orElse(ImmutableList.of());
        piglins.forEach((piglin) -> {
            if (piglin.getTarget() == null) {
                piglin.setTarget(player);
                piglin.getBrain().remember(MemoryModuleType.ANGRY_AT, player.getUuid(), 600L);
            }
        });
    }

}
