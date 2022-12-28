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
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.scirave.nox.config.NoxConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuardianEntity.class)
public abstract class GuardianEntityMixin extends HostileEntityMixin {
    //AeiouEnigma's work
    private static final BlockState nox$WATER = Blocks.WATER.getDefaultState();
    private static final BlockState nox$FLOWING_WATER = nox$WATER.with(Properties.LEVEL_15, 8);
    private static final BlockState nox$SMALL_WATER = nox$WATER.with(Properties.LEVEL_15, 7);

    @Override
    public void nox$onDamaged(DamageSource source, float amount, CallbackInfo ci) {
        if (NoxConfig.guardiansWaterOnDeath && !this.world.isClient) {
            BlockPos pos = this.getBlockPos();
            BlockState state = this.world.getBlockState(pos);
            if (state != nox$WATER && state.getMaterial().equals(Material.AIR)) {
                if (NoxConfig.guardiansWaterSourceOnDeath) {
                    this.world.setBlockState(pos, nox$WATER);
                } else {
                    // (Comment from AeiouEnigma): order matters
                    state = this.world.getBlockState(pos.up());
                    this.world.setBlockState(pos, nox$FLOWING_WATER);
                    if (state != nox$WATER && state.getMaterial().isReplaceable())
                        this.world.setBlockState(pos.up(), nox$SMALL_WATER);
                }
            }
        }
    }
}
