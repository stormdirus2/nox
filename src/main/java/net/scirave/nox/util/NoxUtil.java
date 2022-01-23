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

package net.scirave.nox.util;

import net.fabricmc.fabric.api.tag.TagRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tag.Tag;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.scirave.nox.Nox;

public class NoxUtil {

    public static final Tag<Item> FIREPROOF = TagRegistry.item(new Identifier("c:fireproof"));

    private final static ItemStack WOOD_PICKAXE = Items.WOODEN_PICKAXE.getDefaultStack();
    private final static ItemStack WOOD_AXE = Items.WOODEN_AXE.getDefaultStack();
    private final static ItemStack WOOD_SHOVEL = Items.WOODEN_SHOVEL.getDefaultStack();

    public static boolean isAtWoodLevel(BlockState state) {
        return !state.isToolRequired() || WOOD_PICKAXE.isSuitableFor(state) || WOOD_AXE.isSuitableFor(state) || WOOD_SHOVEL.isSuitableFor(state);
    }

    public static boolean isAnAlly(MobEntity attacker, MobEntity victim) {

        boolean validTypes = (attacker instanceof Monster && victim instanceof Monster) ||
                (attacker instanceof GolemEntity && victim instanceof GolemEntity);

        LivingEntity attackerTarget = attacker.getTarget();
        LivingEntity victimTarget = victim.getTarget();

        return !Nox.CONFIG.mobsHaveFriendlyFire && validTypes && attackerTarget != attacker && victimTarget != victim && victimTarget != null && attackerTarget == victimTarget;
    }

    public static void EnderDragonShootFireball(EnderDragonEntity dragon, LivingEntity target) {
        Vec3d i = dragon.getRotationVec(1.0F);
        double k = dragon.head.getX() - i.x;
        double l = dragon.head.getBodyY(0.5D) + 0.5D;
        double m = dragon.head.getZ() - i.z;
        double n = target.getX() - k;
        double o = target.getBodyY(0.5D) - l;
        double p = target.getZ() - m;
        if (!dragon.isSilent()) {
            dragon.world.syncWorldEvent(null, 1017, dragon.getBlockPos(), 0);
        }

        DragonFireballEntity dragonFireballEntity = new DragonFireballEntity(dragon.world, dragon, n, o, p);
        dragonFireballEntity.refreshPositionAndAngles(k, l, m, 0.0F, 0.0F);
        dragonFireballEntity.powerX *= 5;
        dragonFireballEntity.powerY *= 5;
        dragonFireballEntity.powerZ *= 5;
        dragon.world.spawnEntity(dragonFireballEntity);
    }

}
