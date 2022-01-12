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

package net.scirave.nox.config;

import draylar.omegaconfig.api.Comment;
import draylar.omegaconfig.api.Config;
import net.scirave.nox.Nox;
import org.jetbrains.annotations.Nullable;

public class NoxConfig implements Config {

    // Global
    @Comment("[Global] Enable mob friendly fire")
    public boolean friendlyFire = false;
    @Comment("[Global] Mobs see through walls")
    public boolean mobXray = true;
    @Comment("[Global] Baseline Monster buffs")
    public boolean buffAllMonsters = true;
    @Comment("[Global] Baseline Golem buffs")
    public boolean buffAllGolems = true;

    // Block Breaking
    @Comment("[Block Breaking] Max block hardness mineable")
    public float maxBlockHardnessMineableByMobs = 3.0F;
    @Comment("[Block Breaking] Endermen can break blocks")
    public boolean endermenBreakBlocks = true;
    @Comment("[Block Breaking] Piglins can break blocks")
    public boolean piglinsBreakBlocks = true;
    @Comment("[Block Breaking] Pillagers can break blocks")
    public boolean pillagersBreakBlocks = true;
    @Comment("[Block Breaking] Vindicators can break blocks")
    public boolean vindicatorsBreakBlocks = true;
    @Comment("[Block Breaking] Wither Skeletons can break blocks")
    public boolean witherSkeletonsBreakBlocks = true;
    @Comment("[Block Breaking] Zombies can break blocks")
    public boolean zombiesBreakBlocks = true;

    // Sleep
    @Comment("[Sleep] Monsters detected glow")
    public boolean sleepApplyGlowing = true;
    @Comment("[Sleep] Check expands to reach sea level")
    public boolean sleepExtendToSeaLevel = true;
    @Comment("[Sleep] Horizontal search distance")
    public int sleepHorizontalSearchDistance = 50;
    @Comment("[Sleep] Minimum vertical search distance")
    public int sleepMinVerticalSearchDistance = 20;

    // Mobs
    @Comment("[Mobs] Blazes ignite nearby targets")
    public boolean blazesIgniteNearbyTargets = true;
    @Comment("[Mobs] Blaze ignition radius")
    public int blazeIgnitionRadius = 2;
    @Comment("[Mobs] Creepers breach walls")
    public boolean creepersBreachWalls = true;
    @Comment("[Mobs] Spider attacks place webs")
    public boolean spiderAttacksPlaceWebs = true;

    @Override
    public String getName() {
        return "Nox Config";
    }

    @Override
    public @Nullable String getModid() {
        return Nox.MOD_ID;
    }

}