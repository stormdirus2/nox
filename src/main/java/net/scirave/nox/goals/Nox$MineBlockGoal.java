/*
 * -------------------------------------------------------------------
 * Nox
 * Copyright (c) 2023 SciRave
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 * -------------------------------------------------------------------
 */

package net.scirave.nox.goals;


import net.minecraft.block.*;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.scirave.nox.config.NoxConfig;
import net.scirave.nox.util.Nox$MiningInterface;
import net.scirave.nox.util.NoxUtil;
import org.jetbrains.annotations.Nullable;
import static net.scirave.nox.util.NoxUtil.NOX_ALWAYS_MINE;
import static net.scirave.nox.util.NoxUtil.NOX_CANT_MINE;

public class Nox$MineBlockGoal extends Goal {

    protected final MobEntity owner;
    private LivingEntity target;

    @Nullable
    private LivingEntity targetSeen;
    private BlockPos posToMine;
    private int mineTick;

    public Nox$MineBlockGoal(MobEntity living) {
        this.owner = living;
    }

    public static float getAdjustedHardness(World world, BlockPos pos) {
        float hardness = world.getBlockState(pos).getHardness(world, pos);
        if (hardness < 0) {
            return 0.0F;
        }
        return 2.0F / hardness / 30F;
    }

    public static boolean canMine(BlockState block) {
        if (block.isIn(NOX_ALWAYS_MINE)) {
            return NoxUtil.isAtWoodLevel(block);
        } else if (block.getBlock().getHardness() >= NoxConfig.blockBreakingHardnessCutoff || block.isIn(NOX_CANT_MINE)) {
            return false;
        } else {
            return NoxUtil.isAtWoodLevel(block);
        }
    }

    public @Nullable BlockPos findBlock(LivingEntity victim, @Nullable Path path) {
        BlockPos origin = this.owner.getBlockPos();

        if (path != null && path.getEnd() != null) {
            origin = path.getEnd().getBlockPos();
        }

        int yMod = 1;
        BlockPos elected;

        if (isBreakable(this.owner.world, origin.up())) {
            return origin.up();
        }

        int xDiff = victim.getBlockX() - this.owner.getBlockX();
        int zDiff = victim.getBlockZ() - this.owner.getBlockZ();

        int absXDiff = Math.abs(xDiff);
        int absZDiff = Math.abs(zDiff);

        if (this.owner.getBlockY() > victim.getBlockY()) {
            yMod = 0;
            if (isBreakable(this.owner.world, origin.down())) {
                return origin.down();
            }
            if (absXDiff == absZDiff) {
                elected = searchForBlock(this.owner.world, origin);
                if (elected != null) {
                    return elected;
                }
            }
        } else if (this.owner.getBlockY() < victim.getBlockY()) {
            yMod = 2;
            if (isBreakable(this.owner.world, origin.up(2))) {
                return origin.up(2);
            }
            if (absXDiff == absZDiff) {
                elected = searchForBlock(this.owner.world, origin.up());
                if (elected != null) {
                    return elected;
                }
            }
        }

        if (isBreakable(this.owner.world, origin.up().up(yMod - 1))) {
            return origin.up();
        }

        if (absXDiff > absZDiff) {
            if (xDiff > 0) {
                elected = searchForBlock(this.owner.world, origin.east().up(yMod));
            } else {
                elected = searchForBlock(this.owner.world, origin.west().up(yMod));
            }
            if (elected != null) {
                return elected;
            }
            if (zDiff > 0) {
                elected = searchForBlock(this.owner.world, origin.south().up(yMod));
            } else {
                elected = searchForBlock(this.owner.world, origin.north().up(yMod));
            }
        } else {
            if (zDiff > 0) {
                elected = searchForBlock(this.owner.world, origin.south().up(yMod));
            } else {
                elected = searchForBlock(this.owner.world, origin.north().up(yMod));
            }
            if (elected != null) {
                return elected;
            }
            if (xDiff > 0) {
                elected = searchForBlock(this.owner.world, origin.east().up(yMod));
            } else {
                elected = searchForBlock(this.owner.world, origin.west().up(yMod));
            }
        }

        return elected;
    }

    @Override
    public boolean canStart() {
        if (!((Nox$MiningInterface) owner).nox$isAllowedToMine())
            return false;
        if (shouldContinue()) {
            return true;
        } else if (!NoxConfig.mobsBreakBlocks) {
            return false;
        }
        LivingEntity victim = this.owner.getTarget();
        if (victim == null || victim.isDead()) {
            this.targetSeen = null;
            return false;
        }

        if (this.owner.age > 60 && (this.owner.isOnGround() || this.owner.isTouchingWater()) && victim.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING)) {

            if (this.targetSeen != victim) {
                if (this.owner.canSee(victim)) {
                    this.targetSeen = victim;
                } else {
                    return false;
                }
            }

            Path path = this.owner.getNavigation().findPathTo(victim, 0);
            if (path != null && path.reachesTarget()) {
                return false;
            }
            if (path == null || path.getEnd() == null || path.getEnd().getSquaredDistance(this.owner.getBlockPos()) <= 1) {
                BlockPos blockPos = this.findBlock(victim, path);
                if (blockPos == null) {
                    this.owner.getNavigation().startMovingAlong(path, 1.0);
                    return false;
                }
                this.posToMine = blockPos;
                return true;
            } else {
                this.owner.getNavigation().startMovingAlong(path, 1.0);
            }
        }
        return false;
    }

    @Override
    public void start() {
        ((Nox$MiningInterface) this.owner).nox$setMining(true);
        this.target = this.owner.getTarget();
    }

    @Override
    public boolean shouldContinue() {
        if (this.target != null && this.target.isAlive() && this.owner.isAlive()) {
            if (this.posToMine != null && this.owner.squaredDistanceTo(this.posToMine.getX(), this.posToMine.getY(), this.posToMine.getZ()) <= 25.0D) {
                Path path = this.owner.getNavigation().findPathTo(target, 0);
                return path == null || !path.reachesTarget();
            }
        }
        return false;
    }

    @Override
    public void stop() {
        ((Nox$MiningInterface) this.owner).nox$setMining(false);
        this.mineTick = 0;
        if (this.posToMine != null) {
            this.owner.world.setBlockBreakingInfo(this.owner.getId(), this.posToMine, -1);
        }
        this.posToMine = null;
    }

    @Override
    public void tick() {
        if (this.posToMine == null || !isBreakable(this.owner.world, this.posToMine)) {
            this.mineTick = 0;
            stop();
            return;
        }
        float f = getAdjustedHardness(this.owner.world, this.posToMine) * (float) (mineTick + 1);
        int k = (int) (f * 10.0F);
        this.mineTick++;
        this.owner.getNavigation().stop();
        this.owner.getLookControl().lookAt(this.posToMine.getX() + 0.5D, this.posToMine.getY() + 0.5D, this.posToMine.getZ() + 0.5D);
        this.owner.clearActiveItem();
        if (this.mineTick % 5 == 0) {
            this.owner.swingHand(Hand.MAIN_HAND);
        }
        this.owner.world.setBlockBreakingInfo(this.owner.getId(), this.posToMine, k);
        if (f > 1.0F) {
            this.owner.world.breakBlock(this.posToMine, true, this.owner);
        }
    }

    public @Nullable BlockPos searchForBlock(World world, BlockPos origin) {
        if (isBreakable(world, origin)) {
            return origin;
        } else if (isBreakable(world, origin.up())) {
            return origin.up();
        } else if (isBreakable(world, origin.down())) {
            return origin.down();
        }
        return null;
    }

    private boolean isBreakable(World world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return !state.getCollisionShape(world, pos).isEmpty() && canMine(state);
    }
}
