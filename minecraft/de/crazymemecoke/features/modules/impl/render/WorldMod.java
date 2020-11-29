package de.crazymemecoke.features.modules.impl.render;

import de.crazymemecoke.features.modules.Category;
import de.crazymemecoke.features.modules.Module;
import de.crazymemecoke.features.modules.ModuleInfo;
import de.crazymemecoke.manager.eventmanager.Event;
import de.crazymemecoke.manager.eventmanager.impl.EventPacket;
import de.crazymemecoke.manager.eventmanager.impl.EventUpdate;
import de.crazymemecoke.manager.settingsmanager.Setting;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.world.storage.WorldInfo;

@ModuleInfo(name = "WorldMod", category = Category.RENDER, description = "Mods the world (Custom Weather & Time)")
public class WorldMod extends Module {

    public Setting staticTime = new Setting("Static Time", this, 9000, 0, 24000, true);
    public Setting staticWeather = new Setting("Static Weather", this, "Sunny", new String[]{"Sunny", "Rainy", "Stormy"});
    public Setting changeTime = new Setting("Change Time", this, true);
    public Setting changeWeather = new Setting("Change Weather", this, true);

    @Override
    public void onToggle() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventPacket) {
            if (((EventPacket) event).getType() == EventPacket.Type.RECEIVE) {
                if (((EventPacket) event).getPacket() instanceof S03PacketTimeUpdate) {
                    if (changeTime.isToggled()) {
                        event.setCancelled(true);
                    }
                }
            }
        }

        if (event instanceof EventUpdate) {
            if (changeTime.isToggled()) {
                getWorld().setWorldTime((long) staticTime.getCurrentValue());
            }

            if (changeWeather.isToggled()) {
                WorldInfo worldinfo = getWorld().getWorldInfo();
                switch (staticWeather.getCurrentMode()) {
                    case "Sunny": {
                        worldinfo.setRainTime(0);
                        worldinfo.setThunderTime(0);
                        worldinfo.setRaining(false);
                        worldinfo.setThundering(false);
                        break;
                    }
                    case "Rainy": {
                        worldinfo.setCleanWeatherTime(0);
                        worldinfo.setRaining(true);
                        worldinfo.setThundering(false);
                        break;
                    }
                    case "Stormy": {
                        worldinfo.setCleanWeatherTime(0);
                        worldinfo.setRaining(true);
                        worldinfo.setThundering(true);
                        break;
                    }
                }
            }
        }
    }
}
