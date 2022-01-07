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

import net.minecraft.entity.EntityData;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.EndermiteEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermiteEntity.class)
public abstract class EndermiteEntityMixin extends HostileEntityMixin {

    @Override
    public void nox$modifyAttributes(ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, EntityData entityData, NbtCompound entityNbt, CallbackInfoReturnable<EntityData> cir) {
        this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).addPersistentModifier(new EntityAttributeModifier("Nox: Endermite bonus", 0.6, EntityAttributeModifier.Operation.MULTIPLY_BASE));
    }

    @Override
    public void nox$onSuccessfulAttack(LivingEntity target) {
        if (target.world instanceof ServerWorld serverWorld) {
            double d = target.getX();
            double e = target.getY();
            double f = target.getZ();

            for (int i = 0; i < 16; ++i) {
                double g = target.getX() + (target.getRandom().nextDouble() - 0.5D) * 16.0D;
                double h = MathHelper.clamp(target.getY() + (double) (target.getRandom().nextInt(16) - 8), serverWorld.getBottomY(), serverWorld.getBottomY() + serverWorld.getLogicalHeight() - 1);
                double j = target.getZ() + (target.getRandom().nextDouble() - 0.5D) * 16.0D;

                if (target.hasVehicle()) {
                    target.stopRiding();
                }

                if (target.teleport(g, h, j, true)) {
                    serverWorld.playSound(null, d, e, f, SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, SoundCategory.HOSTILE, 1.0F, 1.0F);
                    target.playSound(SoundEvents.ITEM_CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                    break;
                }
            }
        }
    }

    @Override
    public void nox$invulnerableCheck(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        super.nox$invulnerableCheck(source, cir);
        if (source.getName().equals("fall") || source.getName().equals("inWall")) {
            cir.setReturnValue(true);
        }
    }


}
