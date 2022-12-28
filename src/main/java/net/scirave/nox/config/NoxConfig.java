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
    @Entry
    public static boolean piglinsMineBlocks = true;
    @Entry
    public static boolean endermenMineBlocks = true;
    @Entry
    public static boolean pillagersMineBlocks = true;
    @Entry
    public static boolean vindicatorsMineBlocks = true;
    @Entry
    public static boolean witherSkeletonsMineBlocks = true;
    @Entry
    public static boolean zombiesMineBlocks = true;
    @Entry
    public static boolean zombifiedPiglinsMineBlocks = true;
    @Entry
    public static int creeperBreachDistance = 7;

    //Placing
    @Entry
    public static boolean guardiansWaterOnDeath = true;
    @Entry
    public static boolean guardiansWaterSourceOnDeath = false;
    @Entry
    public static boolean magmaCubeLavaOnDeath = true;

    // Sleep
    @Entry
    public static boolean sleepApplyGlowing = true;
    @Entry
    public static boolean sleepExtendToSeaLevel = true;
    @Entry
    public static int sleepHorizontalSearchDistance = 50;
    @Entry
    public static int sleepMinVerticalSearchDistance = 20;

    // Spawning
    @Entry
    public static boolean slimeNaturalSpawn = true;
    @Entry
    public static boolean moreWitchSpawns = true;
    @Entry
    public static boolean moreDrownedSpawns = true;
    @Entry
    public static boolean caveSpidersInCaves = true;
    //from AeiouEnigma's pull request
    @Entry
    public static int freeRangeGuardiansSpawnWeight = 10;
    @Entry
    public static boolean blazeNaturalSpawn = true;
    @Entry
    public static boolean witherSkeletonsSpawnNaturally = true;
    @Entry
    public static boolean ghastsInWarpedForests = true;
    @Entry
    public static boolean endermiteFlowerSpawn = true;

    // Behavior
    @Entry
    public static boolean creepersRunFromShields = true;
    @Entry
    public static boolean creepersAttackShields = false;
    @Entry
    public static boolean endermenTeleportOnHit = true;
    @Entry
    public static boolean endermitesTeleportTargetOnHit = true;
    @Entry
    public static boolean phantomsNoClip = true;
    @Entry
    public static boolean piglinsRequireExclusivelyGoldArmor = true;
    @Entry
    public static boolean magmaCubesJumpConstantly = true;
    @Entry
    public static boolean slimesJumpConstantly = true;
    @Entry
    public static boolean spidersPlaceCobwebs = true;
    @Entry
    public static boolean witchesFleeToHeal = true;
    @Entry
    public static boolean witherSkeletonArchers = true;

    // Immunities
    @Entry
    public static boolean endermitesTakeFallDamage = false;
    @Entry
    public static boolean endermitesSuffocate = false;
    @Entry
    public static boolean evokersTakeMagicDamage = false;
    @Entry
    public static boolean evokersTakeProjectileDamage = false;
    @Entry
    public static boolean returnToSender = false;
    @Entry
    public static boolean silverfishTakeFallDamage = false;
    @Entry
    public static boolean silverfishDrown = false;
    @Entry
    public static boolean silverfishSuffocate = false;
    @Entry
    public static boolean slimesTakeFallDamage = false;
    @Entry
    public static boolean slimesTakeProjectileDamage = false;
    @Entry
    public static boolean spidersTakeFallDamage = false;
    @Entry
    public static boolean witchesTakeMagicDamage = false;

    // Stats
    @Entry
    public static boolean blazeFasterShooting = true;
    @Entry
    public static float drownedSwimSpeedMultiplier = 10F;
    @Entry
    public static double endermiteSpeedMultiplier = 1.6;
    @Entry
    public static double evokerHealthMultiplier = 1.5;
    @Entry
    public static int ghastFireballStrengthMultiplier = 2;
    @Entry
    public static double ghastHealthMultiplier = 1.5;
    @Entry
    public static boolean ghastLessFireballCooldown = true;
    @Entry
    public static float witchLingeringPotionRadiusMultiplier = 1.5F;
    @Entry
    public static int witchPotionWindupDivisor = 2;
    @Entry
    public static double slimeHealthMultiplier = 1.5;
    @Entry
    public static double slimeViewDistanceMultiplier = 1.5;
    @Entry
    public static double slimeKnockbackMultiplier = 0;
    @Entry
    public static double slimeSpeedMultiplier = 0.3;
    @Entry
    public static double vindicatorKnockbackResistanceBonus = 0.3;
    @Entry
    public static float witherSkeletonArcherDamageMultiplier = 1.5F;
    @Entry
    public static double witherSkeletonKnockbackResistanceBonus = 0.3;
    @Entry
    public static double zombieSpeedMultiplier = 1.25;

    // Effects
    @Entry
    public static int caveSpiderSlownessBiteDuration = 200;
    @Entry
    public static int caveSpiderSlownessBiteLevel = 2;
    @Entry
    public static int endermanBlindnessStareDuration = 140;
    @Entry
    public static int endermanBlindnessHitDuration = 140;
    @Entry
    public static int huskHungerLevel = 2;
    @Entry
    public static int phantomWeaknessBiteDuration = 300;
    @Entry
    public static int shulkerBulletBlindnessDuration = 100;
    @Entry
    public static int silverfishMiningFatigueBiteDuration = 300;
    @Entry
    public static int magmaCubeContactFireDuration = 4;
    @Entry
    public static int straySlownessLevel = 2;
    @Entry
    public static boolean witchesHaveBetterPotions = true;
    @Entry
    public static boolean witchesHaveBetterSlowness = true;

    // Auras
    @Entry
    public static int blazeBurnAura = 4;
    @Entry
    public static int blazeAuraDuration = 4;
    @Entry
    public static float slimePoisonCloudRadiusMultiplier = 1.5F;
    @Entry
    public static int slimePoisonCloudDurationDivisor = 4;
    @Entry
    public static int slimePoisonDuration = 60;
    @Entry
    public static int slimePoisonAmplifier = 2;
    @Entry
    public static int witherSkeletonsWitherAuraRadius = 4;
    @Entry
    public static int witherSkeletonsWitherAuraDuration = 80;

    // Golems
    @Entry
    public static double golemHealthMultiplier = 2;
    @Entry
    public static double golemViewDistanceMultiplier = 2;
    @Entry
    public static boolean ironGolemSweepAttack = true;
    @Entry
    public static int snowGolemDamage = 4;
    @Entry
    public static int snowGolemAttackRechargeSpeedMultiplier = 2;
    @Entry
    public static float snowGolemAttackRangeMultiplier = 1.4F;
    @Entry
    public static float snowGolemShotSpeed = 1.6F;
    @Entry
    public static float snowGolemInverseAccuracy = 3.0F;

    // Wither
    @Entry
    public static boolean destructiveWither = true;
    @Entry
    public static int witherBlockBreakingCooldown = 20;
    @Entry
    public static int witherSkeletonSummonCooldown = 600;
    @Entry
    public static int witherHealthBonus = 0;
    @Entry
    public static double witherViewDistanceMultiplier = 1;
    @Entry
    public static boolean withersSuffocate = false;

    // Dragon
    @Entry
    public static int dragonBreathDuration = 300;
    @Entry
    public static float dragonBreathRadius = 3.0F;
    @Entry
    public static int dragonFireballCooldown = 100;
    @Entry
    public static boolean dragonTakesExplosiveDamage = false;
}