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

package net.scirave.nox.polymer.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.scirave.nox.Nox;

public class NoxCobwebBlockEntity extends BlockEntity {

    private static final String AGE_NBT_KEY = "nox:cobweb_age";
    private static final short MAX_AGE = 200; // ticks

    private short age = 0;
    private byte ticksUntilRemovalCheck = 0;

    public NoxCobwebBlockEntity(BlockPos pos, BlockState state) {
        super(Nox.NOX_COBWEB_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, NoxCobwebBlockEntity be) {
        if (be.age > MAX_AGE) {
            if (be.ticksUntilRemovalCheck == 0) {
                be.ticksUntilRemovalCheck = 12;
                if (world.getRandom().nextInt(20) == 0 && world.getOtherEntities(null, new Box(pos).expand(0.3), e -> e instanceof LivingEntity).isEmpty())
                    world.removeBlock(pos, false);
            } else be.ticksUntilRemovalCheck--;
        } else be.age++;
    }

    @Override
    public void readNbt(NbtCompound tag) {
        super.readNbt(tag);
        this.age = tag.getShort(AGE_NBT_KEY);
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        tag.putShort(AGE_NBT_KEY, this.age);
    }

}
