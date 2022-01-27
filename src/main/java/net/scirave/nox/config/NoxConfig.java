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
    public boolean mobsHaveFriendlyFire = false;
    @Comment("[Global] Mobs see through walls")
    public boolean mobXray = true;
    @Comment("[Global] Monster base health multiplier")
    public double monsterBaseHealthMultiplier = 1.5;
    @Comment("[Global] Monster follow range multiplier")
    public double monsterFollowRangeMultiplier = 1.5;
    @Comment("[Global] Contact provokes Monsters")
    public boolean contactProvokesMonsters = true;

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

    // Spawning
    @Comment("[Slimes] Remove Slime spawn restrictions")
    public boolean allowSlimesInAllChunks = true;
    @Comment("[Spawning] Guardian natural spawn weight (0 to disable)\n" +
            "Note: does not affect Ocean Monument spawns")
    public int guardianNaturalSpawnWeight = 10;
    @Comment("[Spawning] More Drowned spawn in Oceans")
    public boolean doMoreDrownedSpawns = true;
    @Comment("[Spawning] Witches spawn more frequently")
    public boolean doMoreWitchSpawns = true;
    @Comment("[Spawning] Cave Spiders spawn in caves")
    public boolean spawnCaveSpidersInCaves = true;
    @Comment("[Spawning] Ghasts spawn more often")
    public boolean increaseGhastSpawns = true;
    @Comment("[Spawning] Ghasts can spawn in Crimson/Warped Forest")
    public boolean spawnGhastsInMoreBiomes = true;
    @Comment("[Spawning] Blazes spawn in Wastes/Deltas/Crimson Forest")
    public boolean blazesSpawnNaturallyInNether = true;
    @Comment("[Spawning] Wither Skeletons spawn in Soul Sand Valley/Warped Forest")
    public boolean witherSkeletonsSpawnNaturallyInNether = true;

    // Sleep
    @Comment("[Sleep] Monsters detected glow")
    public boolean sleepApplyGlowing = true;
    @Comment("[Sleep] Check extends to reach sea level")
    public boolean sleepExtendToSeaLevel = true;
    @Comment("[Sleep] Horizontal search distance")
    public int sleepHorizontalSearchDistance = 50;
    @Comment("[Sleep] Minimum vertical search distance")
    public int sleepMinVerticalSearchDistance = 20;

    // Blazes
    @Comment("[Blazes] Fireball recharge speed multiplier")
    public int blazeFireballRechargeSpeedMultiplier = 2;
    @Comment("[Blazes] Ignite targets within X blocks")
    public int blazeIgnitionRadius = 2;

    // Creepers
    @Comment("[Creepers] Creepers breach walls")
    public boolean creepersBreachWalls = true;
    @Comment("[Creepers] Creepers pounce at their target")
    public boolean creepersPounceAtTarget = true;
    @Comment("[Creepers] Pounce cooldown (ticks)")
    public long creeperPounceCooldown = 10;

    // Ender Dragon
    @Comment("[Ender Dragon] Base health multiplier")
    public double enderDragonBaseHealthMultiplier = 3.0;
    @Comment("[Ender Dragon] Fireball cooldown")
    public int enderDragonFireballCooldown = 100;
    @Comment("[Ender Dragon] Dragon is immune to explosion damage")
    public boolean enderDragonIsImmuneToExplosionDamage = true;
    @Comment("[End Crystals (in the fight vicinity) can only be damaged\n" +
            "while connected to the Ender Dragon")
    public boolean endCrystalsIndestructibleUnlessConnectedToDragon = true;
    public boolean buffEnderDragonFireball = true;

    // Endermen
    @Comment("[Endermen] Apply blindness when provoked")
    public boolean endermanAppliesBlindnessOnAggro = true;
    @Comment("[Endermen] Apply blindness on hit")
    public boolean endermanAppliesBlindnessOnHit = true;
    @Comment("[Endermen] Teleport when hit in melee")
    public boolean endermanTeleportsFromMeleeHit = true;

    // Endermites
    @Comment("[Endermites] Base move speed multiplier")
    public double endermiteMoveSpeedMultiplier = 1.6;
    @Comment("[Endermites] Attacks cause their target to teleport")
    public boolean endermiteAttacksMakeTargetTeleport = true;
    @Comment("[Endermites] Endermites are immune to fall damage")
    public boolean endermitesImmuneToFallDamage = true;
    @Comment("[Endermites] Endermites can suffocate in blocks")
    public boolean endermitesCanSuffocate = false;

    // Ghasts
    @Comment("[Ghasts] Base health multiplier")
    public double ghastBaseHealthMultiplier = 2.5;
    @Comment("[Ghasts] Fireball explosion strength multiplier")
    public int ghastFireballExplosionStrengthMultiplier = 2;
    @Comment("[Ghasts] Reflected fireballs insta-kill Ghasts")
    public boolean ghastFireballsInstantlyKillGhasts = false;
    @Comment("[Ghasts] Shoot fireballs more frequently")
    public boolean ghastReducedFireballCooldown = true;

    // Golems
    @Comment("[Golems] Base health multiplier")
    public double golemBaseHealthMultiplier = 2.0;
    @Comment("[Golems] Base follow range multiplier")
    public double golemFollowRangeMultiplier = 2.0;
    @Comment("[Golems] Iron Golems make an area sweep attack")
    public boolean ironGolemsHaveASweepAttack = true;
    @Comment("[Golems] Snow Golem attack recharge speed multiplier")
    public double snowGolemAttackRechargeSpeedMultiplier = 2;
    @Comment("[Golems] Snow Golem attack range multiplier")
    public float snowGolemAttackRangeMultiplier = 1.4F;

    // Guardians
    @Comment("[Guardians] Place water on death")
    public boolean guardiansPlaceWaterOnDeath = true;
    public boolean guardianDeathLeavesWaterSource = true;

    // Illagers
    @Comment("[Illagers] Evoker base health multiplier")
    public double evokerBaseHealthMultiplier = 1.5;
    @Comment("[Illagers] Evokers are immune to non-armor-piercing projectiles")
    public boolean evokersResistProjectiles = true;
    @Comment("[Illagers] Evokers are immune to magical damage")
    public boolean evokersImmuneToMagic = true;
    @Comment("[Illagers] Vindicator knockback resistance bonus")
    public double vindicatorKnockbackResistanceBonus = 0.3;

    // Phantoms
    @Comment("[Phantoms] Phantoms phase through blocks")
    public boolean phantomsPhaseThroughBlocks = true;
    @Comment("[Phantoms] Attacks apply Weakness to target")
    public boolean phantomAttacksApplyWeakness = true;

    // Piglins
    @Comment("[Piglins] Become angry if the player wears any non-gold armor")
    public boolean piglinsRequireExclusivelyGoldArmor = true;

    // Shulkers
    @Comment("[Shulkers] Shulker bullets cause blindness")
    public boolean shulkerBulletsCauseBlindness = true;

    // Silverfish
    @Comment("[Silverfish] Base movement speed multiplier")
    public double silverfishMoveSpeedMultiplier = 2.0;
    @Comment("[Silverfish] Silverfish attacks apply Mining Fatigue")
    public boolean silverfishAttacksGiveMiningFatigue = true;
    @Comment("[Silverfish] Silverfish pounce at their target")
    public boolean silverfishPounceAtTarget = true;
    @Comment("[Silverfish] Silverfish are immune to fall damage")
    public boolean silverfishImmuneToFallDamage = true;
    @Comment("[Silverfish] Silverfish can drown")
    public boolean silverfishCanDrown = false;
    @Comment("[Silverfish] Silverfish can suffocate in blocks")
    public boolean silverfishCanSuffocate = false;

    @Comment("[Skeletons] Skeletons can swim")
    public boolean skeletonsCanSwim = true;
    @Comment("[Skeletons] Stray attacks apply Slowness II")
    public boolean strayAttacksApplyStrongerSlowness = true;
    @Comment("[Skeletons] Wither skeletons can spawn with Bows")
    public boolean witherSkeletonArchersExist = true;
    @Comment("[Skeletons] Wither Skeleton Bow damage multiplier")
    public float witherSkeletonArcherDamageMultiplier = 1.5F;
    @Comment("[Skeletons] Wither Skeleton knockback resistance bonus")
    public double witherSkeletonKnockbackResistanceBonus = 0.3;
    @Comment("[Skeletons] Wither Skeletons passively wither target within X blocks")
    public int witherSkeletonWitheringRadius = 2;

    // Slimes
    @Comment("[Slimes] Slime base health multiplier")
    public double slimeBaseHealthMultiplier = 2.5;
    @Comment("[Slimes] Base follow range multiplier")
    public double slimeFollowRangeMultiplier = 2.5;
    @Comment("[Slimes] Base move speed multiplier")
    public double slimeMoveSpeedMultiplier = 1.3;
    @Comment("[Slimes] Slimes jump constantly")
    public boolean slimesJumpConstantly = true;
    @Comment("[Slimes] Slimes are immune to fall damage")
    public boolean slimesImmuneToFallDamage = true;
    @Comment("[Slimes] Slimes are immune to non-armor-piercing projectiles")
    public boolean slimesResistProjectiles = true;
    @Comment("[Slimes] Slimes spawn poison cloud on death")
    public boolean slimePoisonCloudOnDeath = true;
    @Comment("[Slimes] Magma Cube attacks set target on fire")
    public boolean magmaCubeAttacksIgniteTarget = true;
    @Comment("[Slimes] Magma Cubes leave Lava when killed")
    public boolean magmaCubeLeavesLavaWhenKilled = true;
    @Comment("[Slimes] Magma Cubes place Lava sources on death\n" +
            "If this is false, non-source Lava is used")
    public boolean magmaCubeMakesLavaSourceBlocks = true;

    // Spiders
    @Comment("[Spiders] Spider attacks place webs")
    public boolean spiderAttacksPlaceWebs = true;
    @Comment("[Spiders] Spiders are immune to fall damage")
    public boolean spidersImmuneToFallDamage = true;
    @Comment("[Spiders] Cave Spider attacks place webs")
    public boolean caveSpiderAttacksPlaceWebs = false;
    @Comment("[Spiders] Cave Spider attacks give Slowness III")
    public boolean caveSpidersApplySlowness = true;

    // Witches
    @Comment("[Witches] Witches drink buffed potions")
    public boolean witchesDrinkBetterPotions = true;
    @Comment("[Witches] Prevent potion drinking if target is within 7 blocks")
    public boolean witchesOnlyDrinkAlone = true;
    @Comment("[Witches] Use Lingering Potions instead of Splash Potions")
    public boolean witchesUseLingeringPotions = true;
    @Comment("[Witches] Use buffed Slowness potions")
    public boolean witchesUseStrongerSlowness = true;
    @Comment("[Witches] Ignore damage from non-armor-piercing projectiles")
    public boolean witchesResistProjectiles = true;

    // Wither
    @Comment("[Wither] Base health multiplier")
    public double witherBaseHealthMultiplier = 2.0;
    @Comment("[Wither] Base follow range multiplier")
    public double witherFollowRangeMultiplier = 2.0;
    @Comment("[Wither] Wither rapidly breaks blocks around itself")
    public boolean witherRapidlyBreaksSurroundingBlocks = true;
    @Comment("[Wither] Cooldown for Wither to summon reinforcements")
    public int witherCallReinforcementsCooldown = 600;
    @Comment("[Wither] Wither summons X Wither Skeletons as reinforcements")
    public int witherReinforcementsGroupSize = 3;
    @Comment("[Wither] Can summon reinforcements if target is within X blocks")
    public int witherReinforcementsTriggerRadius = 7;

    // Zombies
    @Comment("[Zombies] Zombies pounce at their target")
    public boolean zombiesPounceAtTarget = true;
    @Comment("[Zombies] Pounce cooldown (ticks)")
    public long zombiePounceCooldown = 30;
    @Comment("[Zombies] Knockback resistance bonus")
    public double zombieKnockbackResistanceBonus = 0.3;
    @Comment("[Zombies] Babies get knockback resistance")
    public boolean babyZombiesGetKnockbackResistance = false;
    @Comment("[Zombies] Drowned swim speed multiplier")
    public float drownedSwimSpeedMultiplier = 10.0F;
    @Comment("[Zombies] Husk attacks apply Hunger II")
    public boolean huskAttacksApplyStrongerHunger = true;

    @Override
    public String getName() {
        return "Nox Config";
    }

    @Override
    public @Nullable String getModid() {
        return Nox.MOD_ID;
    }

}