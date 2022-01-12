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
    @Comment("[Global] (Certain) mobs can break blocks")
    public boolean mobsBreakBlocks = true;

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
    @Comment("[Mobs] Creepers breach walls")
    public boolean creepersBreachWalls = true;

    @Override
    public String getName() {
        return "Nox Config";
    }

    @Override
    public @Nullable String getModid() {
        return Nox.MOD_ID;
    }

}