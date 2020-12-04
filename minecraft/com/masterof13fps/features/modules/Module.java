package com.masterof13fps.features.modules;

import com.masterof13fps.Client;
import com.masterof13fps.Wrapper;
import com.masterof13fps.utils.time.TimeHelper;
import com.masterof13fps.Methods;
import com.masterof13fps.features.modules.impl.gui.Invis;
import com.masterof13fps.features.modules.impl.misc.Plugins;
import com.masterof13fps.features.modules.impl.misc.SendPublic;
import com.masterof13fps.manager.eventmanager.Event;
import com.masterof13fps.manager.notificationmanager.NotificationType;
import com.masterof13fps.manager.settingsmanager.SettingsManager;
import com.masterof13fps.utils.NotifyUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C01PacketChatMessage;
import org.lwjgl.input.Keyboard;

public abstract class Module extends Methods implements Wrapper {

    public static Minecraft mc = Minecraft.mc();
    public static SettingsManager s = Client.main().setMgr();
    private final Category category;
    public boolean enabled;
    public TimeHelper timeHelper = new TimeHelper();
    private final String name;
    private String visualName;
    private int bind;

    public Module() {
        ModuleInfo moduleInfo = getClass().getAnnotation(ModuleInfo.class);
        category = moduleInfo.category();
        name = moduleInfo.name();
        visualName = name;
        bind = moduleInfo.bind();
    }

    public String name() {
        return name;
    }

    public String visualName() {
        return visualName;
    }

    public int bind() {
        return bind;
    }

    public Category category() {
        return category;
    }

    public boolean state() {
        return enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setState(boolean state) {
        this.onToggle();
        if (state) {
            this.onEnable();
            this.enabled = true;
            if (!(this.isCategory(Category.GUI)) && !(Client.main().modMgr().getModule(Invis.class)).state() && !(name.equals(Client.main().modMgr().getModule(Plugins.class).name))) {
                NotifyUtil.notification("Modul aktiviert", "§c" + this.name + "§r wurde aktiviert!", NotificationType.INFO, 2);
            }
        } else {
            this.onDisable();
            this.enabled = false;
            if (!(this.isCategory(Category.GUI)) && !(Client.main().modMgr().getModule(Invis.class)).state() && !(name.equals(Client.main().modMgr().getModule(Plugins.class).name))) {
                NotifyUtil.notification("Modul deaktiviert", "§c" + this.name + "§r wurde deaktiviert!", NotificationType.INFO, 2);
            }
        }
    }

    public void toggle() {
        this.setState(!this.state());

        if (getSettingByName("Mod Toggle", getModuleManager().getModule(SendPublic.class)).isToggled() && getModuleManager().getModule(SendPublic.class).state()) {
            sendPacket(new C01PacketChatMessage(state() ? name + " activated" : name + " deactivated"));
        }
    }

    public abstract void onToggle();

    public abstract void onEnable();

    public abstract void onDisable();

    public abstract void onEvent(Event event);

    public void setBind(int bind) {
        if (bind != 0) {
            System.out.println("Keybind of " + name() + " was set to " + Keyboard.getKeyName(bind));
        }
        this.bind = bind;
    }


    public final boolean isCategory(Category s) {
        return s == category;
    }

    public void setDisplayName(String displayName) {
        visualName = displayName;
    }


}
