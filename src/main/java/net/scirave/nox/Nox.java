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

package net.scirave.nox;

import draylar.omegaconfig.OmegaConfig;
import eu.pb4.polymer.api.block.PolymerBlockUtils;
import eu.pb4.polymer.api.block.SimplePolymerBlock;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.scirave.nox.config.NoxConfig;
import net.scirave.nox.polymer.blocks.NoxCobwebBlock;
import net.scirave.nox.polymer.blocks.NoxCobwebBlockEntity;

public class Nox implements ModInitializer {

    public static final String MOD_ID = "nox";
    public static final NoxConfig CONFIG = OmegaConfig.register(NoxConfig.class);

    public static final Block NOX_COBWEB = new NoxCobwebBlock(AbstractBlock.Settings.copy(Blocks.COBWEB));
    public static BlockEntityType<NoxCobwebBlockEntity> NOX_COBWEB_BLOCK_ENTITY;

    @Override
    public void onInitialize() {
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "cobweb"), NOX_COBWEB);
        PolymerBlockUtils.registerBlockEntity(NOX_COBWEB_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(MOD_ID, "cobweb_block_entity"),
                FabricBlockEntityTypeBuilder.create(NoxCobwebBlockEntity::new, NOX_COBWEB).build()));
    }
}
