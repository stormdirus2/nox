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
    @Comment("[Global] Golem base health multiplier")
    public double golemBaseHealthMultiplier = 2.0;
    @Comment("[Global] Golem follow range multiplier")
    public double golemFollowRangeMultiplier = 2.0;
    @Comment("[Global] Contact provokes Monsters")
    public boolean contactProvokesMonsters = true;

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
    @Comment("[Sleep] Check extends to reach sea level")
    public boolean sleepExtendToSeaLevel = true;
    @Comment("[Sleep] Horizontal search distance")
    public int sleepHorizontalSearchDistance = 50;
    @Comment("[Sleep] Minimum vertical search distance")
    public int sleepMinVerticalSearchDistance = 20;

    // Mobs (Misc.)
    @Comment("[Mobs] Skeletons can swim")
    public boolean skeletonsCanSwim = true;

    // Blaze
    @Comment("[Blaze] Fireball recharge speed multiplier")
    public int blazeFireballRechargeSpeedMultiplier = 2;
    @Comment("[Blaze] Ignite targets within X blocks")
    public int blazeIgnitionRadius = 2;

    // Creeper
    @Comment("[Creeper] Creepers breach walls")
    public boolean creepersBreachWalls = true;
    @Comment("[Creeper] Creepers pounce at their target")
    public boolean creepersPounceAtTarget = true;
    @Comment("[Creeper] Pounce cooldown (ticks)")
    public long creeperPounceCooldown = 10;

    // Enderman
    @Comment("[Enderman] Apply blindness when provoked")
    public boolean endermanAppliesBlindnessOnAggro = true;
    @Comment("[Enderman] Apply blindness on hit")
    public boolean endermanAppliesBlindnessOnHit = true;
    @Comment("[Enderman] Teleport when hit in melee")
    public boolean endermanTeleportsFromMeleeHit = true;

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

    @Comment("[Wither Skeletons] Can spawn with Bows")
    public boolean witherSkeletonArchersExist = true;
    @Comment("[Wither Skeletons] Bow damage multiplier")
    public float witherSkeletonArcherDamageMultiplier = 1.5F;
    @Comment("[Wither Skeletons] Knockback resistance multiplier")
    public double witherSkeletonKnockbackResistanceMultiplier = 1.3;
    @Comment("[Wither Skeletons] Passively wither target within X blocks")
    public int witherSkeletonWitheringRadius = 2;

    // Zombies
    @Comment("[Zombies] Zombies pounce at their target")
    public boolean zombiesPounceAtTarget = true;
    @Comment("[Zombies] Pounce cooldown (ticks)")
    public long zombiePounceCooldown = 30;
    @Comment("[Zombies] Knockback resistance modifier (additive)")
    public double zombieKnockbackResistanceModifier = 0.3;
    @Comment("[Zombies] Babies get knockback resistance")
    public boolean babyZombiesGetKnockbackResistance = false;

    @Override
    public String getName() {
        return "Nox Config";
    }

    @Override
    public @Nullable String getModid() {
        return Nox.MOD_ID;
    }

}