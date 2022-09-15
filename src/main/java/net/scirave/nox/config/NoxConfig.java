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

import eu.midnightdust.lib.config.MidnightConfig;

public class NoxConfig extends MidnightConfig {

    // Global
    @Entry
    public static boolean noFriendlyFire = true;
    @Entry
    public static boolean mobXray = true;
    @Entry
    public static double monsterRangeMultiplier = 1.5;
    @Entry
    public static boolean monsterGearScales = true;
    @Entry
    public static boolean monsterAngerOnShove = true;
    @Entry
    public static boolean buffGolems = true;
    @Entry
    public static boolean mobsBreakBlocks = true;

    // Block breaking
    @Entry
    public static float blockBreakingHardnessCutoff = 3.0F;

    // Sleep
    @Entry
    public static boolean sleepApplyGlowing = true;
    @Entry
    public static boolean sleepExtendToSeaLevel = true;
    @Entry
    public static int sleepHorizontalSearchDistance = 50;
    @Entry
    public static int sleepMinVerticalSearchDistance = 20;

    // Mobs
    @Entry
    public static boolean creepersBreachWalls = true;
    @Entry
    public static boolean creepersRunFromShields = true;
    @Entry
    public static boolean endermiteFlowerSpawn = true;
}