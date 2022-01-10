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
import net.fabricmc.api.ModInitializer;
import net.scirave.nox.config.NoxConfig;

public class Nox implements ModInitializer {

    public static String MOD_ID = "nox";
    public static final NoxConfig CONFIG = OmegaConfig.register(NoxConfig.class);

    @Override
    public void onInitialize() {
        // Empty...
    }
}
