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

package net.scirave.nox.util;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import java.util.Map;

public interface Nox$WebManagerInterface {

    Map<RegistryKey<World>, Map<BlockPos, Nox$WebManagerInterface>> nox$WEB_MAPS_BY_DIM = new Object2ObjectOpenHashMap<>();
    BlockState nox$COBWEB = Blocks.COBWEB.getDefaultState();
    int nox$MAX_WEBS_PER_SPIDER = 5;
    int nox$MAX_WEB_AGE = 400; // How long (in ticks) before Spider-placed Cobwebs can be removed from the World

    static Nox$WebManagerInterface nox$getWebOwner(World world, BlockPos pos) {
        Map<BlockPos, Nox$WebManagerInterface> worldWideWebs = nox$WEB_MAPS_BY_DIM.get(world.getRegistryKey());
        if (worldWideWebs == null)
            return null;
        return worldWideWebs.get(pos);
    }

    Map<BlockPos, Nox$WebManagerInterface> nox$getWorldWebMap();

    void nox$flagForRemoval(BlockPos pos);

}
