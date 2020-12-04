package com.masterof13fps.features.ui.guiscreens;

import com.masterof13fps.Client;
import com.masterof13fps.Wrapper;
import com.masterof13fps.manager.fontmanager.FontManager;
import com.masterof13fps.manager.fontmanager.UnicodeFontRenderer;
import com.masterof13fps.utils.render.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.StringJoiner;

public class GuiChangelog extends GuiScreen {

    public GuiScreen parent;
    FontManager fM = Client.main().fontMgr();

    float scrollAmount = (-Mouse.getDWheel()) * 0.07F;
    float scrolled = 0;
    String result;
    private GuiScreen parentScreen;

    public GuiChangelog(GuiScreen parentScreen) {
        parent = parentScreen;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        buttonList.add(new GuiButton(1, width / 2 - 100, height - 30, 200, 20, "Zurück"));

        Thread changelogThread = new Thread(() -> {
            try {
                URL url = new URL("https://pastebin.com/raw/3NiZ3SMC");
                InputStreamReader isr = new InputStreamReader(url.openStream());
                BufferedReader reader = new BufferedReader(isr);
                StringJoiner sb = new StringJoiner("");
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.add(line + System.lineSeparator());
                }
                result = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        changelogThread.start();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
            mc.displayGuiScreen(parentScreen);
        }
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 1) {
            mc.displayGuiScreen(parent);
        }
    }

    public void drawScreen(int posX, int posY, float f) {
        ScaledResolution sr = new ScaledResolution(Wrapper.mc);

        int scroll = Mouse.getDWheel() / 12;
        float bottom = sr.height() - 103;

        scrollAmount += scroll;
        if (scrollAmount >= bottom) scrollAmount = bottom;

        mc.getTextureManager().bindTexture(new ResourceLocation(Client.main().getClientBackground()));
        Gui.drawScaledCustomSizeModalRect(0, 0, 0.0F, 0.0F, sr.width(), sr.height(),
                width, height, sr.width(), sr.height());

        RenderUtils.drawRect(5, 0, width - 5, height, new Color(0, 0, 0, 155).getRGB());


        UnicodeFontRenderer cabin35 = fM.font("Cabin", 35, Font.PLAIN);
        UnicodeFontRenderer cabin23 = fM.font("Cabin", 23, Font.PLAIN);

        String title = "Credits";
        cabin35.drawStringWithShadow(title, width / 2 - cabin35.getStringWidth(title) / 2, 10, -1);

        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        RenderUtils.scissor(5, 35, sr.width() - 5, sr.height() - 35);
        RenderUtils.drawRect(0, 0, sr.width(), sr.height(), new Color(50, 50, 50).getRGB());
        try {
            cabin23.drawStringWithShadow(result, 10, 40 + scrollAmount, -1);
        } catch (Exception ignored) {
        }

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        super.drawScreen(posX, posY, f);
    }
}