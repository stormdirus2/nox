package net.scirave.nox.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;

@Config(name = "nox")
public class NoxConfig extends PartitioningSerializer.GlobalData {


    @ConfigEntry.Category("sleep")
    @ConfigEntry.Gui.TransitiveObject
    private SleepConfig sleepCategory = new SleepConfig();

    public static NoxConfig get() {
        return AutoConfig.getConfigHolder(NoxConfig.class).getConfig();
    }

    public SleepConfig getSleepConfig() {
        return this.sleepCategory;
    }
}
