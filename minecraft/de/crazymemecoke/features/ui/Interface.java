package de.crazymemecoke.features.ui;

import de.crazymemecoke.Client;
import de.crazymemecoke.features.modules.impl.gui.HUD;
import de.crazymemecoke.features.modules.impl.gui.Invis;
import de.crazymemecoke.features.ui.guiscreens.GuiItems;
import de.crazymemecoke.features.ui.tabgui.TabGUI;
import de.crazymemecoke.manager.settingsmanager.SettingsManager;
import de.crazymemecoke.manager.fontmanager.UnicodeFontRenderer;
import de.crazymemecoke.features.modules.Category;
import de.crazymemecoke.features.modules.Module;
import de.crazymemecoke.utils.Wrapper;
import de.crazymemecoke.utils.render.Colors;
import de.crazymemecoke.utils.render.Rainbow;
import de.crazymemecoke.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Interface extends GuiIngame {

    private final static Interface instance = new Interface(Minecraft.mc());
    Minecraft mc = Wrapper.mc;
    int offset, brightness, saturation, speed;

    public Interface(Minecraft mcIn) {
        super(mcIn);
    }

    public static Interface main() {
        return instance;
    }

    public void renderGameOverlay(float p_175180_1_) {
        super.renderGameOverlay(p_175180_1_);
        offset = (int) Client.main().setMgr().settingByName("Rainbow Offset", Client.main().modMgr().getModule(HUD.class)).getCurrentValue();
        speed = (int) Client.main().setMgr().settingByName("Rainbow Speed", Client.main().modMgr().getModule(HUD.class)).getCurrentValue();
        brightness = (int) Client.main().setMgr().settingByName("Rainbow Brightness", Client.main().modMgr().getModule(HUD.class)).getCurrentValue();
        saturation = (int) Client.main().setMgr().settingByName("Rainbow Saturation", Client.main().modMgr().getModule(HUD.class)).getCurrentValue();
        if (!(Client.main().modMgr().getByName("Invis").state())) {
            Display.setTitle(Client.main().getClientName() + " " + Client.main().getClientVersion() + " | made by " + Client.main().getClientCoder());

            if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                Client.main().modMgr().getModule(Invis.class).setState(true);
            }

            if (Keyboard.isKeyDown(Keyboard.KEY_END) && mc.currentScreen == null) {
                mc.displayGuiScreen(new GuiItems(null));
            }

            if (Client.main().modMgr().getByName("HUD").state()) {
                if (Client.main().setMgr().settingByName("Developer Mode", Client.main().modMgr().getByName("HUD")).isToggled()) {
                    doRenderStuff();
                } else {
                    if (mc.currentScreen == null && !mc.gameSettings.showDebugInfo) {
                        doRenderStuff();
                    }
                }
            }
        } else {
            Display.setTitle("Minecraft 1.8.8");
            if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL) && (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT))) {
                Client.main().modMgr().getModule(Invis.class).setState(false);
            }
        }
    }

    private void doRenderStuff() {
        ScaledResolution s = new ScaledResolution(mc);

        SettingsManager setMgr = Client.main().setMgr();
        if (setMgr.settingByName("Watermark", Client.main().modMgr().getByName("HUD")).isToggled()) {
            renderWatermark();
        }
        if (setMgr.settingByName("TabGUI", Client.main().modMgr().getByName("HUD")).isToggled()) {
            renderTabGUI();
        }
        if (setMgr.settingByName("ArrayList", Client.main().modMgr().getByName("HUD")).isToggled()) {
            renderArrayList();
        }
        if (setMgr.settingByName("KeyStrokes", Client.main().modMgr().getByName("HUD")).isToggled()) {
            renderKeyStrokes();
        }
        if (setMgr.settingByName("Hotbar", Client.main().modMgr().getByName("HUD")).isToggled()) {
            renderHotbar();
        }
    }

    private void renderHotbar() {
        if (mc.currentScreen == null || Client.main().setMgr().settingByName("Developer Mode", Client.main().modMgr().getByName("HUD")).isToggled()) {
            ScaledResolution s = new ScaledResolution(mc);
            UnicodeFontRenderer bigNoodleTitling22 = Client.main().fontMgr().font("BigNoodleTitling", 22, Font.PLAIN);
            UnicodeFontRenderer comfortaa22 = Client.main().fontMgr().font("Comfortaa", 22, Font.PLAIN);

            String ping;

            try {
                ping = String.valueOf(mc.getCurrentServerData().pingToServer);
            } catch (Exception e) {
                ping = "N/A";
            }
            String s1 = "§7FPS: §8" + Minecraft.debugFPS + " §7| PING: §8" + ping;
            String s2 = "§7CORDS: X §8" + (int) mc.thePlayer.posX + " §7Y §8" + (int) mc.thePlayer.posY + " §7Z §8" + (int) mc.thePlayer.posZ;

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime localTime = LocalDateTime.now();
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDateTime localDate = LocalDateTime.now();

            String s3 = timeFormatter.format(localTime);
            String s4 = dateFormatter.format(localDate);

            RenderUtils.drawRect(0, s.height() - 22, 4, s.height(), Colors.main().ambienBlueTop);
            RenderUtils.drawRect(4, s.height() - 22, bigNoodleTitling22.getStringWidth(s2) + 8, s.height(), new Color(0, 0, 0, 180).getRGB());

            RenderUtils.drawRect(s.width() - 4, s.height() - 22, s.width(), s.height(), Colors.main().ambienBlueTop);
            RenderUtils.drawRect(s.width() - comfortaa22.getStringWidth(s4) - 12, s.height() - 22, s.width() - 4, s.height(), new Color(0, 0, 0, 180).getRGB());

            bigNoodleTitling22.drawStringWithShadow(s1, 5, s.height() - 20, -1);
            bigNoodleTitling22.drawStringWithShadow(s2, 5, s.height() - 10, -1);

            comfortaa22.drawStringWithShadow(s3, s.width() - 55, s.height() - 20, -1);
            comfortaa22.drawStringWithShadow(s4, s.width() - 63, s.height() - 10, -1);
        }
    }

    private void renderKeyStrokes() {
        ScaledResolution s = new ScaledResolution(mc);

        RenderUtils.drawFilledCircle(s.width() - 60, s.height() - 80, 13, new Color(0, 0, 0, 150).getRGB());
        RenderUtils.drawFilledCircle(s.width() - 90, s.height() - 50, 13, new Color(0, 0, 0, 150).getRGB());
        RenderUtils.drawFilledCircle(s.width() - 60, s.height() - 50, 13, new Color(0, 0, 0, 150).getRGB());
        RenderUtils.drawFilledCircle(s.width() - 30, s.height() - 50, 13, new Color(0, 0, 0, 150).getRGB());

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            RenderUtils.drawFilledCircle(s.width() - 60, s.height() - 80, 13, Colors.main().getAmbienOldBlueColor());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            RenderUtils.drawFilledCircle(s.width() - 90, s.height() - 50, 13, Colors.main().getAmbienOldBlueColor());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            RenderUtils.drawFilledCircle(s.width() - 60, s.height() - 50, 13, Colors.main().getAmbienOldBlueColor());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            RenderUtils.drawFilledCircle(s.width() - 30, s.height() - 50, 13, Colors.main().getAmbienOldBlueColor());
        }

        Client.main().fontMgr().font("esp", 25, Font.PLAIN).drawStringWithShadow("W", s.width() - 66, s.height() - 83, -1);
        Client.main().fontMgr().font("esp", 25, Font.PLAIN).drawStringWithShadow("A", s.width() - 95, s.height() - 53, -1);
        Client.main().fontMgr().font("esp", 25, Font.PLAIN).drawStringWithShadow("S", s.width() - 65, s.height() - 53, -1);
        Client.main().fontMgr().font("esp", 25, Font.PLAIN).drawStringWithShadow("D", s.width() - 34, s.height() - 53, -1);
    }

    private void renderWatermark() {
        ScaledResolution s = new ScaledResolution(mc);

        String mode = Client.main().setMgr().settingByName("Design", Client.main().modMgr().getByName("HUD")).getCurrentMode();

        switch (mode) {
            case "Ambien Old": {
                RenderUtils.drawRect(0, 0, 73, 25, new Color(0, 0, 0).getRGB());
                Client.main().fontMgr().ambien45.drawStringWithShadow("A", 3, 1, Colors.main().getAmbienOldBlueColor());
                Client.main().fontMgr().ambien45.drawStringWithShadow("mbien", 17, 1, Colors.main().getGrey());
                Client.main().fontMgr().ambien20.drawStringWithShadow(String.valueOf(Client.main().getClientVersion()), 55, 0, 0x349ac0);
                break;
            }
            case "Vortex": {
                GL11.glPushMatrix();
                GL11.glScalef(2f, 2f, 2f);
                Client.main().fontMgr().vortex20.drawString("V", 0, -2, Colors.main().getVortexRedColor());
                GL11.glScalef(2f / 4, 2f / 4, 2f / 4);
                GL11.glPopMatrix();

                Client.main().fontMgr().vortex20.drawString("ortex", 18, 6, Colors.main().getGrey());

                GL11.glPushMatrix();
                GL11.glScalef(0.8f, 0.8f, 0.8f);
                Client.main().fontMgr().vortex20.drawString(String.valueOf(Client.main().getClientVersion()), 72, 9, 0x349ac0);
                GL11.glPopMatrix();
                break;
            }
            case "Suicide": {
                Client.main().fontMgr().font("Comfortaa", 30, Font.PLAIN).drawStringWithShadow("SUICIDE", 2, 3, Colors.main().getSuicideBlueColor());
                Client.main().fontMgr().font("Comfortaa", 20, Font.PLAIN).drawStringWithShadow(String.valueOf(Client.main().getClientVersion()), 68, 4, Colors.main().getSuicideBlueColor());

                RenderUtils.drawRect(0, 110, 75, 151, Colors.main().getSuicideDarkBlueGreyColor());
                RenderUtils.drawRect(0, 110, 73, 153, Colors.main().getSuicideBlueGreyColor());

                Client.main().fontMgr().font("Comfortaa", 20, Font.PLAIN).drawStringWithShadow("FPS: ", 2, 115, Colors.main().getSuicideBlueColor());
                Client.main().fontMgr().font("Comfortaa", 20, Font.PLAIN).drawStringWithShadow(String.valueOf(Minecraft.debugFPS), 26, 115, -1);

                Client.main().fontMgr().font("Comfortaa", 20, Font.PLAIN).drawStringWithShadow("Ping: ", 2, 127, Colors.main().getSuicideBlueColor());
                try {
                    Client.main().fontMgr().font("Comfortaa", 20, Font.PLAIN).drawStringWithShadow(String.valueOf(mc.getCurrentServerData().pingToServer), 30, 127, -1);
                } catch (Exception ex) {
                    Client.main().fontMgr().font("Comfortaa", 20, Font.PLAIN).drawStringWithShadow("N/A", 30, 127, -1);
                }

                Client.main().fontMgr().font("Comfortaa", 20, Font.PLAIN).drawStringWithShadow("Time: ", 2, 139, Colors.main().getSuicideBlueColor());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                Client.main().fontMgr().font("Comfortaa", 20, Font.PLAIN).drawStringWithShadow(dtf.format(now), 32, 139, -1);
                break;
            }
            case "Apinity": {
                RenderUtils.drawRect(0, 35, 40, 175, new Color(0, 0, 0, 150).getRGB());

                double height =

                Client.main().fontMgr().font("Raleway Light", 45, Font.PLAIN).drawStringWithShadow("Apinity", 1, 1, Colors.main().getApinityGreyColor());
                Client.main().fontMgr().font("Raleway Light", 30, Font.PLAIN).drawStringWithShadow(String.valueOf(Client.main().getClientVersion()), 72, 1, Colors.main().getApinityBlueColor());
                try {
                    Client.main().fontMgr().font("Comfortaa", 15, Font.PLAIN).drawStringWithShadow("Ping: " + mc.getCurrentServerData().pingToServer, 2, 125, Colors.main().getApinityBlueColor());
                } catch (Exception ex) {
                    Client.main().fontMgr().font("Comfortaa", 15, Font.PLAIN).drawStringWithShadow("Ping: N/A", 1, 125, Colors.main().getApinityBlueColor());
                }

                Client.main().fontMgr().font("Comfortaa", 15, Font.PLAIN).drawStringWithShadow("FPS: " + Minecraft.debugFPS, 2, 135, Colors.main().getApinityBlueColor());

                Client.main().fontMgr().font("Comfortaa", 15, Font.PLAIN).drawStringWithShadow("X: " + mc.thePlayer.getPosition().getX(), 2, 145, Colors.main().getApinityBlueColor());
                Client.main().fontMgr().font("Comfortaa", 15, Font.PLAIN).drawStringWithShadow("Y: " + mc.thePlayer.getPosition().getY(), 2, 155, Colors.main().getApinityBlueColor());
                Client.main().fontMgr().font("Comfortaa", 15, Font.PLAIN).drawStringWithShadow("Z: " + mc.thePlayer.getPosition().getZ(), 2, 165, Colors.main().getApinityBlueColor());
                break;
            }
            case "Huzuni": {
                Client.main().fontMgr().font("Arial", 20, Font.PLAIN).drawStringWithShadow("Huzuni Dev " + Client.main().getClientVersion(), 2, 2, -1);
                break;
            }
            case "Wurst": {
                RenderUtils.drawRect(0, 10, 145, 28, new Color(255, 255, 255, 130).getRGB());

                Client.main().fontMgr().font("Arial", 20, Font.PLAIN).drawString("v" + Client.main().getClientVersion() + " MC1.8.8", 80, 15, new Color(0, 0, 0).getRGB());

                int width = 75;
                int height = 20;
                int x = 0;
                int y = 9;

                RenderUtils.drawImage(Client.main().getWurstWatermark(), x, y, width, height);
                break;
            }
            case "Nodus": {
                mc.fontRendererObj.drawString("Nodus", 2, 2, Colors.main().getNodusPurpleColor());
                mc.fontRendererObj.drawString("v" + Client.main().getClientVersion(), 35, 2, Colors.main().getNodusTealColor());
                break;
            }
            case "Saint": {
                UnicodeFontRenderer f1 = Client.main().fontMgr().font("Verdana", 20, Font.PLAIN);
                UnicodeFontRenderer f2 = Client.main().fontMgr().font("Verdana", 17, Font.PLAIN);
                String s1 = "Saint";
                String s2 = "v" + Client.main().getClientVersion();
                f1.drawStringWithShadow(s1, s.width() / 2 - f1.getStringWidth(s1) / 2, 3, -1);
                f2.drawStringWithShadow(s2, s.width() / 2 - f2.getStringWidth(s2) / 2, 13, new Color(0x4A4A4A).getRGB());

                f2.drawStringWithShadow("FPS:", 2, 88, -1);
                f2.drawStringWithShadow(String.valueOf(Minecraft.debugFPS), 22, 88, new Color(0x4A4A4A).getRGB());
                break;
            }
            case "Icarus Old": {
                mc.fontRendererObj.drawString("Icarus (b" + Client.main().getClientVersion() + ")", 2, 2, -1);
                break;
            }
            case "Icarus New": {
                RenderUtils.drawRect(s.width() - 62, 0, s.width(), 20, Colors.main().getIcarusNewGreyColor());

                Client.main().fontMgr().font("BigNoodleTiltling", 40, Font.BOLD).drawStringWithShadow("Icarus", s.width() - 60, -2, -1);
                break;
            }
            case "Ambien New": {
                RenderUtils.drawRect(0, 10, 112, 40, Colors.main().getAmbienNewDarkGreyColor());

                Client.main().fontMgr().font("FIFA Welcome", 60, Font.PLAIN).drawStringWithShadow("A", 2, 7, Colors.main().getAmbienNewBlueColor());
                Client.main().fontMgr().font("FIFA Welcome", 60, Font.PLAIN).drawStringWithShadow("mbien", 20, 7, -1);
                Client.main().fontMgr().font("BigNoodleTiltling", 20, Font.PLAIN).drawStringWithShadow("V", 89, 24, Colors.main().getAmbienNewBlueColor());
                Client.main().fontMgr().font("BigNoodleTiltling", 20, Font.PLAIN).drawStringWithShadow(String.valueOf(Client.main().getClientVersion()), 96, 24, -1);
                break;
            }
            case "Hero": {
                RenderUtils.drawRect(0, 0, 60, 21, new Color(0, 0, 0, 70).getRGB());

                Client.main().fontMgr().font("Raleway Light", 55, Font.PLAIN).drawStringWithShadow("Hero", -2, -4, Colors.main().getHeroGreenColor());
                break;
            }
            case "Klientus": {
                Client.main().fontMgr().font("Verdana", 50, Font.PLAIN).drawStringWithShadow("K", 1, 1, new Color(0x00659f).getRGB());
                Client.main().fontMgr().font("Verdana", 50, Font.PLAIN).drawStringWithShadow("lientus", 18, 1, -1);

                RenderUtils.drawRect(3, 28, 105, 30, -1);

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                Client.main().fontMgr().font("Verdana", 18, Font.PLAIN).drawStringWithShadow(dtf.format(now), 32, 32, -1);
                break;
            }
            case "Ambien Newest": {
                int width = 110;
                int height = 110;
                int x = -5;
                int y = -15;

                int x2 = 15;
                int y2 = 65;

                int x3 = 10;
                int y3 = 70;

                RenderUtils.drawRect(x2, y2, x2 + 79, y2 + 10, Colors.main().getAmbienNewestGreyMainColor());
                RenderUtils.drawRect(x3, y3, x3 + 84, y3 + 10, Colors.main().getAmbienNewestGreyMainColor());
                RenderUtils.drawImage(Client.main().getAmbienWatermark(), x, y, width, height);
                break;
            }
            case "Koks": {
                GL11.glPushMatrix();
                GL11.glScalef(2f, 2f, 2f);
                mc.fontRendererObj.drawString("Koks", 3, 4, Colors.main().getKoksGreenColor());
                GL11.glScalef(2f / 4, 2f / 4, 2f / 4);
                GL11.glPopMatrix();

                mc.fontRendererObj.drawString("v" + Client.main().getClientVersion(), 55, 15, Colors.main().getGrey());
                break;
            }
        }
    }

    private void renderTabGUI() {
        TabGUI gui = new TabGUI(mc);

        if (Client.main().setMgr().settingByName("Watermark", Client.main().modMgr().getByName("HUD")).isToggled()) {
            String mode = Client.main().setMgr().settingByName("Design", Client.main().modMgr().getByName("HUD")).getCurrentMode();

            switch (mode) {
                case "Ambien Old": {
                    gui.drawGui(1, 30, 72);
                    break;
                }
                case "Ambien Newest": {
                    gui.drawGui(11, 75, 83);
                    break;
                }
                case "Vortex":
                case "Suicide": {
                    gui.drawGui(1, 20, 72);
                    break;
                }
                case "Apinity": {
                    gui.drawGui(5, 40, 55);
                    break;
                }
                case "Huzuni": {
                    gui.drawGui(3, 15, 95);
                    break;
                }
                case "Saint":
                case "Icarus Old":
                case "Icarus New": {
                    gui.drawGui(3, 15, 65);
                    break;
                }
                case "Ambien New": {
                    gui.drawGui(1, 45, 65);
                    break;
                }
                case "Hero": {
                    gui.drawGui(1, 22, 59);
                    break;
                }
                case "Vanta": {
                    gui.drawGui(1, 20, 65);
                    break;
                }
                case "Koks": {
                    gui.drawGui(6, 28, 70);
                    break;
                }
            }
        }
    }

    private void renderArrayList() {
        ScaledResolution scaledResolution = new ScaledResolution(mc);

        UnicodeFontRenderer font = Client.main().fontMgr().font("Exo Regular", 20, Font.PLAIN);

        ArrayList<Module> activeModuleNames = this.getActiveModuleNames();
        activeModuleNames.sort((m1, m2) -> {
            final int diff = font.getStringWidth(m2.visualName())
                    - font.getStringWidth(m1.visualName());
            return (diff != 0) ? diff : m2.visualName().compareTo(m1.visualName());
        });
        int yDist = 2;
        int rectY = 1;
        int stringY = 1;

        for (Module m : activeModuleNames) {
            int xDist = scaledResolution.width() - font.getStringWidth(m.visualName()) - 4;
            RenderUtils.drawRect(scaledResolution.width() - font.getStringWidth(m.visualName()) - 5, (rectY - 2), scaledResolution.width(), (rectY + 10), new Color(0, 0, 0, 150).getRGB());
            RenderUtils.drawRect(scaledResolution.width() - 2, (rectY - 2), scaledResolution.width(), (rectY + 10), Rainbow.getRainbow(offset, speed, saturation, brightness));
            font.drawString(m.visualName(), xDist + 1, stringY, Color.RED.getRGB());
            rectY += 12;
            stringY += 12;
            offset += offset;
        }
    }

    protected ArrayList<Module> getActiveModuleNames() {
        ArrayList<Module> list = new ArrayList<Module>();

        for (Module m : Client.main().modMgr().modules) {
            if (m.state() && !m.isCategory(Category.GUI)) {
                list.add(m);
            }
        }

        return list;
    }

}