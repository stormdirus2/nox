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

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.world.GameRules;
import net.scirave.nox.util.Nox$CreeperBreachInterface;

import java.util.EnumSet;

public class Nox$CreeperBreachGoal extends Goal {

    private final CreeperEntity creeper;

    public Nox$CreeperBreachGoal(CreeperEntity creeper) {
        this.creeper = creeper;
        this.setControls(EnumSet.of(Goal.Control.MOVE));
    }

    public boolean canStart() {
        if (((Nox$CreeperBreachInterface) creeper).nox$isAllowedToBreachWalls()) {
            LivingEntity living = this.creeper.getTarget();
            return living != null && living.world.getGameRules().getBoolean(GameRules.DO_MOB_GRIEFING) && shouldBreach(living);
        }
        return false;
    }

    private boolean shouldBreach(LivingEntity living) {
        if (!creeper.isNavigating() && this.creeper.age > 60 && (this.creeper.isOnGround() || this.creeper.isTouchingWater())) {
            Path path = creeper.getNavigation().findPathTo(living, 0);
            if (path == null || (!path.reachesTarget() && path.getEnd() != null && path.getEnd().getSquaredDistance(this.creeper.getBlockPos()) <= 4)) {
                return true;
            } else {
                creeper.getNavigation().startMovingAlong(path, 1.0);
            }
        }
        return false;
    }

    public void start() {
        this.creeper.ignite();
    }
}
