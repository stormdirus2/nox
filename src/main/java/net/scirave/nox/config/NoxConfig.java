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
    public static boolean magmaCubeLavaOnDeath = true;
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
    @Entry//@Comment("[Golems] Snow Golem attack recharge speed multiplier")
    public static double snowGolemAttackRechargeSpeedMultiplier = 2;
    @Entry//@Comment("[Golems] Snow Golem attack range multiplier")
    public static float snowGolemAttackRangeMultiplier = 1.4F;
    @Entry
    public static int snowGolemDamage = 4;
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
    public static boolean withersSuffocate = false;

    // Dragon
    @Entry
    public static int dragonBreathDuration = 300;
    @Entry
    public static float dragonBreathRadius = 3.0F;

    // Ender Dragon
    @Entry//@Comment("[Ender Dragon] Base health multiplier")
    public static double enderDragonBaseHealthMultiplier = 3.0;
    @Entry//@Comment("[Ender Dragon] Fireball cooldown")
    public static int enderDragonFireballCooldown = 100;
    @Entry//@Comment("[Ender Dragon] Dragon is immune to explosion damage")
    public static boolean enderDragonIsImmuneToExplosionDamage = true;
    @Entry//@Comment("[End Crystals (in the fight vicinity) can only be damaged while connected to the Ender Dragon")
    public static boolean endCrystalsIndestructibleUnlessConnectedToDragon = true;
    public static boolean buffEnderDragonFireball = true;

    // Endermen
    @Entry//@Comment("[Endermen] Apply blindness when provoked")
    public static boolean endermanAppliesBlindnessOnAggro = true;
    @Entry//@Comment("[Endermen] Apply blindness on hit")
    public static boolean endermanAppliesBlindnessOnHit = true;
    @Entry//@Comment("[Endermen] Teleport when hit in melee")
    public static boolean endermanTeleportsFromMeleeHit = true;

    // Endermites
    @Entry//@Comment("[Endermites] Base move speed multiplier")
    public static double endermiteMoveSpeedMultiplier = 1.6;
    @Entry//@Comment("[Endermites] Attacks cause their target to teleport")
    public static boolean endermiteAttacksMakeTargetTeleport = true;
    @Entry//@Comment("[Endermites] Endermites are immune to fall damage")
    public static boolean endermitesImmuneToFallDamage = true;
    @Entry//@Comment("[Endermites] Endermites can suffocate in blocks")
    public static boolean endermitesCanSuffocate = false;

    // Ghasts
    @Entry//@Comment("[Ghasts] Base health multiplier")
    public static double ghastBaseHealthMultiplier = 2.5;
    @Entry//@Comment("[Ghasts] Fireball explosion strength multiplier")
    public static int ghastFireballExplosionStrengthMultiplier = 2;
    @Entry//@Comment("[Ghasts] Reflected fireballs insta-kill Ghasts")
    public static boolean ghastFireballsInstantlyKillGhasts = false;
    @Entry//@Comment("[Ghasts] Shoot fireballs more frequently")
    public static boolean ghastReducedFireballCooldown = true;

    // Guardians
    @Entry//@Comment("[Guardians] Place water on death")
    public static boolean guardiansPlaceWaterOnDeath = true;
    public static boolean guardianDeathLeavesWaterSource = true;

    // Illagers
    @Entry//@Comment("[Illagers] Evoker base health multiplier")
    public static double evokerBaseHealthMultiplier = 1.5;
    @Entry//@Comment("[Illagers] Evokers are immune to non-armor-piercing projectiles")
    public static boolean evokersResistProjectiles = true;
    @Entry//@Comment("[Illagers] Evokers are immune to magical damage")
    public static boolean evokersImmuneToMagic = true;
    @Entry//@Comment("[Illagers] Vindicator knockback resistance bonus")
    public static double vindicatorKnockbackResistanceBonus = 0.3;

    // Phantoms
    @Entry//@Comment("[Phantoms] Phantoms phase through blocks")
    public static boolean phantomsPhaseThroughBlocks = true;
    @Entry//@Comment("[Phantoms] Attacks apply Weakness to target")
    public static boolean phantomAttacksApplyWeakness = true;

    // Piglins
    @Entry//@Comment("[Piglins] Become angry if the player wears any non-gold armor")
    public static boolean piglinsRequireExclusivelyGoldArmor = true;

    // Shulkers
    @Entry//@Comment("[Shulkers] Shulker bullets cause blindness")
    public static boolean shulkerBulletsCauseBlindness = true;

    // Silverfish
    @Entry//@Comment("[Silverfish] Base movement speed multiplier")
    public static double silverfishMoveSpeedMultiplier = 2.0;
    @Entry//@Comment("[Silverfish] Silverfish attacks apply Mining Fatigue")
    public static boolean silverfishAttacksGiveMiningFatigue = true;
    @Entry//@Comment("[Silverfish] Silverfish pounce at their target")
    public static boolean silverfishPounceAtTarget = true;
    @Entry//@Comment("[Silverfish] Silverfish are immune to fall damage")
    public static boolean silverfishImmuneToFallDamage = true;
    @Entry//@Comment("[Silverfish] Silverfish can drown")
    public static boolean silverfishCanDrown = false;
    @Entry//@Comment("[Silverfish] Silverfish can suffocate in blocks")
    public static boolean silverfishCanSuffocate = false;

    @Entry//@Comment("[Skeletons] Skeletons can swim")
    public static boolean skeletonsCanSwim = true;
    @Entry//@Comment("[Skeletons] Stray attacks apply Slowness II")
    public static boolean strayAttacksApplyStrongerSlowness = true;
    @Entry//@Comment("[Skeletons] Wither skeletons can spawn with Bows")
    public static boolean witherSkeletonArchersExist = true;
    @Entry//@Comment("[Skeletons] Wither Skeleton Bow damage multiplier")
    public static float witherSkeletonArcherDamageMultiplier = 1.5F;
    @Entry//@Comment("[Skeletons] Wither Skeleton knockback resistance bonus")
    public static double witherSkeletonKnockbackResistanceBonus = 0.3;

    // Slimes
    @Entry//@Comment("[Slimes] Slime base health multiplier")
    public static double slimeBaseHealthMultiplier = 2.5;
    @Entry//@Comment("[Slimes] Base follow range multiplier")
    public static double slimeFollowRangeMultiplier = 2.5;
    @Entry//@Comment("[Slimes] Base move speed multiplier")
    public static double slimeMoveSpeedMultiplier = 1.3;
    @Entry//@Comment("[Slimes] Slimes jump constantly")
    public static boolean slimesJumpConstantly = true;
    @Entry//@Comment("[Slimes] Slimes are immune to fall damage")
    public static boolean slimesImmuneToFallDamage = true;
    @Entry//@Comment("[Slimes] Slimes are immune to non-armor-piercing projectiles")
    public static boolean slimesResistProjectiles = true;
    @Entry//@Comment("[Slimes] Slimes spawn poison cloud on death")
    public static boolean slimePoisonCloudOnDeath = true;
    @Entry//@Comment("[Slimes] Magma Cube attacks set target on fire")
    public static boolean magmaCubeAttacksIgniteTarget = true;
    @Entry//@Comment("[Slimes] Magma Cubes leave Lava when killed")
    public static boolean magmaCubeLeavesLavaWhenKilled = true;
    @Entry//@Comment("[Slimes] Magma Cubes place Lava sources on death. If this is false, non-source Lava is used")
    public static boolean magmaCubeMakesLavaSourceBlocks = true;

    // Spiders
    @Entry//@Comment("[Spiders] Spider attacks place webs")
    public static boolean spiderAttacksPlaceWebs = true;
    @Entry//@Comment("[Spiders] Spiders are immune to fall damage")
    public static boolean spidersImmuneToFallDamage = true;
    @Entry//@Comment("[Spiders] Cave Spider attacks place webs")
    public static boolean caveSpiderAttacksPlaceWebs = false;
    @Entry//@Comment("[Spiders] Cave Spider attacks give Slowness III")
    public static boolean caveSpidersApplySlowness = true;

    // Witches
    @Entry//@Comment("[Witches] Witches drink buffed potions")
    public static boolean witchesDrinkBetterPotions = true;
    @Entry//@Comment("[Witches] Prevent potion drinking if target is within 7 blocks")
    public static boolean witchesFleeToDrink = true;
    @Entry//@Comment("[Witches] Use Lingering Potions instead of Splash Potions")
    public static boolean witchesUseLingeringPotions = true;
    @Entry//@Comment("[Witches] Use buffed Slowness potions")
    public static boolean witchesUseStrongerSlowness = true;
    @Entry//@Comment("[Witches] Ignore damage from non-armor-piercing projectiles")
    public static boolean witchesResistProjectiles = true;
    @Entry
    public static boolean witchesTakeMagicDamage = false;
    @Entry
    public static float witchLingeringPotionRadiusMultiplier = 1.5F;
    @Entry
    public static int witchPotionWindupDivisor = 2;

    // Wither
    @Entry//@Comment("[Wither] Base health multiplier")
    public static double witherBaseHealthMultiplier = 2.0;
    @Entry//@Comment("[Wither] Base follow range multiplier")
    public static double witherFollowRangeMultiplier = 2.0;
    @Entry//@Comment("[Wither] Wither rapidly breaks blocks around itself")
    public static boolean witherRapidlyBreaksSurroundingBlocks = true;
    @Entry//@Comment("[Wither] Cooldown for Wither to summon reinforcements")
    public static int witherCallReinforcementsCooldown = 600;
    @Entry//@Comment("[Wither] Wither summons X Wither Skeletons as reinforcements")
    public static int witherReinforcementsGroupSize = 3;
    @Entry//@Comment("[Wither] Can summon reinforcements if target is within X blocks")
    public static int witherReinforcementsTriggerRadius = 7;

    // Zombies
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
    @Entry//@Comment("[Zombies] Drowned swim speed multiplier")
    public static float drownedSwimSpeedMultiplier = 10.0F;
    @Entry//@Comment("[Zombies] Husk attacks apply Hunger II")
    public static boolean huskAttacksApplyStrongerHunger = true;
    @Entry
    public static double zombieSpeedMultiplier = 1.25;

    @Entry
    public static boolean lessBlazeFireballCooldown = true;

}