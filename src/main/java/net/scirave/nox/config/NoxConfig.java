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
    @Comment(centered = true)
    public static Comment comment_global;
    @Entry
    public static boolean noFriendlyFire = true;
    @Entry
    public static boolean mobXray = true;
    /* TODO Not used
    @Entry
    public static double monsterBaseHealthMultiplier = 1.5;
     */
    @Entry
    public static double monsterRangeMultiplier = 1.5;
    @Entry
    public static boolean monsterGearScales = true;
    @Entry
    public static boolean monsterAngerOnShove = true;
    @Entry
    public static boolean buffGolems = true;

    // Block Breaking
    @Comment(centered = true)
    public static Comment comment_blockBreaking;
    @Entry
    public static boolean mobsBreakBlocks = true;
    @Entry
    public static float blockBreakingHardnessCutoff = 3.0F;
    @Entry
    public static boolean endermenBreakBlocks = true;
    @Entry
    public static boolean piglinsBreakBlocks = true;
    @Entry
    public static boolean pillagersBreakBlocks = true;
    @Entry
    public static boolean vindicatorsBreakBlocks = true;
    @Entry
    public static boolean witherSkeletonsBreakBlocks = true;
    @Entry
    public static boolean zombiesBreakBlocks = true;
    @Entry
    public static boolean zombifiedPiglinsBreakBlocks = true;

    // Spawning
    @Comment(centered = true)
    public static Comment comment_spawning;
    @Entry
    public static boolean slimeNaturalSpawn = true;
    @Entry
    public static int guardianNaturalSpawnWeight = 10;
    @Entry
    public static boolean doMoreDrownedSpawns = true;
    @Entry
    public static boolean doMoreWitchSpawns = true;
    @Entry
    public static boolean spawnCaveSpidersInCaves = true;
    @Entry
    public static boolean increaseGhastSpawns = true;
    @Entry
    public static boolean spawnGhastsInMoreBiomes = true;
    @Entry
    public static boolean blazeNaturalSpawn = true;
    @Entry
    public static boolean witherSkeletonsSpawnNaturallyInNether = true;
    @Entry
    public static boolean endermiteFlowerSpawn = true;

    // Sleep
    @Comment(centered = true)
    public static Comment comment_sleep;
    @Entry
    public static boolean sleepApplyGlowing = true;
    @Entry
    public static boolean sleepExtendToSeaLevel = true;
    @Entry
    public static int sleepHorizontalSearchDistance = 50;
    @Entry
    public static int sleepMinVerticalSearchDistance = 20;


    // Creepers
    @Comment(centered = true)
    public static Comment comment_creepers;
    @Entry
    public static boolean creepersBreachWalls = true;
    @Entry
    public static int creeperBreachDistance = 7;
    @Entry
    public static boolean creepersRunFromShields = true;
    @Entry
    public static boolean creepersAttackShields = false;

    // Effects
    @Comment(centered = true)
    public static Comment comment_effects;
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

    // Auras
    @Comment(centered = true)
    public static Comment comment_auras;
    @Entry
    public static int blazeIgnitionAuraRadius = 2;
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
    @Comment(centered = true)
    public static Comment comment_golems;
    @Entry
    public static double golemBaseHealthMultiplier = 2.0;
    @Entry
    public static double golemFollowRangeMultiplier = 2.0;
    @Entry
    public static boolean ironGolemsHaveASweepAttack = true;
    @Entry
    public static double snowGolemAttackRechargeSpeedMultiplier = 2;
    @Entry
    public static float snowGolemAttackRangeMultiplier = 1.4F;
    @Entry
    public static int snowGolemDamage = 4;
    @Entry
    public static float snowGolemShotSpeed = 1.6F;
    @Entry
    public static float snowGolemInverseAccuracy = 3.0F;

    // Ender Dragon
    @Comment(centered = true)
    public static Comment comment_enderDragon;
    @Entry
    public static double enderDragonBaseHealthMultiplier = 3.0;
    @Entry
    public static int enderDragonFireballCooldown = 100;
    @Entry
    public static boolean enderDragonIsImmuneToExplosionDamage = true;
    @Entry
    public static boolean endCrystalsIndestructibleUnlessConnectedToDragon = true;
    @Entry
    public static boolean buffEnderDragonFireball = true;
    @Entry
    public static int enderDragonBreathDuration = 300;
    @Entry
    public static float enderDragonBreathRadius = 3.0F;

    // Endermen
    @Comment(centered = true)
    public static Comment comment_endermen;
    @Entry
    public static boolean endermanAppliesBlindnessOnAggro = true;
    @Entry
    public static boolean endermanAppliesBlindnessOnHit = true;
    @Entry
    public static boolean endermanTeleportsFromMeleeHit = true;

    // Endermites
    @Comment(centered = true)
    public static Comment comment_endermites;
    @Entry
    public static double endermiteMoveSpeedMultiplier = 1.6;
    @Entry
    public static boolean endermiteAttacksMakeTargetTeleport = true;
    @Entry
    public static boolean endermitesImmuneToFallDamage = true;
    @Entry
    public static boolean endermitesCanSuffocate = false;

    // Ghasts
    @Comment(centered = true)
    public static Comment comment_ghasts;
    @Entry
    public static double ghastBaseHealthMultiplier = 2.5;
    @Entry
    public static int ghastFireballExplosionStrengthMultiplier = 2;
    @Entry
    public static boolean ghastFireballsInstantlyKillGhasts = false;
    @Entry
    public static boolean ghastReducedFireballCooldown = true;

    // Guardians
    @Comment(centered = true)
    public static Comment comment_guardians;
    @Entry
    public static boolean guardiansPlaceWaterOnDeath = true;
    @Entry
    public static boolean guardianDeathLeavesWaterSource = true;

    // Illagers
    @Comment(centered = true)
    public static Comment comment_illagers;
    @Entry
    public static double evokerBaseHealthMultiplier = 1.5;
    @Entry
    public static boolean evokersResistProjectiles = true;
    @Entry
    public static boolean evokersImmuneToMagic = true;
    @Entry
    public static double vindicatorKnockbackResistanceBonus = 0.3;

    // Phantoms
    @Comment(centered = true)
    public static Comment comment_phantoms;
    @Entry
    public static boolean phantomsPhaseThroughBlocks = true;
    @Entry
    public static boolean phantomAttacksApplyWeakness = true;

    // Piglins
    @Comment(centered = true)
    public static Comment comment_piglins;
    @Entry
    public static boolean piglinsRequireExclusivelyGoldArmor = true;

    // Shulkers
    @Comment(centered = true)
    public static Comment comment_shulker;
    @Entry
    public static boolean shulkerBulletsCauseBlindness = true;

    // Silverfish
    @Comment(centered = true)
    public static Comment comment_silverfish;
    @Entry
    public static double silverfishMoveSpeedMultiplier = 2.0;
    @Entry
    public static boolean silverfishAttacksGiveMiningFatigue = true;
    @Entry
    public static boolean silverfishPounceAtTarget = true;
    @Entry
    public static boolean silverfishImmuneToFallDamage = true;
    @Entry
    public static boolean silverfishCanDrown = false;
    @Entry
    public static boolean silverfishCanSuffocate = false;

    //Skeletons
    @Comment(centered = true)
    public static Comment comment_skeletons;
    @Entry
    public static boolean skeletonsCanSwim = true;
    @Entry
    public static boolean strayAttacksApplyStrongerSlowness = true;
    @Entry
    public static boolean witherSkeletonArchersExist = true;
    @Entry
    public static float witherSkeletonArcherDamageMultiplier = 1.5F;
    @Entry
    public static double witherSkeletonKnockbackResistanceBonus = 0.3;

    // Slimes
    @Comment(centered = true)
    public static Comment comment_slimes;
    @Entry
    public static double slimeBaseHealthMultiplier = 2.5;
    @Entry
    public static double slimeFollowRangeMultiplier = 2.5;
    @Entry
    public static double slimeMoveSpeedMultiplier = 1.3;
    @Entry
    public static boolean slimesJumpConstantly = true;
    @Entry
    public static boolean slimesImmuneToFallDamage = true;
    @Entry
    public static boolean slimesResistProjectiles = true;
    @Entry
    public static boolean slimePoisonCloudOnDeath = true;
    @Entry
    public static boolean magmaCubeAttacksIgniteTarget = true;
    @Entry
    public static boolean magmaCubeLeavesLavaWhenKilled = true;
    @Entry
    public static boolean magmaCubeMakesLavaSourceBlocks = true;


    // Spiders
    @Comment(centered = true)
    public static Comment comment_spiders;
    @Entry
    public static boolean spiderAttacksPlaceWebs = true;
    @Entry
    public static boolean spidersImmuneToFallDamage = true;
    @Entry
    public static boolean caveSpiderAttacksPlaceWebs = false;
    @Entry
    public static boolean caveSpidersApplySlowness = true;

    // Witches
    @Comment(centered = true)
    public static Comment comment_witches;
    @Entry
    public static boolean witchesDrinkBetterPotions = true;
    @Entry
    public static boolean witchesFleeToDrink = true;
    @Entry
    public static boolean witchesUseLingeringPotions = true;
    @Entry
    public static boolean witchesUseStrongerSlowness = true;
    @Entry
    public static boolean witchesResistProjectiles = true;
    @Entry
    public static boolean witchesTakeMagicDamage = false;
    @Entry
    public static float witchLingeringPotionRadiusMultiplier = 1.5F;
    @Entry
    public static int witchPotionWindupDivisor = 2;

    // Wither
    @Comment(centered = true)
    public static Comment comment_wither;
    @Entry
    public static boolean destructiveWither = true;
    @Entry
    public static int witherBlockBreakingCooldown = 20;
    @Entry
    public static double witherBaseHealthMultiplier = 2.0;
    @Entry
    public static double witherFollowRangeMultiplier = 2.0;
    @Entry
    public static boolean witherRapidlyBreaksSurroundingBlocks = true;
    @Entry
    public static int witherCallReinforcementsCooldown = 600;
    @Entry
    public static int witherReinforcementsGroupSize = 3;
    @Entry
    public static int witherReinforcementsTriggerRadius = 7;
    @Entry
    public static boolean withersSuffocate = false;

    // Zombies
    @Comment(centered = true)
    public static Comment comment_zombies;
    /* TODO Not used
    @Entry//@Comment("[Zombies] Zombies pounce at their target")
    public static boolean zombiesPounceAtTarget = true;
    @Entry//@Comment("[Zombies] Pounce cooldown (ticks)")
    public static long zombiePounceCooldown = 30;
    @Entry//@Comment("[Zombies] Knockback resistance bonus")
    public static double zombieKnockbackResistanceBonus = 0.3;
    @Entry//@Comment("[Zombies] Babies get knockback resistance")
    public static boolean babyZombiesGetKnockbackResistance = false;
    */
    @Entry
    public static float drownedSwimSpeedMultiplier = 10.0F;
    @Entry
    public static boolean huskAttacksApplyStrongerHunger = true;
    @Entry
    public static double zombieSpeedMultiplier = 1.25;

    //Blazes
    @Comment(centered = true)
    public static Comment comment_blazes;
    @Entry
    public static boolean lessBlazeFireballCooldown = true;

}