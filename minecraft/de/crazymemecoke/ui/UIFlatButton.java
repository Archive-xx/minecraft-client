package de.crazymemecoke.ui;

import de.crazymemecoke.Client;
import de.crazymemecoke.utils.Wrapper;
import de.crazymemecoke.utils.time.TimeHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.StringUtils;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class UIFlatButton extends GuiButton {
    private final TimeHelper time = new TimeHelper();
    public String displayString;
    public int id;
    public boolean enabled;
    public boolean visible;
    protected boolean hovered;
    private final int color;
    private float opacity;
    private UnicodeFontRenderer font;

    public UIFlatButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int color) {
        super(buttonId, x, y, 10, 12, buttonText);
        this.width = widthIn;
        this.height = heightIn;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.displayString = buttonText;
        this.color = color;
    }

    public UIFlatButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText, int color, UnicodeFontRenderer font) {
        super(buttonId, x, y, 10, 12, buttonText);
        this.width = widthIn;
        this.height = heightIn;
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.xPosition = x;
        this.yPosition = y;
        this.displayString = buttonText;
        this.color = color;
    }

    protected int getHoverState(boolean mouseOver) {
        byte i = 1;
        if(!this.enabled) {
            i = 0;
        } else if(mouseOver) {
            i = 2;
        }

        return i;
    }

    public void drawButton(Minecraft mc, int mouseX, int mouseY) {
        if(this.visible) {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int var5 = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.blendFunc(770, 771);
            if(!this.hovered) {
                this.time.reset();
                this.opacity = 0.0F;
            }

            if(this.hovered) {
                this.opacity += 0.5F;
                if(this.opacity > 1.0F) {
                    this.opacity = 1.0F;
                }
            }

            float radius = this.height / 2.0F;
            GL11.glColor3f(2.55F, 2.55F, 2.55F);
            this.mouseDragged(mc, mouseX, mouseY);
            GL11.glPushMatrix();
            GL11.glPushAttrib(1048575);
            GL11.glScaled(1.0D, 1.0D, 1.0D);
            boolean var6 = true;
            float var10000 = Client.getInstance().getFontManager().comfortaa20.getStringHeight(StringUtils.stripControlCodes(this.displayString));
            Wrapper.mc.fontRendererObj.drawString(this.displayString, (this.xPosition + this.width / 2), this.yPosition + (this.height - Wrapper.mc.fontRendererObj.FONT_HEIGHT) / 2, this.hovered?-1:-3487030);
            GL11.glPopAttrib();
            GL11.glPopMatrix();
        }

    }

    private Color darkerColor(Color c, int step) {
        int red = c.getRed();
        int blue = c.getBlue();
        int green = c.getGreen();
        int var10000;
        if(red >= step) {
            var10000 = red - step;
        }

        if(blue >= step) {
            var10000 = blue - step;
        }

        if(green >= step) {
            var10000 = green - step;
        }

        return c.darker();
    }

    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
    }

    public void mouseReleased(int mouseX, int mouseY) {
    }

    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
    }

    public boolean isMouseOver() {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY) {
    }

    public int getButtonWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
