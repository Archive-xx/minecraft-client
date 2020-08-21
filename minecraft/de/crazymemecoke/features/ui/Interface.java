package de.crazymemecoke.features.ui;

import de.crazymemecoke.Client;
import de.crazymemecoke.features.modules.combat.Aura;
import de.crazymemecoke.features.ui.guiscreens.clienthelper.GuiClientHelper;
import de.crazymemecoke.features.ui.tabgui.TabGUI;
import de.crazymemecoke.manager.fontmanager.FontManager;
import de.crazymemecoke.manager.fontmanager.UnicodeFontRenderer;
import de.crazymemecoke.manager.modulemanager.Category;
import de.crazymemecoke.manager.modulemanager.Module;
import de.crazymemecoke.utils.Wrapper;
import de.crazymemecoke.utils.render.Rainbow;
import de.crazymemecoke.utils.render.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Interface extends GuiIngame {

    Minecraft mc = Wrapper.mc;
    FontManager font = Client.instance().getFontManager();

    public Interface(Minecraft mcIn) {
        super(mcIn);
    }

    public void renderGameOverlay(float p_175180_1_) {
        super.renderGameOverlay(p_175180_1_);
        if (!(Client.instance().modManager().getByName("Invis").getState())) {
            Display.setTitle(Client.instance().getClientName() + " " + Client.instance().getClientVersion() + " | made by " + Client.instance().getClientCoder());
            if (Client.instance().modManager().getByName("HUD").getState()) {
                if (Client.instance().setMgr().getSettingByName("Developer Mode", Client.instance().modManager().getByName("HUD")).getBool()) {
                    doRenderStuff();
                } else {
                    if (mc.currentScreen == null && !mc.gameSettings.showDebugInfo) {
                        doRenderStuff();
                    }
                }
            }
        } else {
            Display.setTitle("Minecraft 1.8.8");
        }
    }

    private void doRenderStuff() {
        if (Client.instance().setMgr().getSettingByName("Watermark", Client.instance().modManager().getByName("HUD")).getBool()) {
            renderWatermark();
        }
        if (Client.instance().setMgr().getSettingByName("TabGUI", Client.instance().modManager().getByName("HUD")).getBool()) {
            renderTabGUI();
        }
        if (Client.instance().setMgr().getSettingByName("ArrayList", Client.instance().modManager().getByName("HUD")).getBool()) {
            renderArrayList();
        }
        if (Client.instance().setMgr().getSettingByName("Target HUD", Client.instance().modManager().getByName("HUD")).getBool()) {
            renderTargetHUD();
        }
        if (Client.instance().setMgr().getSettingByName("KeyStrokes", Client.instance().modManager().getByName("HUD")).getBool()) {
            renderKeyStrokes();
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_RCONTROL)) {
            mc.displayGuiScreen(new GuiClientHelper());
        }
    }

    private void renderKeyStrokes() {
        ScaledResolution s = new ScaledResolution(mc);

        RenderUtils.drawFilledCircle(s.width() - 60, s.height() - 70, 13, new Color(0, 0, 0, 150).getRGB());
        RenderUtils.drawFilledCircle(s.width() - 90, s.height() - 40, 13, new Color(0, 0, 0, 150).getRGB());
        RenderUtils.drawFilledCircle(s.width() - 60, s.height() - 40, 13, new Color(0, 0, 0, 150).getRGB());
        RenderUtils.drawFilledCircle(s.width() - 30, s.height() - 40, 13, new Color(0, 0, 0, 150).getRGB());

        if (Keyboard.isKeyDown(Keyboard.KEY_W)) {
            RenderUtils.drawFilledCircle(s.width() - 60, s.height() - 70, 13, Client.instance().getAmbienOldBlueColor());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_A)) {
            RenderUtils.drawFilledCircle(s.width() - 90, s.height() - 40, 13, Client.instance().getAmbienOldBlueColor());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_S)) {
            RenderUtils.drawFilledCircle(s.width() - 60, s.height() - 40, 13, Client.instance().getAmbienOldBlueColor());
        }
        if (Keyboard.isKeyDown(Keyboard.KEY_D)) {
            RenderUtils.drawFilledCircle(s.width() - 30, s.height() - 40, 13, Client.instance().getAmbienOldBlueColor());
        }

        Client.instance().getFontManager().getFont("esp", 25, Font.PLAIN).drawStringWithShadow("W", s.width() - 66, s.height() - 73, -1);
        Client.instance().getFontManager().getFont("esp", 25, Font.PLAIN).drawStringWithShadow("A", s.width() - 95, s.height() - 43, -1);
        Client.instance().getFontManager().getFont("esp", 25, Font.PLAIN).drawStringWithShadow("S", s.width() - 65, s.height() - 43, -1);
        Client.instance().getFontManager().getFont("esp", 25, Font.PLAIN).drawStringWithShadow("D", s.width() - 34, s.height() - 43, -1);
    }

    private void renderTargetHUD() {
        ScaledResolution s = new ScaledResolution(mc);

        if (Aura.currentTarget instanceof EntityPlayer && Client.instance().setMgr().getSettingByName("Target HUD", Client.instance().modManager().getByName("HUD")).getBool()) {
            RenderUtils.drawRect(s.width() / 2 - 130, s.height() / 2 - 50, s.width() / 2 + 130, s.height() / 2 + 50, new Color(0, 0, 0, 110).getRGB());

            EntityPlayer p = (EntityPlayer) Aura.currentTarget;
            Client.instance().getFontManager().cabin23.drawStringWithShadow("Spieler: " + p.getName(), s.width() / 2 - 125, s.height() / 2 - 45, -1);
            Client.instance().getFontManager().cabin23.drawStringWithShadow("Leben: " + p.getHealth() + " / " + p.getMaxHealth(), s.width() / 2 - 125, s.height() / 2 - 35, -1);

            ItemStack i = p.getCurrentEquippedItem();
            if (i == null) {
                Client.instance().getFontManager().cabin23.drawStringWithShadow("Item: Kein Item", s.width() / 2 - 125, s.height() / 2 - 25, -1);
            } else {
                Client.instance().getFontManager().cabin23.drawStringWithShadow("Item: " + i.getDisplayName(), s.width() / 2 - 125, s.height() / 2 - 25, -1);
            }

            GuiInventory.drawEntityOnScreen(51, 75, 30, (float) (51) - 50, (float) (75 - 50) - 20, (EntityLivingBase) Aura.currentTarget);

        }
    }

    private void renderWatermark() {
        ScaledResolution s = new ScaledResolution(mc);

        String mode = Client.instance().setMgr().getSettingByName("Design", Client.instance().modManager().getByName("HUD")).getMode();

        switch (mode) {
            case "ambien old": {
                RenderUtils.drawRect(0, 0, 73, 25, new Color(0, 0, 0).getRGB());
                Client.instance().getFontManager().ambien45.drawStringWithShadow("A", 3, 1, Client.instance().getAmbienOldBlueColor());
                Client.instance().getFontManager().ambien45.drawStringWithShadow("mbien", 17, 1, Client.instance().getGrey());
                Client.instance().getFontManager().ambien20.drawStringWithShadow(Client.instance().getClientVersion(), 55, 0, 0x349ac0);
                break;
            }
            case "vortex": {
                GL11.glPushMatrix();
                GL11.glScalef(2f, 2f, 2f);
                Client.instance().getFontManager().vortex20.drawString("V", 0, -2, Client.instance().getVortexRedColor());
                GL11.glScalef(2f / 4, 2f / 4, 2f / 4);
                GL11.glPopMatrix();

                Client.instance().getFontManager().vortex20.drawString("ortex", 18, 6, Client.instance().getGrey());

                GL11.glPushMatrix();
                GL11.glScalef(0.8f, 0.8f, 0.8f);
                Client.instance().getFontManager().vortex20.drawString(Client.instance().getClientVersion(), 72, 9, 0x349ac0);
                GL11.glPopMatrix();
                break;
            }
            case "suicide": {
                Client.instance().getFontManager().getFont("Comfortaa", 30, Font.PLAIN).drawStringWithShadow("SUICIDE", 2, 3, Client.instance().getSuicideBlueColor());
                Client.instance().getFontManager().getFont("Comfortaa", 20, Font.PLAIN).drawStringWithShadow(Client.instance().getClientVersion(), 68, 4, Client.instance().getSuicideBlueColor());

                RenderUtils.drawRect(0, 110, 75, 151, Client.instance().getSuicideDarkBlueGreyColor());
                RenderUtils.drawRect(0, 110, 73, 153, Client.instance().getSuicideBlueGreyColor());

                Client.instance().getFontManager().getFont("Comfortaa", 20, Font.PLAIN).drawStringWithShadow("FPS: ", 2, 115, Client.instance().getSuicideBlueColor());
                Client.instance().getFontManager().getFont("Comfortaa", 20, Font.PLAIN).drawStringWithShadow(String.valueOf(Minecraft.debugFPS), 26, 115, -1);

                Client.instance().getFontManager().getFont("Comfortaa", 20, Font.PLAIN).drawStringWithShadow("Ping: ", 2, 127, Client.instance().getSuicideBlueColor());
                try {
                    Client.instance().getFontManager().getFont("Comfortaa", 20, Font.PLAIN).drawStringWithShadow(String.valueOf(mc.getCurrentServerData().pingToServer), 30, 127, -1);
                } catch (Exception ex) {
                    Client.instance().getFontManager().getFont("Comfortaa", 20, Font.PLAIN).drawStringWithShadow("N/A", 30, 127, -1);
                }

                Client.instance().getFontManager().getFont("Comfortaa", 20, Font.PLAIN).drawStringWithShadow("Time: ", 2, 139, Client.instance().getSuicideBlueColor());
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();
                Client.instance().getFontManager().getFont("Comfortaa", 20, Font.PLAIN).drawStringWithShadow(dtf.format(now), 32, 139, -1);
                break;
            }
            case "apinity": {
                RenderUtils.drawRect(0, 35, 40, 175, new Color(0, 0, 0, 150).getRGB());

                Client.instance().getFontManager().getFont("Raleway Light", 45, Font.PLAIN).drawStringWithShadow("Apinity", 1, 1, Client.instance().getApinityGreyColor());
                Client.instance().getFontManager().getFont("Raleway Light", 30, Font.PLAIN).drawStringWithShadow(Client.instance().getClientVersion(), 72, 1, Client.instance().getApinityBlueColor());

                try {
                    Client.instance().getFontManager().getFont("Comfortaa", 15, Font.PLAIN).drawStringWithShadow("Ping: " + mc.getCurrentServerData().pingToServer, 2, 116, Client.instance().getApinityBlueColor());
                } catch (Exception ex) {
                    Client.instance().getFontManager().getFont("Comfortaa", 15, Font.PLAIN).drawStringWithShadow("Ping: N/A", 1, 116, Client.instance().getApinityBlueColor());
                }

                Client.instance().getFontManager().getFont("Comfortaa", 15, Font.PLAIN).drawStringWithShadow("FPS: " + Minecraft.debugFPS, 2, 125, Client.instance().getApinityBlueColor());

                Client.instance().getFontManager().getFont("Comfortaa", 15, Font.PLAIN).drawStringWithShadow("X: " + mc.thePlayer.getPosition().getX(), 2, 145, Client.instance().getApinityBlueColor());
                Client.instance().getFontManager().getFont("Comfortaa", 15, Font.PLAIN).drawStringWithShadow("Y: " + mc.thePlayer.getPosition().getY(), 2, 155, Client.instance().getApinityBlueColor());
                Client.instance().getFontManager().getFont("Comfortaa", 15, Font.PLAIN).drawStringWithShadow("Z: " + mc.thePlayer.getPosition().getZ(), 2, 165, Client.instance().getApinityBlueColor());
                break;
            }
            case "huzuni": {
                Client.instance().getFontManager().getFont("Arial", 20, Font.PLAIN).drawStringWithShadow("Huzuni Dev " + Client.instance().getClientVersion(), 2, 2, -1);
                break;
            }
            case "wurst": {
                RenderUtils.drawRect(0, 10, 145, 28, new Color(255, 255, 255, 130).getRGB());

                Client.instance().getFontManager().getFont("Arial", 20, Font.PLAIN).drawString("v" + Client.instance().getClientVersion() + " MC1.8.8", 80, 15, new Color(0, 0, 0).getRGB());

                int width = 75;
                int height = 20;
                int x = 0;
                int y = 9;

                RenderUtils.drawImage(Client.instance().getWurstWatermark(), x, y, width, height);
                break;
            }
            case "nodus": {
                mc.fontRendererObj.drawString("Nodus", 2, 2, Client.instance().getNodusPurpleColor());
                mc.fontRendererObj.drawString("v" + Client.instance().getClientVersion(), 35, 2, Client.instance().getNodusTealColor());
                break;
            }
            case "saint": {
                UnicodeFontRenderer f1 = Client.instance().getFontManager().getFont("Verdana", 20, Font.PLAIN);
                UnicodeFontRenderer f2 = Client.instance().getFontManager().getFont("Verdana", 17, Font.PLAIN);
                String s1 = "Saint";
                String s2 = "v" + Client.instance().getClientVersion();
                f1.drawStringWithShadow(s1, s.width() / 2 - f1.getStringWidth(s1) / 2, 3, -1);
                f2.drawStringWithShadow(s2, s.width() / 2 - f2.getStringWidth(s2) / 2, 13, new Color(0x4A4A4A).getRGB());

                f2.drawStringWithShadow("FPS:", 2, 88, -1);
                f2.drawStringWithShadow(String.valueOf(Minecraft.debugFPS), 22, 88, new Color(0x4A4A4A).getRGB());
                break;
            }
            case "icarus old": {
                mc.fontRendererObj.drawString("Icarus (b" + Client.instance().getClientVersion() + ")", 2, 2, -1);
                break;
            }
            case "icarus new": {
                RenderUtils.drawRect(s.width() - 62, 0, s.width(), 20, Client.instance().getIcarusNewGreyColor());

                Client.instance().getFontManager().getFont("BigNoodleTiltling", 40, Font.BOLD).drawStringWithShadow("Icarus", s.width() - 60, -2, -1);
                break;
            }
            case "ambien new": {
                RenderUtils.drawRect(0, 10, 112, 40, Client.instance().getAmbienNewDarkGreyColor());

                Client.instance().getFontManager().getFont("FIFA Welcome", 60, Font.PLAIN).drawStringWithShadow("A", 2, 7, Client.instance().getAmbienNewBlueColor());
                Client.instance().getFontManager().getFont("FIFA Welcome", 60, Font.PLAIN).drawStringWithShadow("mbien", 20, 7, -1);
                Client.instance().getFontManager().getFont("BigNoodleTiltling", 20, Font.PLAIN).drawStringWithShadow("V", 89, 24, Client.instance().getAmbienNewBlueColor());
                Client.instance().getFontManager().getFont("BigNoodleTiltling", 20, Font.PLAIN).drawStringWithShadow(Client.instance().getClientVersion(), 96, 24, -1);
                break;
            }
            case "hero": {
                RenderUtils.drawRect(0, 0, 60, 21, new Color(0, 0, 0, 70).getRGB());

                Client.instance().getFontManager().getFont("Raleway Light", 55, Font.PLAIN).drawStringWithShadow("Hero", -2, -4, Client.instance().getHeroGreenColor());
                break;
            }
            case "klientus": {
                Client.instance().getFontManager().getFont("Verdana", 50, Font.PLAIN).drawStringWithShadow("K", 1, 1, new Color(0x00659f).getRGB());
                Client.instance().getFontManager().getFont("Verdana", 50, Font.PLAIN).drawStringWithShadow("lientus", 18, 1, -1);

                RenderUtils.drawRect(3, 28, 105, 30, -1);

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
                LocalDateTime now = LocalDateTime.now();

                Client.instance().getFontManager().getFont("Verdana", 18, Font.PLAIN).drawStringWithShadow(dtf.format(now), 32, 32, -1);
                break;
            }
        }
    }

    private void renderTabGUI() {
        TabGUI gui = new TabGUI(mc);

        if (Client.instance().setMgr().getSettingByName("Watermark", Client.instance().modManager().getByName("HUD")).getBool()) {
            String mode = Client.instance().setMgr().getSettingByName("Design", Client.instance().modManager().getByName("HUD")).getMode();

            switch (mode) {
                case "ambien old": {
                    gui.drawGui(1, 30, 72);
                    break;
                }
                case "vortex": {
                    gui.drawGui(1, 20, 72);
                    break;
                }
                case "suicide": {
                    gui.drawGui(1, 20, 72);
                    break;
                }
                case "apinity": {
                    gui.drawGui(5, 40, 55);
                    break;
                }
                case "huzuni": {
                    gui.drawGui(3, 15, 95);
                    break;
                }
                case "saint": {
                    gui.drawGui(3, 15, 65);
                    break;
                }
                case "icarus old": {
                    gui.drawGui(3, 15, 65);
                    break;
                }
                case "icarus new": {
                    gui.drawGui(3, 15, 65);
                    break;
                }
                case "ambien new": {
                    gui.drawGui(1, 45, 65);
                    break;
                }
                case "hero": {
                    gui.drawGui(1, 22, 59);
                    break;
                }
                case "vanta": {
                    gui.drawGui(1, 20, 65);
                    break;
                }
            }
        }
    }

    private void renderArrayList() {
        ScaledResolution s = new ScaledResolution(mc);
        String module;

        int stringY = 2;
        int rectY = 1;

        String mode = Client.instance().setMgr().getSettingByName("ArrayList Rect Mode", Client.instance().modManager().getByName("HUD")).getMode();
        String design = Client.instance().setMgr().getSettingByName("Design", Client.instance().modManager().getByName("HUD")).getMode();

        UnicodeFontRenderer comfortaa = Client.instance().getFontManager().getFont("Comfortaa", 20, Font.PLAIN);
        UnicodeFontRenderer bigNoodleTilting = Client.instance().getFontManager().getFont("BigNoodleTilting", 20, Font.PLAIN);

        for (Module m : Client.instance().modManager().modules) {
            module = m.getName();
            if (m.getState() && !m.isCategory(Category.GUI)) {
                if (Client.instance().setMgr().getSettingByName("ArrayList Background", Client.instance().modManager().getByName("HUD")).getBool()) {
                    if (mode.equalsIgnoreCase("Left")) {
                        switch (design) {
                            case "ambien new":
                            case "ambien old": {
                                RenderUtils.drawRect(s.width() - bigNoodleTilting.getStringWidth(module) - 3, (rectY - 2), s.width(), (rectY + 10), new Color(0, 0, 0, 150).getRGB());
                                RenderUtils.drawRect(s.width() - bigNoodleTilting.getStringWidth(module) - 5, (rectY - 2), s.width() - bigNoodleTilting.getStringWidth(module) - 3, (rectY + 10), Rainbow.rainbow(1, 1).getRGB());
                                bigNoodleTilting.drawStringWithShadow(module, s.width() - bigNoodleTilting.getStringWidth(module) - 2, stringY - 2, Rainbow.rainbow(1, 1).getRGB());
                                break;
                            }
                            default: {
                                RenderUtils.drawRect(s.width() - comfortaa.getStringWidth(module) - 3, (rectY - 2), s.width(), (rectY + 10), new Color(0, 0, 0, 150).getRGB());
                                RenderUtils.drawRect(s.width() - comfortaa.getStringWidth(module) - 5, (rectY - 2), s.width() - comfortaa.getStringWidth(module) - 3, (rectY + 10), Rainbow.rainbow(1, 1).getRGB());
                                comfortaa.drawStringWithShadow(module, s.width() - comfortaa.getStringWidth(module) - 1, stringY, Rainbow.rainbow(1, 1).getRGB());
                                break;
                            }
                        }
                    } else if (mode.equalsIgnoreCase("Right")) {
                        switch (design) {
                            case "ambien new":
                            case "ambien old": {
                                RenderUtils.drawRect(s.width() - bigNoodleTilting.getStringWidth(module) - 5, (rectY - 2), s.width(), (rectY + 10), new Color(0, 0, 0, 150).getRGB());
                                RenderUtils.drawRect(s.width() - 3, (rectY - 2), s.width(), (rectY + 10), Rainbow.rainbow(1, 1).getRGB());
                                bigNoodleTilting.drawStringWithShadow(module, s.width() - bigNoodleTilting.getStringWidth(module) - 4, stringY - 1, Rainbow.rainbow(1, 1).getRGB());
                                break;
                            }
                            default: {
                                RenderUtils.drawRect(s.width() - comfortaa.getStringWidth(module) - 5, (rectY - 2), s.width(), (rectY + 10), new Color(0, 0, 0, 150).getRGB());
                                RenderUtils.drawRect(s.width() - 3, (rectY - 2), s.width(), (rectY + 10), Rainbow.rainbow(1, 1).getRGB());
                                comfortaa.drawStringWithShadow(module, s.width() - comfortaa.getStringWidth(module) - 4, stringY, Rainbow.rainbow(1, 1).getRGB());
                                break;
                            }
                        }
                    } else if (mode.equalsIgnoreCase("None")) {
                        switch (design) {
                            case "ambien new":
                            case "ambien old": {
                                RenderUtils.drawRect(s.width() - bigNoodleTilting.getStringWidth(module) - 3, (rectY - 2), s.width(), (rectY + 10), new Color(0, 0, 0, 150).getRGB());
                                bigNoodleTilting.drawStringWithShadow(module, s.width() - bigNoodleTilting.getStringWidth(module) - 1, stringY - 1, Rainbow.rainbow(1, 1).getRGB());
                                break;
                            }
                            default: {
                                RenderUtils.drawRect(s.width() - comfortaa.getStringWidth(module) - 3, (rectY - 2), s.width(), (rectY + 10), new Color(0, 0, 0, 150).getRGB());
                                comfortaa.drawStringWithShadow(module, s.width() - comfortaa.getStringWidth(module) - 1, stringY, Rainbow.rainbow(1, 1).getRGB());
                                break;
                            }
                        }
                    }
                } else {
                    Client.instance().getFontManager().comfortaa20.drawString(module, s.width() - Client.instance().getFontManager().comfortaa20.getStringWidth(module) - 1, stringY, Rainbow.rainbow(1, 1).getRGB());
                }
                rectY += 12;
                stringY += 12;
            }
        }
    }
}