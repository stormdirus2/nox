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

package net.scirave.nox.util;

public interface Nox$MiningInterface {

    default boolean nox$isAllowedToMine() {
        return false;
    }

    boolean nox$isMining();

    void nox$setMining(boolean bool);

}
