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

package net.scirave.nox.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "nox")
public class NoxConfig extends PartitioningSerializer.GlobalData {


    @ConfigEntry.Category("sleep")
    @ConfigEntry.Gui.TransitiveObject
    private final SleepConfig sleepCategory = new SleepConfig();

    public static NoxConfig get() {
        return AutoConfig.getConfigHolder(NoxConfig.class).getConfig();
    }

    public SleepConfig getSleepConfig() {
        return this.sleepCategory;
    }
}
