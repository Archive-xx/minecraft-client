package de.crazymemecoke.manager.clickguimanager.clickgui.components;

import java.awt.Color;
import java.util.ArrayList;

import de.crazymemecoke.manager.clickguimanager.clickgui.Panel;
import de.crazymemecoke.manager.clickguimanager.clickgui.listeners.KeyListener;
import de.crazymemecoke.manager.clickguimanager.clickgui.util.RenderUtil;
import de.crazymemecoke.utils.render.RenderUtils;
import org.lwjgl.input.Keyboard;

/**
 * @author sendQueue <Vinii>
 *
 *         Further info at Vinii.de or github@vinii.de, file created at
 *         11.11.2020. Use is only authorized if given credit!
 * 
 */
public class GuiGetKey implements GuiComponent {
	private boolean wasChanged, allowChange;
	private int key, posX, posY, width;
	private String text;

	private ArrayList<KeyListener> keylisteners = new ArrayList<KeyListener>();

	public GuiGetKey(String text, int key) {
		this.text = text;
		this.key = key;
		if (key < 0)
			this.key = 0;
	}

	public void addKeyListener(KeyListener listener) {
		keylisteners.add(listener);
	}

	@Override
	public void render(int posX, int posY, int width, int mouseX, int mouseY) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		switch (Panel.theme) {
		case "Caesium":
			renderCaesium(posX, posY);
			break;
		default:
			break;
		}
	}

	private void renderCaesium(int posX, int posY) {
		String keyString = Keyboard.getKeyName(key);
		if (allowChange) {
			wasChanged = !wasChanged;
		}else {
			wasChanged = true;
		}
		RenderUtils.drawVerticalGradient(posX, posY, width, 14, new Color(Panel.color).darker().getRGB(),
				new Color(Panel.color).brighter().getRGB());
		Panel.fR.drawStringWithShadow(text + ":", posX + 3, posY + 3, Panel.fontColor);
		if (wasChanged) {
			Panel.fR.drawStringWithShadow(keyString, posX + width - Panel.fR.getStringWidth(keyString) - 3, posY + 3,
					Panel.fontColor);
		} else {
			Panel.fR.drawString(keyString, posX + width - Panel.fR.getStringWidth(keyString) - 3, posY + 3,
					Panel.fontColor);
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		String keyString = Keyboard.getKeyName(key);
		final int w = Panel.fR.getStringWidth(text);
		if (RenderUtil.isHovered(posX, posY, width, 11, mouseX, mouseY)) {
			allowChange = true;
		} else {
			allowChange = false;
		}

	}

	@Override
	public void keyTyped(int keyCode, char typedChar) {
		if (allowChange) {
			for (KeyListener listener : keylisteners) {
				listener.keyChanged(keyCode);
			}

			key = keyCode;
			allowChange = false;
		}
	}

	@Override
	public int getWidth() {
		return Panel.fR.getStringWidth(text + Keyboard.getKeyName(key)) + 15;
	}

	@Override
	public int getHeight() {
		return Panel.fR.FONT_HEIGHT + 4;
	}

}
