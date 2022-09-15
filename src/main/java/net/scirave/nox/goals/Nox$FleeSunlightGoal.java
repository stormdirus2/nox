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

package net.scirave.nox.goals;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class Nox$FleeSunlightGoal extends Goal {

    protected final PathAwareEntity mob;
    protected final double speed;
    protected final World world;
    protected Path path;

    public Nox$FleeSunlightGoal(PathAwareEntity mob, double speed) {
        this.mob = mob;
        this.speed = speed;
        this.world = mob.world;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    public boolean canStart() {
        if (!this.world.isDay()) {
            return false;
        } else if (!this.mob.isOnFire()) {
            return false;
        } else if (!this.world.isSkyVisible(this.mob.getBlockPos())) {
            return false;
        } else if (!this.mob.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
            return false;
        } else {
            if (this.path != null && isShadedPos(this.path.getTarget())) {
                Path pathCheck = this.mob.getNavigation().findPathTo(this.path.getTarget(), 0);
                if (pathCheck != null && pathCheck.reachesTarget()) {
                    this.path = pathCheck;
                    return true;
                }
            }
            return this.targetShadedPos();
        }
    }

    protected boolean isShadedPos(BlockPos pos) {
        return !this.world.isSkyVisible(pos) && this.world.isAir(pos);
    }

    protected boolean targetShadedPos() {
        Path maybePath = this.locateShadedPos();
        if (maybePath != null && maybePath.reachesTarget()) {
            this.path = maybePath;
            return true;
        }
        return false;
    }

    public void tick() {
        this.mob.getNavigation().startMovingAlong(this.path, this.speed);
    }

    @Nullable
    protected Path locateShadedPos() {
        Random random = this.mob.getRandom();
        BlockPos blockPos = this.mob.getBlockPos();

        for (int i = 0; i < 50; ++i) {
            BlockPos blockPos2 = blockPos.add(random.nextInt(50) - 25, random.nextInt(8) - 4, random.nextInt(50) - 25);
            if (isShadedPos(blockPos2)) {
                return this.mob.getNavigation().findPathTo(blockPos2, 0);
            }
        }

        return null;
    }


}
