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

package net.scirave.nox.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.scirave.nox.util.Nox$EnderDragonFightInterface;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.UUID;

@Mixin(EnderDragonFight.class)
public class EnderDragonFightMixin implements Nox$EnderDragonFightInterface {

    @Shadow
    @Final
    private ServerWorld world;

    @Shadow
    @Nullable
    private UUID dragonUuid;

    @Shadow
    private boolean dragonKilled;

    @Override
    public boolean isDragonKilled() {
        return this.dragonKilled;
    }

    @Override
    public boolean isConnectedCrystal(EndCrystalEntity crystal) {
        Entity entity = this.world.getEntity(this.dragonUuid);
        if (entity instanceof EnderDragonEntity dragon) {
            return dragon.connectedCrystal == crystal;
        }
        return false;
    }

    @Override
    public boolean inDragonRange(Vec3d pos) {
        if (!this.dragonKilled) {
            Entity entity = this.world.getEntity(this.dragonUuid);
            if (entity instanceof EnderDragonEntity dragon) {
                return dragon.squaredDistanceTo(pos) < 250000.0D;
            }
        }
        return false;
    }

}
