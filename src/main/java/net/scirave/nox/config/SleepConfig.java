package net.scirave.nox.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;

@Config(name = "sleep")
public class SleepConfig implements ConfigData {
    public boolean applyGlowing = true;
    public boolean extendToSeaLevel = true;
    public int horizontalSearchDistance = 50;
    public int minVerticalSearchDistance = 20;
}
