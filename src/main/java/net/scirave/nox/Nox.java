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

import eu.pb4.polymer.api.block.PolymerBlockUtils;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.scirave.nox.config.NoxConfig;
import net.scirave.nox.polymer.blocks.NoxCobwebBlock;
import net.scirave.nox.polymer.blocks.NoxCobwebBlockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Nox implements ModInitializer {

    public static String MOD_ID = "nox";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static List<Item> TOOLS = null;
    public static List<Item> ARMOR = null;

    public static final Block NOX_COBWEB = new NoxCobwebBlock(AbstractBlock.Settings.copy(Blocks.COBWEB));
    public static BlockEntityType<NoxCobwebBlockEntity> NOX_COBWEB_BLOCK_ENTITY;

    @Override
    public void onInitialize() {
        NoxConfig.init(MOD_ID, NoxConfig.class);
        TOOLS = Registry.ITEM.stream().filter((item) -> item instanceof ToolItem).toList();
        ARMOR = Registry.ITEM.stream().filter((item) -> item instanceof ArmorItem).toList();
        Registry.register(Registry.BLOCK, new Identifier(MOD_ID, "cobweb"), NOX_COBWEB);
        PolymerBlockUtils.registerBlockEntity(NOX_COBWEB_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE,
                new Identifier(MOD_ID, "cobweb_block_entity"),
                FabricBlockEntityTypeBuilder.create(NoxCobwebBlockEntity::new, NOX_COBWEB).build()));
    }
}
