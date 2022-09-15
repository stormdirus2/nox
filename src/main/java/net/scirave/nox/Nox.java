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

import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.util.registry.Registry;
import net.scirave.nox.config.NoxConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Nox implements ModInitializer {

    public static String MOD_ID = "nox";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static List<Item> TOOLS = null;
    public static List<Item> ARMOR = null;

    @Override
    public void onInitialize() {
        NoxConfig.init(MOD_ID, NoxConfig.class);
        TOOLS = Registry.ITEM.stream().filter((item) -> item instanceof ToolItem).toList();
        ARMOR = Registry.ITEM.stream().filter((item) -> item instanceof ArmorItem).toList();
    }
}
