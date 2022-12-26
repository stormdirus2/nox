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

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.ZombifiedPiglinEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.scirave.nox.config.NoxConfig;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ZombifiedPiglinEntity.class)
public abstract class ZombifiedPiglinEntityMixin extends HostileEntityMixin {

    @Shadow
    public abstract void setTarget(@Nullable LivingEntity target);

    @Shadow
    protected abstract void angerNearbyZombifiedPiglins();

    @Override
    public void nox$maybeAngerOnShove(PlayerEntity player) {
        super.nox$maybeAngerOnShove(player);
        this.angerNearbyZombifiedPiglins();
    }

    @Override
    public boolean nox$isAllowedToMine() {
        return NoxConfig.zombifiedPiglinsMineBlocks;
    }

}
