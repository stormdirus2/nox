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

import net.minecraft.entity.boss.dragon.EnderDragonFight;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.server.world.ServerWorld;
import net.scirave.nox.config.NoxConfig;
import net.scirave.nox.util.Nox$EnderDragonFightInterface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndCrystalEntity.class)
public abstract class EndCrystalEntityMixin extends EntityMixin {

    public void nox$invulnerableCheck(DamageSource source, CallbackInfoReturnable<Boolean> cir) {
        if (this.world instanceof ServerWorld serverWorld && NoxConfig.endCrystalsIndestructibleUnlessConnectedToDragon) {
            EnderDragonFight enderDragonFight = serverWorld.getEnderDragonFight();
            if (enderDragonFight != null && ((Nox$EnderDragonFightInterface) enderDragonFight).inDragonRange(this.getPos())) {
                if (!((Nox$EnderDragonFightInterface) enderDragonFight).isConnectedCrystal((EndCrystalEntity) (Object) this)) {
                    cir.setReturnValue(true);
                }
            }
        }
    }

}
