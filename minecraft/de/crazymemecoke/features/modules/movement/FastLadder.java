package de.crazymemecoke.features.modules.movement;

import de.crazymemecoke.Client;
import de.crazymemecoke.manager.modulemanager.ModuleInfo;
import de.crazymemecoke.manager.settingsmanager.Setting;
import de.crazymemecoke.manager.eventmanager.Event;
import de.crazymemecoke.manager.modulemanager.Category;
import de.crazymemecoke.manager.modulemanager.Module;

@ModuleInfo(name = "FastLadder", category = Category.MOVEMENT, description = "Climb ladders faster")
public class FastLadder extends Module {

    public Setting mode = new Setting("Mode", this, "Vanilla", new String[] {"Vanilla", "AAC"});
    public Setting vanillaSpeed = new Setting("Vanilla Speed", this, 0.4, 0.1, 1.0, false);
    public Setting aacSpeed = new Setting("AAC Speed", this, 0.2, 0.1, 1.0, false);

    @Override
    public void onToggle() {

    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    private void doAAC() {
        double speed = Client.main().setMgr().settingByName("AAC Speed", this).getCurrentValue();

        if ((mc.thePlayer.isOnLadder()) && (mc.thePlayer.isCollidedHorizontally)) {
            mc.thePlayer.motionY = speed;
        }
    }

    private void doVanilla() {
        double speed = Client.main().setMgr().settingByName("Vanilla Speed", this).getCurrentValue();

        if ((mc.thePlayer.isOnLadder()) && (mc.thePlayer.isCollidedHorizontally)) {
            mc.thePlayer.motionY = speed;
        }
    }

    @Override
    public void onEvent(Event event) {
        String mode = Client.main().setMgr().settingByName("Mode", this).getCurrentMode();

        switch (mode) {
            case "Vanilla": {
                doVanilla();
                break;
            }
            case "AAC": {
                doAAC();
                break;
            }
        }
    }
}